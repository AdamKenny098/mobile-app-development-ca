package ie.setu.mobileappdevelopmentca.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Calendar
import java.util.Date

@Parcelize
data class GameModel(var id: Long = 0,
                     var title: String = "",
                     var ageRating: Int = 0,
                     var platform: List<String> = emptyList(),
                     var genre: List<String> = emptyList(),
                     var releaseDate: Date = Calendar.getInstance().time,
                     var status: String ="",
                     var location: Location = Location()): Parcelable{
}

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable