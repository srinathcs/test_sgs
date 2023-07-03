package com.example.myapplication1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication1.mypreferences.MyPreferences
import com.example.myapplication1.databinding.FragmentFiveBinding

class FiveFragment : Fragment() {

    private lateinit var binding: FragmentFiveBinding
    private lateinit var myPreferences: MyPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        myPreferences = MyPreferences(requireContext())
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

    }
}
