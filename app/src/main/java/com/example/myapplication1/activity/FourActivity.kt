package com.example.myapplication1.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication1.R
import com.example.myapplication1.Resources
import com.example.myapplication1.databinding.ActivityFourBinding
import com.example.myapplication1.repository.TestRepository
import com.example.myapplication1.viewmodel.TestViewModel
import com.example.myapplication1.viewmodel.TestViewModelFactory

class FourActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFourBinding
    private lateinit var testViewModel: TestViewModel
    private var vehicleId = ""
    private var vehicleList: MutableList<String> = mutableListOf()
    private var vehicleIdList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repos = TestRepository()
        val factory = TestViewModelFactory(repos)
        testViewModel = ViewModelProvider(this, factory)[TestViewModel::class.java]
        initView()
    }

    private fun initView() {
        lifecycleScope.launchWhenStarted {
            testViewModel.complete(
                "13",
                "2",
                "13051305",
                "555555",
                "222",
                "456122",
                "501"
            )
        }
        status2()
        onClick()
    }

    private fun status2() {
        lifecycleScope.launchWhenStarted {

            testViewModel.completeFlow2.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {
                        Log.i("TAG", "setVehicleError: ${it.data}")

                    }

                    is Resources.Success -> {
                        Log.i("TAG", "setVehicle: ${it.data}")
                        try {
                            vehicleList.clear()
                            vehicleIdList.clear()
                            for (i in it.data!!) {
                                vehicleList.add(i.name)
                                vehicleIdList.add(i.value)

                            }
                            val arrayAdapter = ArrayAdapter(
                                this@FourActivity,
                                R.layout.complete_text_view,
                                vehicleList
                            )
                            binding.area.setAdapter(arrayAdapter)
                            binding.area.threshold = 1

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        binding.area.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            for (i in 0 until vehicleList.size) {

                try {
                    if (binding.area.text.toString() == vehicleList[i]) {
                        vehicleId = vehicleIdList[i]
                        Log.i("TAG", "forsalessss:$vehicleId")
                    }

                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun onClick(){
        binding.btnNext.setOnClickListener {
            intent = Intent(applicationContext, FiveActivity::class.java)
            startActivity(intent)
        }
    }
}