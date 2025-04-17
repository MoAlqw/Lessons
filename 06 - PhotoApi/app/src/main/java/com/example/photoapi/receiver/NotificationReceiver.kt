package com.example.photoapi.receiver

import android.Manifest
import android.app.Activity
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationManagerCompat
import com.example.photoapi.worker.PollWorker

class NotificationReceiver: BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        val requestCode = intent.getIntExtra(PollWorker.REQUEST_CODE, 0)
        val notification: Notification? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra<Notification>(PollWorker.NOTIFICATION, Notification::class.java)
        } else {
            intent.getParcelableExtra<Notification>(PollWorker.NOTIFICATION)
        }
        val notificationManager = NotificationManagerCompat.from(context)
        if (notification != null) notificationManager.notify(requestCode, notification)
    }
}