package ie.setu.mobileappdevelopmentca.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class GameFirestoreStore {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val games = mutableListOf<GameModel>() //Read and Write unlike List which is read-only

    //Retrieves the current user's games collection based off of auth user id.
    private fun userGamesCollection() =
        db.collection("users")
            .document(auth.currentUser!!.uid) // !! is to prevent a null value
            .collection("games")


    fun fetchAll(onResult: (List<GameModel>) -> Unit) {
        // Function called when Firestore data is available.
        // When it is available do something depending on success or fail.
    userGamesCollection()
            .get()
            .addOnSuccessListener { gamesCollection ->
                val games = gamesCollection.toObjects(GameModel::class.java)
                onResult(games)
            }
            .addOnFailureListener { e ->
                onResult(emptyList())
            }
    }

    fun create(game: GameModel) {
        game.id = generateRandomId()
        userGamesCollection()
            .document(game.id.toString())
            .set(game) //The actual addition to Firestore.
    }

    fun update(game: GameModel) {
        userGamesCollection()
            .document(game.id.toString())
            .set(game) //Overwrite / "update" game
    }

    fun delete(game: GameModel) {
        userGamesCollection()
            .document(game.id.toString())
            .delete()
    }
}

//References:
// https://firebase.google.com/docs/firestore/manage-data/add-data
