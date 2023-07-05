package com.example.myapplication1.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.myapplication1.R

class FirstSplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY:Long= 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent=Intent(this,NewActivity::class.java)
            startActivity(intent)
            finish()
        },SPLASH_DELAY)
    }
}
