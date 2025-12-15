package ie.setu.mobileappdevelopmentca.activities

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ie.setu.mobileappdevelopmentca.databinding.ActivityGameBinding
import ie.setu.mobileappdevelopmentca.main.MainApp
import ie.setu.mobileappdevelopmentca.models.GameModel
import timber.log.Timber.i
import java.util.Calendar
import java.text.SimpleDateFormat

import android.app.DatePickerDialog
import android.view.View

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    lateinit var app: MainApp
    private var game = GameModel()
    private var edit = false

    // List of options for platform and genre
    val ageRatingOptions = arrayOf("3", "7", "12", "15", "18", "21")
    val platformOptions = arrayOf("PC", "Xbox", "PlayStation", "Nintendo Switch", "Mobile")
    val genreOptions =
        arrayOf("Action", "Adventure", "RPG", "Simulation", "Strategy", "Shooter", "Horror")
    val statusOptions = arrayOf("Currently Playing", "Completed")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //These come first
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        edit = intent.hasExtra("game_edit")
        setSupportActionBar(binding.toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (edit) "Edit Game" else "Add Game"

        i("Game Activity started...")

        if (edit) {
            game = intent.extras?.getParcelable("game_edit")!!

            binding.gameTitle.setText(game.title)
            binding.gameAgeRating.setText(game.ageRating.toString())
            binding.gamePlatform.setText(game.platform.joinToString(", "))
            binding.gameGenre.setText(game.genre.joinToString(", "))
            binding.gameReleaseDate.setText(SimpleDateFormat("dd/MM/yyyy").format(game.releaseDate))
            binding.gameStatus.setText(game.status)
            binding.btnAdd.text = "Save Changes"
            binding.btnDelete.visibility = View.VISIBLE
        } else {
            binding.btnDelete.visibility = View.GONE
        }

        binding.gameAgeRating.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose an Age Rating")
            builder.setItems(ageRatingOptions) { _, x ->
                // Set chosen item to the text field
                binding.gameAgeRating.setText(ageRatingOptions[x])

            }
            builder.show()
        }


        // PLATFORM PICKER
        binding.gamePlatform.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose a Platform")
            builder.setItems(platformOptions) { _, x ->
                // Set chosen item to the text field
                binding.gamePlatform.setText(platformOptions[x])
            }
            builder.show()
        }

        // GENRE PICKER
        binding.gameGenre.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose a Genre")
            builder.setItems(genreOptions) { _, x ->
                // Set chosen item to the text field
                binding.gameGenre.setText(genreOptions[x])
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
                    val dateText = String.format(
                        "%02d/%02d/%04d",
                        selectedDay,
                        selectedMonth + 1,
                        selectedYear
                    )
                    binding.gameReleaseDate.setText(dateText)
                },
                year,
                month,
                day
            )

            datePicker.show()
        }


        binding.gameStatus.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose a Status")
            builder.setItems(statusOptions) { _, x ->
                binding.gameStatus.setText(statusOptions[x])
            }
            builder.show()
        }

        binding.btnAdd.setOnClickListener()
        {
            val title = binding.gameTitle.text.toString().trim()
            if (title.isEmpty()) {
                binding.gameTitle.error = "Title is required"
                return@setOnClickListener
            }

            val ageText = binding.gameAgeRating.text.toString().trim()
            if (ageText.isEmpty()) {
                binding.gameAgeRating.error = "Age rating must be selected"
                return@setOnClickListener
            }

            val platformText = binding.gamePlatform.text.toString().trim()
            if (platformText.isEmpty()) {
                binding.gamePlatform.error = "Platform must be selected"
                return@setOnClickListener
            }

            val genreText = binding.gameGenre.text.toString().trim()
            if (genreText.isEmpty()) {
                binding.gameGenre.error = "Genre must be selected"
                return@setOnClickListener
            }

            val statusText = binding.gameStatus.text.toString().trim()
            if (statusText.isEmpty()) {
                binding.gameStatus.error = "Status must be selected"
                return@setOnClickListener
            }

            val dateText = binding.gameReleaseDate.text.toString().trim()
            if (dateText.isEmpty()) {
                binding.gameReleaseDate.error = "Release date must be selected"
                return@setOnClickListener
            }

            game.title = title
            game.ageRating = ageText.toInt()
            game.platform = listOf(platformText)
            game.genre = listOf(genreText)
            game.status = statusText
            game.releaseDate = SimpleDateFormat("dd/MM/yyyy").parse(dateText)

            if (edit) {
                app.firestoreGames.update(game)
            } else {
                app.firestoreGames.create(game)
            }

            setResult(RESULT_OK)
            finish()
        }

        binding.btnDelete.setOnClickListener {
            app.firestoreGames.delete(game)
            setResult(RESULT_OK)
            finish()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        setResult(RESULT_CANCELED) // treat as cancel
        finish()
        return true
    }

}