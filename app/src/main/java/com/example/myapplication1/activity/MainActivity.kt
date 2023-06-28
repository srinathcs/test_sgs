package com.example.myapplication1.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication1.R
import com.example.myapplication1.Resources
import com.example.myapplication1.databinding.ActivityMainBinding
import com.example.myapplication1.repository.TestRepository
import com.example.myapplication1.viewmodel.TestViewModel
import com.example.myapplication1.viewmodel.TestViewModelFactory

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var testViewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setupToolbar("Main Activity")

        val repos = TestRepository()
        val factory = TestViewModelFactory(repos)
        testViewModel = ViewModelProvider(this, factory)[TestViewModel::class.java]

        initView()
    }

    override fun handleNavigationItemClick(itemId: Int) {
        // Handle navigation item clicks specific to MainActivity
        when (itemId) {
            R.id.dataApi -> {
                val intent = Intent(this, TwoActivity::class.java)
                startActivity(intent)
            }

            R.id.dataRecycler -> {
                val intent = Intent(this, ThirdActivity::class.java)
                startActivity(intent)

            }
            // ...
        }
    }

    private fun initView() {
        lifecycleScope.launchWhenCreated {
            testViewModel.data(
                "6",
                "1",
                "501",
                "11.578",
                "77.5789",
                "device",
                "13051305"
            )
            validationStatus()
        }
    }

    private fun validationStatus() {
        lifecycleScope.launchWhenStarted {
            testViewModel.testFlow2.collect {

                when (it) {

                    is Resources.Error -> {
                        Log.i("TAG", "validate for error: ${it.message.toString()}")
                    }

                    is Resources.Loading -> {

                    }

                    is Resources.Success -> {
                        Log.i("TAG", "validationStatus: ${it.data}")
                        binding.tvCoin.text = it.data!!.coin
                        binding.tvError.text = it.data.error
                        binding.tvErrorMsg.text = it.data.error_msg
                        setOnCLick()
                    }
                }
            }
        }
    }

    private fun setOnCLick() {
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, TwoActivity::class.java)
            startActivity(intent)
        }
    }
}