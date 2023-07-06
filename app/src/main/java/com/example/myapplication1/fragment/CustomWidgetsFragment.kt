package com.example.myapplication1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication1.databinding.FragmentCustomWidgetsBinding
import com.example.myapplication1.holder.CustomEditTextDataHolder
import com.example.myapplication1.widgets.customToast.CustomToast
import com.example.myapplication1.widgets.customToast.ToastType


class CustomWidgetsFragment : Fragment() {
    private lateinit var binding: FragmentCustomWidgetsBinding
    private lateinit var dataHolder: CustomEditTextDataHolder
    private var toastWidget: CustomToast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataHolder = CustomEditTextDataHolder(requireContext())
        binding = FragmentCustomWidgetsBinding.inflate(inflater, container, false)
        toastWidget = CustomToast(requireActivity())

        binding.cetName.setupDataModel(dataHolder.getInputText())
        binding.cetEmail.setupDataModel(dataHolder.getInputEmail())
        binding.btnLogin.setOnClickListener {
            val name = binding.cetName.getText()
            val email = binding.cetEmail.getText()

            if (name.isEmpty() || email.isEmpty()) {
                toastWidget?.showToast("please fill all the fields", ToastType.ERROR)
            } else {
                toastWidget?.showToast("This is my Custom Toast", ToastType.SUCCESS)
            }

        }
        return binding.root

    }
}
