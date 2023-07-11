package com.example.myapplication1.fragment.notification

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavDeepLinkBuilder
import com.example.myapplication1.R
import com.example.myapplication1.activity.NewActivity

class NotificationHelper(private val activity: Activity) {

    private companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val NOTIFICATION_ID = 1
        // private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 123
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "My Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager =
                activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    val deepLink = NavDeepLinkBuilder(activity)
        .setGraph(R.navigation.data_passing_nav)
        .setDestination(R.id.firstFragment)
        .createPendingIntent()

    fun showNotification() {
        // Create the notification
        val builder = NotificationCompat.Builder(activity, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_thumb)
            .setContentTitle("Heads-up Notification")
            .setContentText("This is a heads-up notification message.")
            .setContentIntent(deepLink)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Create a full-screen intent
        val intent = Intent(activity, NewActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            activity,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        builder.setFullScreenIntent(pendingIntent, true)

        // Show the notification
        val notificationManager = NotificationManagerCompat.from(activity)
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    /* private fun hasNotificationPermission(): Boolean {
         return ContextCompat.checkSelfPermission(
             context,
             Manifest.permission.ACCESS_NOTIFICATION_POLICY
         ) == PackageManager.PERMISSION_GRANTED
     }

     private fun requestNotificationPermission() {
         if (ActivityCompat.shouldShowRequestPermissionRationale(
                 context as Activity,
                 Manifest.permission.ACCESS_NOTIFICATION_POLICY
             )
         ) {
             // Display additional information or prompt the user to grant the permission
         } else {
             ActivityCompat.requestPermissions(
                 context,
                 arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
                 NOTIFICATION_PERMISSION_REQUEST_CODE
             )
         }
     }*/
}