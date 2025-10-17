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

    // List of options for platform and genre
    val ageRatingOptions = arrayOf(3, 7, 12, 15, 18, 21, "PG", "M", "T")
    val platformOptions = arrayOf("PC", "Xbox", "PlayStation", "Nintendo Switch", "Mobile")
    val genreOptions =
        arrayOf("Action", "Adventure", "RPG", "Simulation", "Strategy", "Shooter", "Horror")
    val statusOptions = arrayOf("Currently Playing", "Completed", "All")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //These come first
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp
        i("Game Activity started...")

        var edit = false

        if (intent.hasExtra("game_edit")) {
            edit = true
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

        binding.btnAdd.setOnClickListener() {
            game.title = binding.gameTitle.text.toString()
            game.ageRating =
                binding.gameAgeRating.text.toString().toIntOrNull() ?: 0 //Defaults to 0
            game.platform = arrayOf(binding.gamePlatform.text.toString())
            game.genre = arrayOf(binding.gameGenre.text.toString())

            val formatter = SimpleDateFormat("dd/MM/yyyy")
            game.releaseDate = formatter.parse(binding.gameReleaseDate.text.toString())

            game.status = binding.gameStatus.text.toString()


            if (game.title.isNotEmpty()) {
                if(edit){
                    app.games.update(game.copy())
                    i("Updated Game: ${game}")
                }
                else if(!edit) {
                    app.games.create(game.copy())
                    i("add Button Pressed: ${game}")
                }
                setResult(RESULT_OK)
                finish()
            }
        }

        binding.btnDelete.setOnClickListener {
            app.games.delete(game)
            i("Deleted Game: ${game.title}")
            setResult(RESULT_OK)
            finish()
        }

    }
}