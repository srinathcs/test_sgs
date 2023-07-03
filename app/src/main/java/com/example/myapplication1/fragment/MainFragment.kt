package com.example.myapplication1.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication1.Resources
import com.example.myapplication1.databinding.FragmentMainBinding
import com.example.myapplication1.repository.TestRepository
import com.example.myapplication1.viewmodel.TestViewModel
import com.example.myapplication1.viewmodel.TestViewModelFactory

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var testViewModel: TestViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repos = TestRepository()
        val factory = TestViewModelFactory(repos)
        testViewModel = ViewModelProvider(this, factory).get(TestViewModel::class.java)
        initView()
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
                    }
                }
            }
        }
    }

}
