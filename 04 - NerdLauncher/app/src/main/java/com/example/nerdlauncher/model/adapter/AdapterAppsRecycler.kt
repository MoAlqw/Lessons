package com.example.nerdlauncher.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nerdlauncher.R
import com.example.nerdlauncher.model.AppInfo

class AdapterAppsRecycler(
    private val data: List<AppInfo>,
    private val onItemClick: (AppInfo) -> Unit
): RecyclerView.Adapter<AdapterAppsRecycler.AppViewHolder>() {

    class AppViewHolder(itemView: View, private val onItemClick: (AppInfo) -> Unit): RecyclerView.ViewHolder(itemView) {
        private val tvNameApp: TextView = itemView.findViewById(R.id.tv_name_app)
        private val iconApp: ImageView = itemView.findViewById(R.id.img_app)

        fun bind(item: AppInfo) {
            tvNameApp.text = item.name
            iconApp.setImageDrawable(item.icon)
            itemView.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.app_name_item, parent, false)
        return AppViewHolder(itemView, onItemClick)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size
}