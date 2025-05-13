package com.dcac.flightsearch.data.airport

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface AirportDao {

    //Preload or display all airports
    @Query("SELECT * FROM airport ORDER BY name ASC")
    fun getAllAirports(): Flow<List<Airport>>

    //Dynamic search suggestions based on the user's query
    @Query("""
    SELECT * FROM airport
    WHERE name LIKE '%' || :query || '%' OR iata_code LIKE '%' || :query || '%'
    ORDER BY name ASC
""")
    fun searchAirports(query: String): Flow<List<Airport>>

    //Display details of a given airport
    @Query("SELECT * FROM airport WHERE iata_code = :code LIMIT 1")
    fun getAirportByCode(code: String): Flow<Airport>

    @Query("""
    SELECT * FROM airport
    WHERE iata_code != :departureCode
    ORDER BY passengers DESC
""")
    fun getDestinationsFrom(departureCode: String): Flow<List<Airport>>


}