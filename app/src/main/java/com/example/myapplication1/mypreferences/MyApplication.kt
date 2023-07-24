package com.example.myapplication1.mypreferences

import android.app.Application
import com.yariksoffice.lingver.Lingver
import java.util.Locale

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Lingver.init(this, getDefaultLocale())
    }

    private fun getDefaultLocale(): Locale {
        return Locale.getDefault()
    }
}