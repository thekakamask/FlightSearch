package com.dcac.flightsearch.model

import androidx.compose.runtime.Immutable
import com.dcac.flightsearch.data.airport.Airport

sealed interface FlightUiState {

    @Immutable
    data class Success(
        val showSuggestions : Boolean = true,              // Flag to display suggestions
        val searchQuery: String = "",                             // Stored search query
        val searchSuggestions: List<Airport> = emptyList(),       // TextSuggestions
        val selectedDeparture: Airport? = null,                   // Selected airport of departure
        val destinations: List<FlightUiModel> = emptyList(),      // Flight available from selected airport
        val favorites: List<FlightUiModel> = emptyList()           // List of enriched favorites routes
        //IDEA of update FILTRED FAVORITES FROM DESTINATIONS IN THE PLACE OF HAD 2 LISTS.
    ) : FlightUiState

    data class Error(                       //Handles error messages while preserving user context (searchQuery)
        val message: String,
        val searchQuery: String = ""
    ) : FlightUiState

}

data class FlightUiModel(
    val departure: Airport,
    val destination: Airport,
    val isFavorite: Boolean
)
