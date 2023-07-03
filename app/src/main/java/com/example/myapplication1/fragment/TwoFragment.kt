package com.example.myapplication1.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication1.RecycleAdapter
import com.example.myapplication1.Resources
import com.example.myapplication1.databinding.FragmentTwoBinding
import com.example.myapplication1.repository.TestRepository
import com.example.myapplication1.viewmodel.TestViewModel
import com.example.myapplication1.viewmodel.TestViewModelFactory

class TwoFragment : Fragment() {

    private lateinit var binding: FragmentTwoBinding
    private lateinit var testViewModel: TestViewModel
    private lateinit var myadapter: RecycleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTwoBinding.inflate(inflater, container, false)
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
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            testViewModel.dataFlow2.collect { it ->
                when (it) {
                    is Resources.Error -> {
                        Log.i("TAG", "validate for error: ${it.message.toString()}")
                    }
                    is Resources.Loading -> {
                        // Handle loading state if needed
                    }
                    is Resources.Success -> {
                        Log.i("TAG", "validationStatus2: ${it.data}")
                        myadapter = RecycleAdapter(requireContext())
                        binding.rvView.adapter = myadapter
                        binding.rvView.layoutManager = LinearLayoutManager(requireContext())
                        myadapter.differ.submitList(it.data)
                        //setOnClick()
                    }
                }
            }
        }
    }

}
