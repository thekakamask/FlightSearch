package com.dcac.flightsearch.data.airport

import kotlinx.coroutines.flow.Flow

interface AirportRepository {

    fun getAllAirports(): Flow<List<Airport>>
    fun searchAirports(query: String): Flow<List<Airport>>
    fun getAirportByCode(code: String): Flow<Airport>
    fun getDestinationsFrom(departureCode: String): Flow<List<Airport>>
}