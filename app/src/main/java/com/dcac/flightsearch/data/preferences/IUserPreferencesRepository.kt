package com.dcac.flightsearch.data.preferences

import kotlinx.coroutines.flow.Flow

interface IUserPreferencesRepository {

    suspend fun saveSearchQuery(query: String)
    val searchQuery: Flow<String>
}