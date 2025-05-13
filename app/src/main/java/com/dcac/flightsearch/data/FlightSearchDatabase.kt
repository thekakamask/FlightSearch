package com.dcac.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dcac.flightsearch.data.airport.Airport
import com.dcac.flightsearch.data.airport.AirportDao
import com.dcac.flightsearch.data.favorite.Favorite
import com.dcac.flightsearch.data.favorite.FavoriteDao

// Tells Room that this class manages two entities: Airport and Favorite
// Room will automatically generate all the code to read/write to these tablesCharge from assets/flight_search.db
@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class FlightSearchDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        //Single instance (singleton) shared throughout the application

        //Makes the variable visible and up-to-date for all threads.
        //Without @Volatile, a thread could see an obsolete version of Instance.
        // Mandatory if several threads can read or write the same variable.
        @Volatile
        private var Instance: FlightSearchDatabase? = null

        //Database access method
        fun getDatabase(context: Context): FlightSearchDatabase {
            return Instance ?: synchronized(this) { // Secure creation of the single instance
                // Secure creation of single instance Prevents multiple threads from creating multiple bases at the same time.
                // Guarantees that only one thread at a time can execute this block.
                // Without synchronized, two threads could attempt to create two instances → 💥 potential crash or memory leak.

                //Create Room database from file "flight_search.db" located in assets/
                Room.databaseBuilder(
                    context,
                    FlightSearchDatabase::class.java,
                    "flight_search_database") // Internal name of file in app storage
                    .createFromAsset("flight_search.db")  //Load from assets/flight_search.db
                    .fallbackToDestructiveMigration() //In case of unmanaged version change, reset DB
                    .build()
                    .also { Instance = it } //Save instance for future calls
            }
        }
    }

}