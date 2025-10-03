package ie.setu.mobileappdevelopmentca.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.setu.mobileappdevelopmentca.databinding.ActivityGameBinding
import ie.setu.mobileappdevelopmentca.main.MainApp
import ie.setu.mobileappdevelopmentca.models.GameModel
import timber.log.Timber.i
import java.util.Calendar
import java.text.SimpleDateFormat

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    var game = GameModel(
        title = "Elden Ring",
        ageRating = 18,
        platform = arrayOf("PC", "Xbox", "PlayStation"),
        genre = arrayOf("RPG", "Open World", "Action"),
        releaseDate = Calendar.getInstance().apply { set(2022, 1, 25) }.time
    )

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Game Activity started...")

        binding.btnAdd.setOnClickListener() {
            game.title = binding.gameTitle.text.toString()
            game.ageRating = binding.gameAgeRating.text.toString().toInt()
            game.platform = arrayOf(binding.gamePlatform.text.toString())
            game.genre = arrayOf(binding.gameGenre.text.toString())

            val releaseDateString = binding.gameReleaseDate.text.toString()
            val formatter = SimpleDateFormat("dd/mm/yyyy")
            game.releaseDate = formatter.parse(releaseDateString)

            if (game.title.isNotEmpty()) {
                app!!.games.add(game.copy())
                i("add Button Pressed: ${game}")
                for (i in app!!.games.indices)
                {
                    i("Placemark[$i]:${this.app!!.games[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }

            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }


}