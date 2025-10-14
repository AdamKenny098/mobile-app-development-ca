package ie.setu.mobileappdevelopmentca.models

interface GameStore {
    fun findAll(): List<GameModel>
    fun create(placemark: GameModel)
}