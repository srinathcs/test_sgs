package com.example.myapplication1.fragment.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myapplication1.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private val notificationViewModel: NotificationViewModel by viewModels {
        NotificationViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClick.setOnClickListener {
            notificationViewModel.onButtonClick()
        }
    }
}