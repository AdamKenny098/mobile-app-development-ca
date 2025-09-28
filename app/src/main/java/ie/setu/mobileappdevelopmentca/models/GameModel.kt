package ie.setu.mobileappdevelopmentca.models

import java.util.Date

data class GameModel(var title: String = "",
                     var ageRating: Int,var platform: Array<String> = arrayOf(""),
                     var genre: Array<String> = arrayOf(""),
                     var releaseDate: Date){
}