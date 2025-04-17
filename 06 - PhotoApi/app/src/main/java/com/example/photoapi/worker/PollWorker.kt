package com.example.photoapi.worker

import android.Manifest
import android.app.Activity
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.photoapi.PhotoApiApplication
import com.example.photoapi.R
import com.example.photoapi.view.activity.MainActivity
import java.util.concurrent.TimeUnit

class PollWorker(val context: Context, workerParams: WorkerParameters):
    Worker(context, workerParams) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun doWork(): Result {
        // Here there should be loading a new page of photos by the keyword entered by the user
        // and comparing their IDs. If they are different, then the following code is executed
        val intent = MainActivity.newIntent(context)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_MUTABLE)
        val resources = context.resources
        val notification = Notification
            .Builder(context, PhotoApiApplication.NOTIFICATION_CHANNEL_ID)
            .setTicker(resources.getString(R.string.new_pictures_title))
            .setSmallIcon(android.R.drawable.ic_menu_report_image)
            .setContentTitle(resources.getString(R.string.new_pictures_title))
            .setContentText(resources.getString(R.string.new_pictures_text))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        showBackgroundNotification(Activity.RESULT_OK, notification)

        return Result.success()
    }

    private fun showBackgroundNotification(
        requestCode: Int,
        notification: Notification
    ) {
        val intent = Intent(ACTION_SHOW_NOTIFICATION).apply {
            `package` = context.packageName
            putExtra(REQUEST_CODE, requestCode)
            putExtra(NOTIFICATION, notification)
        }
        context.sendOrderedBroadcast(intent, PERM_PRIVATE)
    }

    companion object {
        private const val WORK_NAME = "poll_worker"
        const val PERM_PRIVATE = "com.example.photoapi.PRIVATE"
        const val ACTION_SHOW_NOTIFICATION = "com.example.photoapi.worker.SHOW_NOTIFICATION"
        const val REQUEST_CODE = "request_code"
        const val NOTIFICATION = "notification"

        fun enqueue(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()

            val request = PeriodicWorkRequest.Builder(
                PollWorker::class.java,
                15, TimeUnit.MINUTES
            ).setConstraints(constraints).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }

        fun cancelUniqueWork(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
        }
    }
}