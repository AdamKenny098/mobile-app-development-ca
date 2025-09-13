package org.setu.placemark

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    logger.info { "Launching Gam Console App" }
    println("Game Kotlin App Version 1.0")

    var input: Int

    do {
        input = menu()
        when(input) {
            1 -> println("You Chose Add Game")
            -1 -> println("Exiting App")
            else -> println("Invalid Option")
        }
        println()
    } while (input != -1)
    logger.info { "Shutting Down Game Console App" }
}


fun menu():Int
{
    var option : Int
    var input: String? = null

    println("Main Menu")
    println(" 1. Add Game")
    println(" 2. Update Game")
    println(" 3. List All Games")
    println("-1. Exit")
    println()
    print("Enter an integer : ")
    input = readLine()!!
    option = if (input.toIntOrNull() != null && !input.isEmpty())
        input.toInt()
    else
        -9
    return option
}