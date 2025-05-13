package com.dcac.flightsearch.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.dcac.flightsearch.data.airport.AirportRepository
import com.dcac.flightsearch.data.airport.OfflineAirportRepository
import com.dcac.flightsearch.data.favorite.FavoriteRepository
import com.dcac.flightsearch.data.favorite.OfflineFavoriteRepository
import com.dcac.flightsearch.data.preferences.UserPreferencesRepository

// Extension property to provide DataStore instance from any Context
private val Context.dataStore by preferencesDataStore(name = "flight_search_preferences")

//App container for dependency injection
interface AppContainer {
    val airportRepository: AirportRepository
    val favoriteRepository: FavoriteRepository
    val preferencesRepository: UserPreferencesRepository
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

    //Implementation of UserPreferencesRepository
    override val preferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.dataStore)
    }

}