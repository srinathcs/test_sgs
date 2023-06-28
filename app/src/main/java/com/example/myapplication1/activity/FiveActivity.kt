package com.example.myapplication1.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.mypreferences.MyPreferences
import com.example.myapplication1.databinding.ActivityFiveBinding

class FiveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFiveBinding
    private lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        myPreferences = MyPreferences(this)
        binding.btnSave.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            myPreferences.saveString("username", username)
            myPreferences.saveString("password", password)
        }
        val savedUsername = myPreferences.getString("username", "")
        val savedPassword = myPreferences.getString("password", "")
        binding.etUsername.setText(savedUsername)
        binding.etPassword.setText(savedPassword)
        binding.btnNext.setOnClickListener {
            Log.i("TAG", "Clicked")
            val intent = Intent(applicationContext, SixActivity::class.java)
            startActivity(intent)
        }
    }
}