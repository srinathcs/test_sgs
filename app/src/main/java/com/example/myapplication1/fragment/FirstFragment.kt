package com.example.myapplication1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFirstBinding.bind(view)

        binding.btnSend.setOnClickListener {
            val name = binding.etName.text.toString()
            val ageString = binding.etAge.text.toString()
            if (name.isNotEmpty() && ageString.isNotEmpty()) {
                val age = ageString.toInt()
                val direction = FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                    name,
                    age
                )
                findNavController().navigate(direction)
            } else {
                if (name.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter a name", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "Please enter an age", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}