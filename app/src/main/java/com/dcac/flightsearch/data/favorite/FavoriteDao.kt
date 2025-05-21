package com.dcac.flightsearch.data.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    //Add favorite
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorite)

    //Delete favorite
    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    //Display all favorites
    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    //Check if a favorite
    @Query("""
    SELECT EXISTS(
        SELECT 1 FROM favorite
        WHERE departure_code = :departure AND destination_code = :destination
    )
""")
    fun isFavorite(departure: String, destination: String): Flow<Boolean>

}