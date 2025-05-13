package com.dcac.flightsearch.data.favorite

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun insertFavorite(favorite: Favorite)
    suspend fun deleteFavorite(favorite: Favorite)
    fun getAllFavorites(): Flow<List<Favorite>>
    fun isFavorite(departure: String, destination: String): Flow<Boolean>

}