package com.dcac.flightsearch.model

import com.dcac.flightsearch.data.airport.Airport

data class FlightUi(
    val departureAirport: Airport,
    val destinationAirport: Airport,
    val isFavorite: Boolean
)
