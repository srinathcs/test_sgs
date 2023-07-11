package com.example.myapplication1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication1.dao.UserDao
import com.example.myapplication1.database.AppDatabase
import com.example.myapplication1.databinding.FragmentSixBinding
import com.example.myapplication1.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SixFragment : Fragment() {

    private lateinit var binding: FragmentSixBinding
    private lateinit var userDao: UserDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSixBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDao = AppDatabase.DatabaseProvider.getDatabase(requireContext()).userDao()

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val user = User(0, username, password)
                        userDao.insertUser(user)
                    }
                    Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                lifecycleScope.launch {
                    val user = withContext(Dispatchers.IO) {
                        userDao.getUser(username, password)
                    }
                    if (user != null) {
                        Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Login failed, please register first",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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
            val password = binding.etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        userDao.updateUserPassword(username, password)
                    }
                    Toast.makeText(requireContext(), "Password updated", Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.btnDelete.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                lifecycleScope.launch {
                    val user = withContext(Dispatchers.IO) {
                        userDao.getUser(username, password)
                    }
                    if (user != null) {
                        withContext(Dispatchers.IO) {
                            userDao.deleteUser(user)
                        }
                        Toast.makeText(requireContext(), "User deleted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}