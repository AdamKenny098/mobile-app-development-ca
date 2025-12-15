package ie.setu.mobileappdevelopmentca.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.mobileappdevelopmentca.databinding.CardGameBinding
import ie.setu.mobileappdevelopmentca.models.GameModel
import java.text.SimpleDateFormat
import java.util.Locale

interface GameListener {
    fun onGameClick(game: GameModel)
}


class GameAdapter constructor(private var games: List<GameModel>,
private val listener: GameListener) :
    RecyclerView.Adapter<GameAdapter.MainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardGameBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)


        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val game = games[holder.adapterPosition]
        holder.bind(game, listener)
    }

    override fun getItemCount(): Int = games.size

    class MainHolder(private val binding: CardGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(game: GameModel, listener: GameListener) {
            binding.gameTitle.text = game.title

            binding.gameGenre.text = if (game.genre.isNotEmpty()) {
                game.genre.joinToString(", ")
            } else {
                "Unknown Genre"
            }

            binding.gamePlatform.text = if (game.platform.isNotEmpty()) {
                game.platform.joinToString(", ")
            } else {
                "Unknown Platform"
            }

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.gameReleaseDate.text = dateFormat.format(game.releaseDate)

            binding.gameAgeRating.text = if (game.ageRating > 0) {
                "Age Rating: ${game.ageRating}+"
            } else {
                "N/A"
            }

            binding.root.setOnClickListener { listener.onGameClick(game) }



        }
    }

    fun update(newGames: List<GameModel>) {
        games = newGames
        notifyDataSetChanged() //refreshes RecyclerView
    }

}
