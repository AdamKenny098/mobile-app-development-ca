package org.setu.placemark.console.main.controllers

import mu.KotlinLogging
import GameMemStore
import org.setu.placemark.console.main.models.GameModel
import org.setu.placemark.console.main.views.GameView

class GameController {

    val games = GameMemStore()
    val gameView = GameView()
    val logger = KotlinLogging.logger {}

    fun menu() :Int { return gameView.menu() }

    fun add(){
        val aGame = GameModel()

        if (gameView.addGameData(aGame))
            games.create(aGame)
        else
            logger.info("Game Not Added")
    }

    fun list() {
        gameView.listGames(games)
    }

    fun update() {

        gameView.listGames(games)
        val searchId = gameView.getId()
        val aGame = search(searchId)

        if(aGame != null) {
            if(gameView.updateGameData(aGame as GameModel)) {
                games.update(aGame)
                gameView.showGame(aGame)
                logger.info("Game Updated : [ $aGame ]")
            }
            else
                logger.info("Game Not Updated")
        }
        else
            println("Game Not Updated...")
    }

    fun search() {
        val aGame = search(gameView.getId())!!
        gameView.showGame(aGame)
    }

    fun search(id: Long) : GameModel? {
        val foundGame = games.findOne(id)
        return foundGame
    }

    fun dummyData() {
        games.create(GameModel(1, "Overwatch", "A first person team based hero shooter", 12))
        games.create(GameModel(2, "Fallout 4", "A post nuclear apocalypse survival RPG", 18))
        games.create(GameModel(3, "Snake", "A classic", 3))
    }


    fun start() {
        var input: Int

        do {
            input = gameView.menu()
            when (input) {
                1 -> add()
                2 -> update()
                3 -> list()
                4 -> search()
                -1 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != -1)
    }
}
