package ie.setu.mobileappdevelopmentca.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ie.setu.mobileappdevelopmentca.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // “All Games” button → Launch GameListActivity
        binding.btnAllGames.setOnClickListener {
            val intent = Intent(this, GameListActivity::class.java)
            intent.putExtra("list_type", "All")
            startActivity(intent)
        }

        binding.btnPlaying.setOnClickListener {
            val intent = Intent(this, GameListActivity::class.java)
            intent.putExtra("list_type", "Currently Playing")
            startActivity(intent)
        }

        binding.btnCompleted.setOnClickListener {
            val intent = Intent(this, GameListActivity::class.java)
            intent.putExtra("list_type", "Completed")
            startActivity(intent)
        }

        binding.btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}
