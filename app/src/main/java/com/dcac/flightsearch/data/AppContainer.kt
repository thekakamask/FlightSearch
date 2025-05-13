package com.dcac.flightsearch.data

import android.content.Context
import com.dcac.flightsearch.data.airport.AirportRepository
import com.dcac.flightsearch.data.airport.OfflineAirportRepository
import com.dcac.flightsearch.data.favorite.FavoriteRepository
import com.dcac.flightsearch.data.favorite.OfflineFavoriteRepository

//App container for dependency injection
interface AppContainer {
    val airportRepository: AirportRepository
    val favoriteRepository: FavoriteRepository
}

//AppContainer implementation that provides instance of OfflineItemsRepository
class AppDataContainer(private val context: Context) : AppContainer {

    //Implementation of AirportRepository
    override val airportRepository: AirportRepository by lazy {
        OfflineAirportRepository(FlightSearchDatabase.getDatabase(context).airportDao())
    }

    //Implementation of FavoriteRepository
    override val favoriteRepository: FavoriteRepository by lazy {
        OfflineFavoriteRepository(FlightSearchDatabase.getDatabase(context).favoriteDao())
    }
}