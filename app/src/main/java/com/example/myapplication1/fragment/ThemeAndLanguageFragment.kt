package com.example.myapplication1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentThemeAndLanguageBinding
import com.example.myapplication1.mypreferences.SettingPreference
import com.yariksoffice.lingver.Lingver

class ThemeAndLanguageFragment : Fragment(R.layout.fragment_theme_and_language) {

    private lateinit var binding: FragmentThemeAndLanguageBinding
    private lateinit var settingPreference: SettingPreference
    private lateinit var lightdarkmodeBinding: FragmentThemeAndLanguageBinding
    private lateinit var alertDialog: android.app.AlertDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThemeAndLanguageBinding.bind(view)
        setAppLang()
        getAppLanguage()
        alertDialog = android.app.AlertDialog.Builder(requireContext()).create()
        settingPreference = SettingPreference(requireContext())
        binding.themes.setOnClickListener {
            themes()
        }

    }

    private fun setAppLang() {

        binding.tamil.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                settingPreference.setAppLang("ta")
            }
            Lingver.getInstance().setLocale(requireActivity(), "ta")
            requireActivity().recreate()
        }

        binding.english.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                settingPreference.setAppLang("en")
            }
            Lingver.getInstance().setLocale(requireActivity(), "en")
            requireActivity().recreate()

        }

    }

    private fun getAppLanguage() {

        lifecycleScope.launchWhenStarted {
            settingPreference.getAppLang().collect {
                binding.tamil.isChecked = it == "ta"
                binding.english.isChecked = it == "en"
            }
        }

    }

    private fun themes() {
        val layout = layoutInflater.inflate(R.layout.fragment_theme_and_language, null)
        lightdarkmodeBinding = FragmentThemeAndLanguageBinding.bind(layout)
        alertDialog.setView(layout)
        alertDialog.show()

        lightdarkmodeBinding.themes.setOnCheckedChangeListener { _, CheckedId ->
            when (CheckedId) {
                true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }

        }

        lightdarkmodeBinding.close.setOnClickListener {
            alertDialog.dismiss()
        }
    }
}