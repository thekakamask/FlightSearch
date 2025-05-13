package com.dcac.flightsearch

import android.app.Application
import com.dcac.flightsearch.data.AppContainer
import com.dcac.flightsearch.data.AppDataContainer

//This class is instantiated before any other class and allows to
// initialize global dependencies.
class FlightSearchApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

    }
}