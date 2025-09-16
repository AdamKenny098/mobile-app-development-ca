package org.setu.placemark.console.main.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

import org.setu.placemark.console.main.helpers.*
import java.util.*

val JSON_FILE = "games.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<GameModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}
class GameJSONStore {

    var games = mutableListOf<GameModel>()

    init {
        if (exists(JSON_FILE)) {
            deserialize()
        }
    }

    fun findAll(): MutableList<GameModel> {
        return games
    }

    fun findOne(id: Long): GameModel? {
        var foundGame: GameModel? = games.find { p -> p.id == id }
        return foundGame
    }

    fun create(game: GameModel) {
        game.id = generateRandomId()
        games.add(game)
        serialize()
    }

    fun update(game: GameModel) {
        var foundGame = findOne(game.id!!)
        if (foundGame != null) {
            foundGame.title = game.title
            foundGame.description = game.description
            foundGame.ageRating = game.ageRating
        }
        serialize()
    }

    fun delete(game: GameModel) {
        var foundGame = findOne(game.id!!)
        if (foundGame != null) {
            games.remove(game)
            serialize()
        }

    }

    internal fun logAll() {
        games.forEach { println("$it") }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(games, listType)
        write(JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(JSON_FILE)
        games = Gson().fromJson(jsonString, listType)
    }


}
