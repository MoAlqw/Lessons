package com.example.nerdlauncher.model.repository

import android.content.Context
import android.content.Intent
import com.example.nerdlauncher.model.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class NerdRepository private constructor(context: Context){

    private val packageManager = context.packageManager

    suspend fun loadActivities(): List<AppInfo> = withContext(Dispatchers.IO) {
        val startupIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val resolveInfoList = packageManager.queryIntentActivities(startupIntent, 0)

        coroutineScope {
            resolveInfoList.map { resolveInfo ->
                async {
                    AppInfo(
                        name = resolveInfo.loadLabel(packageManager).toString(),
                        icon = resolveInfo.loadIcon(packageManager),
                        activityInfo = resolveInfo.activityInfo
                    )
                }
            }.awaitAll()
        }.sortedBy { it.name.lowercase() }
    }

    companion object {
        private var INSTANCE: NerdRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) INSTANCE = NerdRepository(context)
        }
        fun get(): NerdRepository {
            return INSTANCE ?: throw IllegalStateException("Nerd repository must be initialized")
        }
    }
}