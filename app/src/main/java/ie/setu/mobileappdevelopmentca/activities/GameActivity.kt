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

import android.app.DatePickerDialog

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    // List of options for platform and genre
    val ageRatingOptions = arrayOf(3,7,12,15,18,21, "PG", "M", "T")
    val platformOptions = arrayOf("PC", "Xbox", "PlayStation", "Nintendo Switch", "Mobile")
    val genreOptions = arrayOf("Action", "Adventure", "RPG", "Simulation", "Strategy", "Shooter", "Horror")
    val statusOptions = arrayOf("Currently Playing", "Completed", "All")

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

        //These come first
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Game Activity started...")

        // PLATFORM PICKER
        binding.gamePlatform.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Choose a Platform")
            builder.setItems(platformOptions) { _, which ->
                // Set chosen item to the text field
                binding.gamePlatform.setText(platformOptions[which])
            }
            builder.show()
        }

        // GENRE PICKER
        binding.gameGenre.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Choose a Genre")
            builder.setItems(genreOptions) { _, which ->
                // Set chosen item to the text field
                binding.gameGenre.setText(genreOptions[which])
            }
            builder.show()
        }



        binding.gameReleaseDate.setOnClickListener {
            // Get today’s date as default
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Create the date picker dialog
            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Month is 0-indexed, so we add 1
                    val dateText = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                    binding.gameReleaseDate.setText(dateText)
                },
                year,
                month,
                day
            )

            datePicker.show()
        }


        binding.gameStatus.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Choose a Status")
            builder.setItems(statusOptions) { _, which ->
                binding.gameStatus.setText(statusOptions[which])
            }
            builder.show()
        }

        binding.btnAdd.setOnClickListener() {
            game.title = binding.gameTitle.text.toString()
            game.ageRating = binding.gameAgeRating.text.toString().toIntOrNull() ?: 0 //Defaults to 0
            game.platform = arrayOf(binding.gamePlatform.text.toString())
            game.genre = arrayOf(binding.gameGenre.text.toString())

            val formatter = SimpleDateFormat("dd/MM/yyyy")
            game.releaseDate = formatter.parse(binding.gameReleaseDate.text.toString())

            game.status = binding.gameStatus.text.toString()


            if (game.title.isNotEmpty()) {
                app!!.games.create(game.copy())
                i("add Button Pressed: ${game}")
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