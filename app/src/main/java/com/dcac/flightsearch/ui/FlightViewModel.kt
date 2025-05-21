package com.dcac.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dcac.flightsearch.FlightSearchApplication
import com.dcac.flightsearch.data.airport.Airport
import com.dcac.flightsearch.data.airport.AirportRepository
import com.dcac.flightsearch.data.favorite.Favorite
import com.dcac.flightsearch.data.favorite.FavoriteRepository
import com.dcac.flightsearch.data.preferences.UserPreferencesRepository
import com.dcac.flightsearch.model.FlightUiModel
import com.dcac.flightsearch.model.FlightUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightViewModel(
    private val airportRepository: AirportRepository,
    private val favoriteRepository: FavoriteRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(), IFlightViewModel {

    private val _uiState = MutableStateFlow<FlightUiState>(FlightUiState.Success())
    override val uiState: StateFlow<FlightUiState> = _uiState.asStateFlow()

    init{
        initUiState()
    }

    private fun initUiState() {
        viewModelScope.launch {
            try {
                // Load last search query stored in Preferences (DataStore)
                val query = userPreferencesRepository.searchQuery.first()

                // Load all favorite routes saved in Room
                val rawFavorites = favoriteRepository.getAllFavorites().first()

                // For each Favorite, retrieve the departure and destination Airport from Room
                val enrichedFavorites = rawFavorites.mapNotNull { favorite ->
                    val departure = airportRepository.getAirportByCode(favorite.departureCode).firstOrNull()
                    val destination = airportRepository.getAirportByCode(favorite.destinationCode).firstOrNull()

                    // Wrap into a UI model with isFavorite = true
                    if (departure != null && destination != null) {
                        FlightUiModel(
                            departure = departure,
                            destination = destination,
                            isFavorite = true
                        )
                    } else null  // Skip if data is invalid
                }

                // Initialize the UI with saved search + enriched favorites
                _uiState.value = FlightUiState.Success(
                    searchQuery = query,
                    favorites = enrichedFavorites
                )
            } catch (e: Exception) {
                _uiState.value = FlightUiState.Error(
                    message = "Failed to initialize state: ${e.localizedMessage ?: "Unknown error"}"
                )
            }
        }
    }



    // Triggered when user types in the search field
    override fun displayAirportsByQuery(query: String) {
        // Update the query directly
        _uiState.update { current ->
            if (current is FlightUiState.Success) {
                current.copy(searchQuery = query)
            } else current
        }

        viewModelScope.launch {
            try {
                airportRepository.searchAirports(query).collect { results ->
                    _uiState.update { current ->
                        if (current is FlightUiState.Success) {
                            current.copy(
                                searchSuggestions = results,
                                showSuggestions = true
                            )
                        } else current
                    }
                }
            } catch (e: Exception) {
                _uiState.value = FlightUiState.Error(
                    message = "Search failed: ${e.localizedMessage ?: "Unknown error"}",
                    searchQuery = query
                )
            }
        }
    }

    // Called when user selects an airport suggestion
    override fun displayRoutesFromSelectedAirport(code: String) {
        viewModelScope.launch {
            try {
                // Retrieve the full Airport object for departure
                val departure = airportRepository.getAirportByCode(code).firstOrNull()

                if (departure != null) {
                    // Get all possible destinations except the selected airport
                    val destinations = airportRepository.getDestinationsFrom(code).first()
                    // Build a list of UI models with favorite status
                    val flights = destinations.map { destination ->
                        val isFav = favoriteRepository.isFavorite(departure.iataCode, destination.iataCode).first()
                        FlightUiModel(
                            departure = departure,
                            destination = destination,
                            isFavorite = isFav
                        )
                    }
                    // Update selected departure and destination routes in state
                    _uiState.update { currentState ->
                        if (currentState is FlightUiState.Success) {
                            currentState.copy(
                                selectedDeparture = departure,
                                destinations = flights,
                                showSuggestions = false,
                            )
                        } else currentState
                    }
                } else {
                    _uiState.update {
                        FlightUiState.Error("Airport not found")
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    FlightUiState.Error("Error loading airport: ${e.localizedMessage}")
                }
            }
        }
    }

    // Called when UI needs to display the full list of airports (no filtering)
    override fun getAllAirports() {
        viewModelScope.launch {
            // Get all airports from the database, ordered by name
            airportRepository.getAllAirports().collect { results ->
                _uiState.update { currentState ->
                    if (currentState is FlightUiState.Success) {
                        // Populate the suggestions with the full list
                        currentState.copy(
                            searchQuery = "", // Reset the query
                            searchSuggestions = results
                        )
                    } else currentState
                }
            }
        }
    }

    // Called when user taps the favorite star button (add/remove)
    override fun onToggleFavorite(departure: Airport, destination: Airport) {
        viewModelScope.launch {
            // Check current favorite status from the database
            val isFav = favoriteRepository.isFavorite(departure.iataCode, destination.iataCode).first()
            val favorite = Favorite(departureCode = departure.iataCode, destinationCode = destination.iataCode)
            // Insert or delete depending on current status
            if (isFav) {
                favoriteRepository.deleteFavorite(favorite)
            } else {
                favoriteRepository.insertFavorite(favorite)
            }

            // Refresh favorites list
            val rawFavorites = favoriteRepository.getAllFavorites().first()
            val enrichedFavorites = rawFavorites.mapNotNull { favorite ->
                val dep = airportRepository.getAirportByCode(favorite.departureCode).firstOrNull()
                val dest = airportRepository.getAirportByCode(favorite.destinationCode).firstOrNull()
                if (dep != null && dest != null) {
                    FlightUiModel(dep, dest, true)
                } else null
            }

            // Refresh destination list to reflect new favorite state (e.g., star toggle)
            _uiState.update { current ->
                if (current is FlightUiState.Success) {
                    val updatedDestinations = current.destinations.map {
                        if (
                            it.departure.iataCode == departure.iataCode &&
                            it.destination.iataCode == destination.iataCode
                        ) {
                            it.copy(isFavorite = !isFav)
                        } else it
                    }

                    current.copy(
                        favorites = enrichedFavorites,
                        destinations = updatedDestinations
                    )
                } else current
            }
        }
    }

    // Called when the search field is empty — display favorite routes only
    override fun displayFavorites() {
        viewModelScope.launch {
            try {
                // Load all saved favorites from the database
                val rawFavorites = favoriteRepository.getAllFavorites().first()
                // Resolve departure and destination Airport objects for each favorite
                val enrichedFavorites = rawFavorites.mapNotNull { favorite ->
                    val depCode = airportRepository.getAirportByCode(favorite.departureCode).firstOrNull()
                    val destCode = airportRepository.getAirportByCode(favorite.destinationCode).firstOrNull()
                    if (depCode != null && destCode != null) {
                        FlightUiModel(depCode, destCode, true)
                    } else null
                }

                // Clear the selection and populate favorites
                _uiState.update { current ->
                    if (current is FlightUiState.Success) {
                        current.copy(
                            selectedDeparture = null,
                            destinations = emptyList(), // Hide routes
                            favorites = enrichedFavorites
                        )
                    } else current
                }
            } catch (e: Exception) {
                _uiState.update {
                    FlightUiState.Error("Error loading favorites: ${e.localizedMessage}")
                }
            }
        }
    }

    //to save query of user
    override fun saveSearchQuery(query: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveSearchQuery(query)
            _uiState.update { currentState ->
                // Update suggestions + current query
                if (currentState is FlightUiState.Success) {
                    currentState.copy(
                        searchQuery = query,
                    )
                } else currentState
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                val airportRepository = application.container.airportRepository
                val favoriteRepository = application.container.favoriteRepository
                val userPreferencesRepository = application.container.preferencesRepository
                FlightViewModel(airportRepository, favoriteRepository, userPreferencesRepository)
            }
        }
    }
}