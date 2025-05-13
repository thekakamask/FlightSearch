package com.dcac.flightsearch.data.airport

import kotlinx.coroutines.flow.Flow

class OfflineAirportRepository(
    private val airportDao: AirportDao
) : AirportRepository {

    override fun getAllAirports(): Flow<List<Airport>>
    = airportDao.getAllAirports()

    override fun searchAirports(query: String): Flow<List<Airport>>
    = airportDao.searchAirports(query)

    override fun getAirportByCode(code: String): Flow<Airport>
    = airportDao.getAirportByCode(code)

}