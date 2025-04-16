package com.example.photoapi

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import kotlin.jvm.java

class PhotoApiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val name = getString(R.string.notification_channel_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
        val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "unsplash_poll"
    }
}