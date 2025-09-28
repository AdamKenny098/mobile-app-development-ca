package ie.setu.mobileappdevelopmentca

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.setu.mobileappdevelopmentca.databinding.ActivityGameBinding
import ie.setu.mobileappdevelopmentca.models.GameModel
import timber.log.Timber
import timber.log.Timber.i
import java.time.LocalDateTime
import java.util.Calendar

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    var game = GameModel(
        title = "Elden Ring",
        ageRating = 18,
        platform = arrayOf("PC", "Xbox", "PlayStation"),
        genre = arrayOf("RPG", "Open World", "Action"),
        releaseDate = Calendar.getInstance().apply { set(2022, 1, 25) }.time
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Placemark Activity started...")

        binding.btnAdd.setOnClickListener() {
            game.title = binding.placemarkTitle.text.toString()
            if (game.title.isNotEmpty()) {
                i("add Button Pressed: $game.title")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}