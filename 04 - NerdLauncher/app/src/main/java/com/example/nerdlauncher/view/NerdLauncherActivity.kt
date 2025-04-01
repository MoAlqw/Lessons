package com.example.nerdlauncher.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nerdlauncher.databinding.ActivityNerdLauncherBinding
import com.example.nerdlauncher.model.AppInfo
import com.example.nerdlauncher.model.adapter.AdapterAppsRecycler
import com.example.nerdlauncher.viewmodel.NerdViewModel

class NerdLauncherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNerdLauncherBinding
    private lateinit var recyclerApps: RecyclerView
    private val viewModel: NerdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNerdLauncherBinding.inflate(layoutInflater).also { setContentView(it.root) }
        recyclerApps = binding.appRecyclerView

        viewModel.activities.observe(this) { activities ->
            if (!activities.isNullOrEmpty()) {
                recyclerApps.adapter = AdapterAppsRecycler(activities) { createAndStartIntent(it) }
            }
        }
        viewModel.isLoading.observe(this) {
            if (!it) {
                recyclerApps.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun createAndStartIntent(appInfo: AppInfo) {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            setClassName(appInfo.activityInfo.packageName, appInfo.activityInfo.name)
        }
        startActivity(intent)
    }

}