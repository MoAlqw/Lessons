package com.example.photoapi.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import com.example.photoapi.worker.PollWorker

abstract class VisibleFragment: Fragment() {

    private val onShowNotification = object : BroadcastReceiver() {
        @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
        override fun onReceive(context: Context, intent: Intent) {
            resultCode = Activity.RESULT_CANCELED
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(PollWorker.ACTION_SHOW_NOTIFICATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().registerReceiver(
                onShowNotification,
                filter,
                PollWorker.PERM_PRIVATE,
                null,
                Context.RECEIVER_NOT_EXPORTED
            )
        } else {
            @SuppressLint("UnspecifiedRegisterReceiverFlag")
            requireActivity().registerReceiver(
                onShowNotification,
                filter,
                PollWorker.PERM_PRIVATE,
                null
            )
        }
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(onShowNotification)
    }

}