package edu.udb.taller2_rv180800

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class activity_splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val intent = Intent(this@activity_splash, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }, 9000) // espera 2 segundos antes de pasar a MainActivity
    }
}