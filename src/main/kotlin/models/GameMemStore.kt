import org.setu.placemark.console.main.models.GameModel
import org.setu.placemark.console.main.models.GameStore

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class GameMemStore : GameStore {

    val games = ArrayList<GameModel>()

    override fun findAll(): List<GameModel> {
        return games
    }

    override fun findOne(id: Long) : GameModel? {
        var foundGame: GameModel? = games.find { p -> p.id == id }
        return foundGame
    }

    override fun create(game: GameModel) {
        game.id = getId()
        games.add(game)
        logAll()
    }

    override fun update(game: GameModel) {
        var foundGame = findOne(game.id!!)
        if (foundGame != null) {
            foundGame.title = game.title
            foundGame.description = game.description
        }
    }

    internal fun logAll() {
        games.forEach { println("${it}") }
    }
}
