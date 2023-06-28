package com.example.myapplication1.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication1.dao.UserDao
import com.example.myapplication1.database.AppDatabase
import com.example.myapplication1.databinding.ActivitySixBinding
import com.example.myapplication1.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SixActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySixBinding
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySixBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        userDao = AppDatabase.DatabaseProvider.getDatabase(this).userDao()

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val user = User(0, username, password)
                    userDao.insertUser(user)
                }
                Toast.makeText(this@SixActivity, "Registration successful", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            lifecycleScope.launch {
                val user = withContext(Dispatchers.IO) {
                    userDao.getUser(username, password)
                }
                if (user != null) {
                    Toast.makeText(this@SixActivity, "Login successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this@SixActivity, "Login failed register first", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.btnShow.setOnClickListener {
            lifecycleScope.launch {
                val users = withContext(Dispatchers.IO) {
                    userDao.getAllUsers()
                }
                val stringBuilder = StringBuilder()
                for (user in users) {
                    stringBuilder.append("Username: ${user.username}, Password: ${user.password}\n")
                }
                binding.tvShow.text = stringBuilder.toString()
            }
        }

        binding.btnUpdate.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val newPassword = binding.etPassword.text.toString()
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    userDao.updateUserPassword(username, newPassword)
                }
                Toast.makeText(this@SixActivity, "Password updated", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            lifecycleScope.launch {
                val user = withContext(Dispatchers.IO) {
                    userDao.getUser(username, password)
                }
                if (user != null) {
                    withContext(Dispatchers.IO) {
                        userDao.deleteUser(user)
                    }
                    Toast.makeText(this@SixActivity, "User deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SixActivity, "User not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initView() {
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, SevenActivity::class.java)
            startActivity(intent)
        }
    }
}