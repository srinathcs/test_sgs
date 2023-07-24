package com.example.myapplication1.mypreferences

class SettingsRepository (private val settingsPreference: SettingsPreference, private val mainPreference: MainPreference) {


    suspend fun setAppTheme(value: String) = settingsPreference.setAppTheme(value)

    fun getAppTheme() = settingsPreference.getAppTheme()

    suspend fun saveIconStatus(value: String) = mainPreference.saveIconStatus(value)

}