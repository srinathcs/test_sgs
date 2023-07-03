package com.example.myapplication1.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication1.R
import com.example.myapplication1.Resources
import com.example.myapplication1.databinding.FragmentFourBinding
import com.example.myapplication1.repository.TestRepository
import com.example.myapplication1.viewmodel.TestViewModel
import com.example.myapplication1.viewmodel.TestViewModelFactory

class FourFragment : Fragment() {

    private lateinit var binding: FragmentFourBinding
    private lateinit var testViewModel: TestViewModel
    private var vehicleId = ""
    private var vehicleList: MutableList<String> = mutableListOf()
    private var vehicleIdList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFourBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repos = TestRepository()
        val factory = TestViewModelFactory(repos)
        testViewModel = ViewModelProvider(requireActivity(), factory)[TestViewModel::class.java]
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
                                requireContext(),
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
}
