package org.setu.placemark.console.main.controllers

import mu.KotlinLogging
import org.setu.placemark.console.main.models.GameJSONStore
import org.setu.placemark.console.main.models.GameModel
import org.setu.placemark.console.main.views.GameView

class GameController {

    val games = GameJSONStore()

    val gameView = GameView()
    val logger = KotlinLogging.logger {}

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

    fun delete() {
        gameView.listGames(games)
        var searchId = gameView.getId()
        val aGame = search(searchId)

        if(aGame != null) {
            games.delete(aGame)
            println("Game Deleted...")
        }
        else
            println("Game Not Deleted...")
    }


    fun search() {
        val aGame = search(gameView.getId())!!
        gameView.showGame(aGame)
    }

    fun search(id: Long) : GameModel? {
        val foundGame = games.findOne(id)
        return foundGame
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
                5 -> delete()
                -1 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != -1)
    }
}
