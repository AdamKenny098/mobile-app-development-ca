package ie.setu.mobileappdevelopmentca.main

import android.app.Application
import ie.setu.mobileappdevelopmentca.models.GameJSONStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var games: GameJSONStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")

        games = GameJSONStore(applicationContext)
        games.load() // make sure it loads from JSON on startup
    }
}

