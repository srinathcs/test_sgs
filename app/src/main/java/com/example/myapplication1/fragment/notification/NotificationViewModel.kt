package com.example.myapplication1.fragment.notification

import android.app.Activity
import androidx.lifecycle.ViewModel

class NotificationViewModel(activity: Activity) : ViewModel() {
    private val notificationHelper: NotificationHelper = NotificationHelper(activity)

    fun onButtonClick() {
        notificationHelper.createNotificationChannel()
        notificationHelper.showNotification()

    }
}
