package ie.setu.mobileappdevelopmentca.main

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import ie.setu.mobileappdevelopmentca.models.GameMemStore
import ie.setu.mobileappdevelopmentca.models.GameModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val games = GameMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")
    }
}

