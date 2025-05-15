package com.dcac.flightsearch.ui

import com.dcac.flightsearch.data.airport.Airport
import com.dcac.flightsearch.model.FlightUiState
import kotlinx.coroutines.flow.StateFlow
// 15H30
interface IFlightViewModel {

    val uiState: StateFlow<FlightUiState>

    fun displayAirportsByQuery(query: String)
    fun displayRoutesFromSelectedAirport(code: String)
    fun getAllAirports()
    fun onToggleFavorite(departure: Airport, destination: Airport)
    fun displayFavorites()
    fun saveSearchQuery(query: String)



}