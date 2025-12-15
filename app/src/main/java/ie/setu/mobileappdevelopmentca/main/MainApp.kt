package ie.setu.mobileappdevelopmentca.main

import android.app.Application
import ie.setu.mobileappdevelopmentca.models.GameFirestoreStore
import ie.setu.mobileappdevelopmentca.models.GameJSONStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    //lateinit var games: GameJSONStore
    lateinit var firestoreGames: GameFirestoreStore


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")

        //games = GameJSONStore(applicationContext)
        firestoreGames = GameFirestoreStore()

        //games.load() // make sure it loads from JSON on startup
    }
}

