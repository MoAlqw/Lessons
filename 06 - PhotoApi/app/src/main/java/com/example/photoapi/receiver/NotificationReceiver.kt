package com.example.photoapi.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.photoapi.worker.PollWorker

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == PollWorker.ACTION_SHOW_NOTIFICATION) {
            TODO()
        }
    }
}