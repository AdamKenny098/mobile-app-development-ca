package ie.setu.mobileappdevelopmentca.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.setu.mobileappdevelopmentca.R
import ie.setu.mobileappdevelopmentca.databinding.ActivityGameListBinding
import ie.setu.mobileappdevelopmentca.databinding.CardGameBinding
import ie.setu.mobileappdevelopmentca.main.MainApp
import ie.setu.mobileappdevelopmentca.models.GameModel
import java.text.SimpleDateFormat
import java.util.Locale

class GameListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityGameListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        setSupportActionBar(binding.toolbar)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // --- RecyclerView setup ---
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        // --- Filter buttons setup ---
        binding.btnAllGames.setOnClickListener {
            binding.toolbar.title = "All Games"
            binding.recyclerView.adapter = GameAdapter(app.games)
        }

        binding.btnPlaying.setOnClickListener {
            val filtered = app.games.filter { it.status == "Currently Playing" }
            binding.toolbar.title = "Currently Playing"
            binding.recyclerView.adapter = GameAdapter(filtered)
        }

        binding.btnCompleted.setOnClickListener {
            val filtered = app.games.filter { it.status == "Completed" }
            binding.toolbar.title = "Completed Games"
            binding.recyclerView.adapter = GameAdapter(filtered)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, GameActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Sets a back button out of GameListActivity back to MainMenuActivity
    override fun onSupportNavigateUp(): Boolean {
        finish() // closes this activity and returns to the main menu
        return true
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.games.size)
            }
        }


}


class GameAdapter constructor(private var games: List<GameModel>) :
    RecyclerView.Adapter<GameAdapter.MainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardGameBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)


        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val game = games[holder.adapterPosition]
        holder.bind(game)
    }

    override fun getItemCount(): Int = games.size

    class MainHolder(private val binding: CardGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(game: GameModel) {
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



        }
    }
}

