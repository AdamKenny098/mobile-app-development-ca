package org.setu.placemark.console.main.views

import GameMemStore
import org.setu.placemark.console.main.models.GameModel

class GameView {

    fun menu() : Int {

        val option : Int
        val input: String?

        println("MAIN MENU")
        println(" 1. Add Game")
        println(" 2. Update Game")
        println(" 3. List All Game")
        println(" 4. Search Game")
        println("-1. Exit")
        println()
        print("Enter Option : ")
        input = readLine()!!
        option = if (input.toIntOrNull() != null && !input.isEmpty())
            input.toInt()
        else
            -9
        return option
    }

    fun listGames(games : GameMemStore) {
        println("List All Game")
        println()
        games.findAll().forEach { println("ID: ${it.id}, Title: ${it.title}, Desc: ${it.description}, Age Rating: ${it.ageRating}") }
        println()
    }

    fun showGame(game : GameModel) {
        if(game != null)
            println("Game Details [ ID: ${game.id}, Title: ${game.title}, Desc: ${game.description}, Age Rating: ${game.ageRating} ]")
        else
            println("Game Not Found...")
    }

    fun addGameData(game : GameModel) : Boolean {

        println()
        print("Enter a Title : ")
        game.title = readLine()!!
        print("Enter a Description : ")
        game.description = readLine()!!
        print("Enter an Age Rating (number) : ")
        game.ageRating = readLine()!!.toIntOrNull() ?: 0

        return game.title.isNotEmpty() && game.description.isNotEmpty() && game.ageRating != 0
    }

    fun updateGameData(game : GameModel) : Boolean {

        val tempTitle: String?
        val tempDescription: String?
        val tempAge: Int?

        if (game != null) {
            print("Enter a new Title for [ " + game.title + " ] : ")
            tempTitle = readLine()!!
            print("Enter a new Description for [ " + game.description + " ] : ")
            tempDescription = readLine()!!
            print("Enter a new Age Rating for [ " + game.ageRating + " ] : ")
            tempAge = readLine()!!.toIntOrNull()

            if (tempTitle.isNotEmpty() && tempDescription.isNotEmpty() && tempAge != null) {
                game.title = tempTitle
                game.description = tempDescription
                game.ageRating = tempAge
                return true
            }
        }
        return false
    }

    fun getId() : Long {
        var strId : String? // String to hold user input
        var searchId : Long // Long to hold converted id
        print("Enter id to Search/Update : ")
        strId = readLine()!!
        searchId = if (strId.toLongOrNull() != null && !strId.isEmpty())
            strId.toLong()
        else
            -9
        return searchId
    }
}
