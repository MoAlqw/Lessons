package com.example.nerdlauncher.model

import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable

data class AppInfo(val name: String, val icon: Drawable, val activityInfo: ActivityInfo) {
}