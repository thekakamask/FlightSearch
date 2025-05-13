package com.dcac.flightsearch.data.favorite

import kotlinx.coroutines.flow.Flow

class OfflineFavoriteRepository(
    private val favoriteDao: FavoriteDao
): FavoriteRepository {

    override suspend fun insertFavorite(favorite: Favorite)
    = favoriteDao.insertFavorite(favorite)

    override suspend fun deleteFavorite(favorite: Favorite)
    = favoriteDao.deleteFavorite(favorite)

    override fun getAllFavorites(): Flow<List<Favorite>>
    = favoriteDao.getAllFavorites()

    override fun isFavorite(
        departure: String,
        destination: String
    ): Flow<Boolean>
    = favoriteDao.isFavorite(departure, destination)

}