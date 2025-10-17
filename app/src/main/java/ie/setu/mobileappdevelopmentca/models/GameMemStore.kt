package ie.setu.mobileappdevelopmentca.models

import timber.log.Timber.i
import java.util.*

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class GameMemStore : GameStore {

    val games = ArrayList<GameModel>()

    override fun findAll(): List<GameModel> {
        return games
    }

    override fun create(game: GameModel) {
        game.id = getId()
        games.add(game)
        logAll()
    }

    override fun update(game: GameModel) {
        val foundGame: GameModel? = games.find { g -> g.id == game.id }
        if (foundGame != null) {
            foundGame.title = game.title
            foundGame.ageRating = game.ageRating
            foundGame.platform = game.platform
            foundGame.genre = game.genre
            foundGame.releaseDate = game.releaseDate
            foundGame.status = game.status
            logAll()
        }
    }

    fun logAll() {
        games.forEach{ i("$it") }
    }
}