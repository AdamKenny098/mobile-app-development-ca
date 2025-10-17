package ie.setu.mobileappdevelopmentca.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import timber.log.Timber.i
import java.io.File
import java.util.*

private const val JSON_FILE = "games.json"
private val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
private val listType = object : TypeToken<ArrayList<GameModel>>() {}.type

fun generateRandomId(): Long = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE

class GameJSONStore(private val context: Context) {

    private var games = mutableListOf<GameModel>()

    init {
        load() // Auto-load when app starts
    }

    // --- CRUD Operations ---

    fun findAll(): List<GameModel> = games

    fun create(game: GameModel) {
        game.id = generateRandomId()
        games.add(game)
        save()
        i("Created game: ${game.title}")
    }

    fun update(game: GameModel) {
        val foundGame = games.find { it.id == game.id }
        if (foundGame != null) {
            foundGame.title = game.title
            foundGame.ageRating = game.ageRating
            foundGame.platform = game.platform
            foundGame.genre = game.genre
            foundGame.releaseDate = game.releaseDate
            foundGame.status = game.status
            save()
            i("Updated game: ${game.title}")
        }
    }

    fun delete(game: GameModel) {
        val removed = games.removeIf { it.id == game.id }
        if (removed) {
            save()
            i("Deleted game: ${game.title}")
        } else {
            i("Delete failed — not found: ${game.title}")
        }
    }

    // --- JSON Save & Load ---

    fun save() {
        try {
            val jsonString = gsonBuilder.toJson(games, listType)
            File(context.filesDir, JSON_FILE).writeText(jsonString)
            i("Saved ${games.size} games to JSON")
        } catch (e: Exception) {
            i("Save failed: ${e.message}")
        }
    }

    fun load() {
        val file = File(context.filesDir, JSON_FILE)
        if (file.exists()) {
            val jsonString = file.readText()
            if (jsonString.isNotEmpty()) {
                games = Gson().fromJson(jsonString, listType)
                i("Loaded ${games.size} games from JSON")
            } else {
                i("games.json empty — starting fresh")
                games = mutableListOf()
            }
        } else {
            i("No games.json found — will create on first save")
        }
    }
}
