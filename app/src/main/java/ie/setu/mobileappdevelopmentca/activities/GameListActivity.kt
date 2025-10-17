package ie.setu.mobileappdevelopmentca.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.mobileappdevelopmentca.R
import ie.setu.mobileappdevelopmentca.adapters.GameAdapter
import ie.setu.mobileappdevelopmentca.adapters.GameListener
import ie.setu.mobileappdevelopmentca.databinding.ActivityGameListBinding
import ie.setu.mobileappdevelopmentca.main.MainApp
import ie.setu.mobileappdevelopmentca.models.GameModel

class GameListActivity : AppCompatActivity(), GameListener {

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

        val listType = intent.getStringExtra("list_type") ?: "All"

        val gamesToDisplay = when (listType) {
            "Currently Playing" -> app.games.findAll().filter { it.status == "Currently Playing" }
            "Completed" -> app.games.findAll().filter { it.status == "Completed" }
            else -> app.games.findAll()
        }

        binding.toolbar.title = listType
        binding.recyclerView.adapter = GameAdapter(gamesToDisplay, this)
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
        finish() // closes this activity and goes back to the previous one
        return true
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.games.findAll().size)
            }
        }

    override fun onGameClick(game: GameModel) {
        val launcherIntent = Intent(this, GameActivity::class.java)
        launcherIntent.putExtra("game_edit", game)
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.games.findAll().size)
            }
        }





}



