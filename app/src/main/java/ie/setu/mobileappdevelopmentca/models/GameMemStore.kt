package ie.setu.mobileappdevelopmentca.models

import timber.log.Timber.i


class GameMemStore : GameStore {

    val games = ArrayList<GameModel>()

    override fun findAll(): List<GameModel> {
        return games
    }

    override fun create(game: GameModel) {
        games.add(game)
        logAll()
    }

    fun logAll() {
        games.forEach{ i("$it") }
    }
}