package com.example.photoapi.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photoapi.databinding.HolderPhotoBinding
import com.example.photoapi.model.api.response.UnsplashItemPhoto
import com.example.photoapi.view.adapter.PhotoAdapter.PhotoHolder
import com.squareup.picasso.Picasso

class PhotoAdapter: ListAdapter<UnsplashItemPhoto, PhotoHolder>(PhotoDiffUtilCallBack()) {

    class PhotoDiffUtilCallBack: DiffUtil.ItemCallback<UnsplashItemPhoto>() {
        override fun areItemsTheSame(oldItem: UnsplashItemPhoto, newItem: UnsplashItemPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UnsplashItemPhoto, newItem: UnsplashItemPhoto): Boolean {
            return oldItem == newItem
        }
    }

    class PhotoHolder(binding: HolderPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        private val image = binding.imageOfPhoto

        fun bind(itemPhoto: UnsplashItemPhoto) {
            Picasso.get()
                .load(itemPhoto.urls.small)
                .resize(600, 500)
                .centerCrop()
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val binding = HolderPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}