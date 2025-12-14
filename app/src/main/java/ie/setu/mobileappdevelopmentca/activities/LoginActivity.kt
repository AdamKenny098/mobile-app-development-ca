package ie.setu.mobileappdevelopmentca.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ie.setu.mobileappdevelopmentca.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (email.isEmpty()) {
                binding.emailInput.error = "Email is required"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.passwordInput.error = "Password is required"
                return@setOnClickListener
            }


            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, go to main menu
                        val intent = Intent()
                        intent.setClassName(
                            this,
                            "ie.setu.mobileappdevelopmentca.activities.MainMenuActivity"
                        )
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        binding.passwordInput.error = "Login failed"
                        binding.passwordInput.text.clear()

                    }
                }
        }

        binding.registerLink.setOnClickListener {
            val intent = Intent()
            intent.setClassName(
                this,
                "ie.setu.mobileappdevelopmentca.activities.RegisterActivity"
            )
            startActivity(intent)
        }

    }
}