package org.setu.placemark.console.main

import mu.KotlinLogging
import org.setu.placemark.console.main.models.GameModel

private val logger = KotlinLogging.logger {}

val games = ArrayList<GameModel>()

fun main() {
    logger.info { "Launching Game Console App" }
    println("Game Kotlin App Version 2.0")

    var input: Int

    do {
        input = menu()
        when(input) {
            1 -> addGame()
            2 -> updateGame()
            3 -> listGames()
            4 -> searchGame()
            -99 -> dummyData()
            -1 -> println("Exiting App")
            else -> println("Invalid Option")
        }
        println()
    } while (input != -1)
    logger.info { "Shutting Down Game Console App" }
}

fun menu() : Int {
    var option : Int
    var input: String?

    println("MAIN MENU")
    println(" 1. Add Game")
    println(" 2. Update Game")
    println(" 3. List All Games")
    println(" 4. Search Games")
    println("-1. Exit")
    println()
    print("Enter Option : ")
    input = readLine()!!
    option = if (input.toIntOrNull() != null && input.isNotEmpty())
        input.toInt()
    else
        -9
    return option
}

fun addGame(){
    var aGame = GameModel()
    println("Add Game")
    println()
    print("Enter a Title : ")
    aGame.title = readLine()!!
    print("Enter a Description : ")
    aGame.description = readLine()!!
    print("Enter an Age Rating (number) : ")
    aGame.ageRating = readLine()!!.toIntOrNull() ?: 0

    if (aGame.title.isNotEmpty() && aGame.description.isNotEmpty()) {
        aGame.id = games.size.toLong()
        games.add(aGame.copy())
        logger.info("Game Added : [ $aGame ]")
    }
    else
        logger.info("Game Not Added")
}

fun updateGame() {
    println("Update Game")
    println()
    listGames()
    var searchId = getId()
    val aGame = search(searchId)

    if(aGame != null) {
        print("Enter a new Title for [ " + aGame.title + " ] : ")
        aGame.title = readLine()!!
        print("Enter a new Description for [ " + aGame.description + " ] : ")
        aGame.description = readLine()!!
        print("Enter a new Age Rating for [ " + aGame.ageRating + " ] : ")
        aGame.ageRating = readLine()!!.toIntOrNull() ?: aGame.ageRating

        println(
            "You updated [ " + aGame.title + " ] for title, " +
                    "[ " + aGame.description + " ] for description, " +
                    "and [ " + aGame.ageRating + " ] for age rating"
        )
    }
    else
        println("Game Not Updated...")
}

fun listGames() {
    println("List All Games")
    println()
    games.forEach { println("ID: ${it.id}, Title: ${it.title}, Desc: ${it.description}, Age Rating: ${it.ageRating}") }
}

fun searchGame() {
    var searchId = getId()
    val aGame = search(searchId)

    if(aGame != null)
        println("Game Details [ ID: ${aGame.id}, Title: ${aGame.title}, Desc: ${aGame.description}, Age Rating: ${aGame.ageRating} ]")
    else
        println("Game Not Found...")
}

fun getId() : Long {
    var strId : String? // String to hold user input
    var searchId : Long // Long to hold converted id
    print("Enter id to Search/Update : ")
    strId = readLine()!!
    searchId = if (strId.toLongOrNull() != null && strId.isNotEmpty())
        strId.toLong()
    else
        -9
    return searchId
}

fun search(id: Long) : GameModel? {
    var foundGame: GameModel? = games.find { g -> g.id == id }
    return foundGame
}

fun dummyData() {
    games.add(GameModel(1, "Overwatch", "A first person team based hero shooter", 12))
    games.add(GameModel(2, "Fallout 4", "A post nuclear apocalypse survival RPG", 18))
    games.add(GameModel(3, "Snake", "A classic", 3))
}
