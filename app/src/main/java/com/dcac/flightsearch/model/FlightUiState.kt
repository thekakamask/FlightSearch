package com.dcac.flightsearch.model

import androidx.compose.runtime.Immutable
import com.dcac.flightsearch.data.airport.Airport

sealed interface FlightUiState {

    @Immutable
    data class Success(
        val searchQuery: String = "",                             // Stored search query
        val searchSuggestions: List<Airport> = emptyList(),       // TextSuggestions
        val selectedDeparture: Airport? = null,                   // Selected airport of departure
        val destinations: List<FlightUiModel> = emptyList(),      // Flight available from selected airport
        val favorites: List<FlightUiModel> = emptyList()           // List of enriched favorites routes
    ) : FlightUiState

    data class Error(                       //Handles error messages while preserving user context (searchQuery)
        val message: String,
        val searchQuery: String = ""
    ) : FlightUiState

    data object Loading : FlightUiState     //Represents a pure loading state

}

data class FlightUiModel(
    val departure: Airport,
    val destination: Airport,
    val isFavorite: Boolean
)
