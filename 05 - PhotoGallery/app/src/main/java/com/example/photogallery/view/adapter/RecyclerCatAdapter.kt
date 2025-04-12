package com.example.photogallery.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.photogallery.databinding.RecyclerFactItemBinding
import com.example.photogallery.model.Fact

class RecyclerCatAdapter: PagingDataAdapter<Fact, RecyclerCatAdapter.CatFactViewHolder>(FactDiffUtil) {

    class CatFactViewHolder(binding: RecyclerFactItemBinding): ViewHolder(binding.root) {
        private val textView = binding.tvFact
        private val imageView = binding.imgCat
        private val glide = Glide.with(itemView.context)

        fun bind(item: Fact, url: String = "https://http.cat/", code: Int = 200) {
            textView.text = item.fact
            glide.load(url + code)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatFactViewHolder {
        val binding = RecyclerFactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatFactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatFactViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    object FactDiffUtil : DiffUtil.ItemCallback<Fact>() {
        override fun areItemsTheSame(oldItem: Fact, newItem: Fact): Boolean {
            return oldItem.fact == newItem.fact
        }

        override fun areContentsTheSame(oldItem: Fact, newItem: Fact): Boolean {
            return oldItem == newItem
        }
    }
}