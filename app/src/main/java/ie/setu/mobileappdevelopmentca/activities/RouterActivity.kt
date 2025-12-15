package ie.setu.mobileappdevelopmentca.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RouterActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()

        val intent = Intent()
        if (FirebaseAuth.getInstance().currentUser != null) {
            intent.setClassName(
                this,
                "ie.setu.mobileappdevelopmentca.activities.MainMenuActivity"
            )
        } else {
            intent.setClassName(
                this,
                "ie.setu.mobileappdevelopmentca.activities.LoginActivity"
            )
        }

        startActivity(intent)
        finish()
    }
}
