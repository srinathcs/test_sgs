package com.example.myapplication1.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication1.RecycleAdapter
import com.example.myapplication1.Resources
import com.example.myapplication1.databinding.ActivityTwoBinding
import com.example.myapplication1.repository.TestRepository
import com.example.myapplication1.viewmodel.TestViewModel
import com.example.myapplication1.viewmodel.TestViewModelFactory

class TwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTwoBinding
    private lateinit var testViewModel: TestViewModel
    private lateinit var myadapter: RecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repos = TestRepository()
        val factory = TestViewModelFactory(repos)
        testViewModel = ViewModelProvider(this, factory)[TestViewModel::class.java]
        initView()
    }

    private fun initView() {
        lifecycleScope.launchWhenCreated {
            testViewModel.dataFlow(
                "18",
                "2",
                "501",
                "11.578",
                "77.1234",
                "device",
                "13051305",
                "501"
            )
            showDataTwo()

        }
    }

    private fun showDataTwo() {
        lifecycleScope.launchWhenStarted {
            testViewModel.dataFlow2.collect {

                when (it) {

                    is Resources.Error -> {

                        Log.i("TAG", "validate for error: ${it.message.toString()}")
                    }

                    is Resources.Loading -> {

                    }

                    is Resources.Success -> {
                        Log.i("TAG", "validationStatus2: ${it.data}")

                        myadapter = RecycleAdapter(this@TwoActivity)
                        binding.rvView.adapter = myadapter
                        binding.rvView.layoutManager = LinearLayoutManager(this@TwoActivity)
                        myadapter.differ.submitList(it.data)
                        setOnCLick()
                    }
                }
            }
        }
    }

    private fun setOnCLick() {
        binding.btnNext.setOnClickListener {
            intent = Intent(applicationContext, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}