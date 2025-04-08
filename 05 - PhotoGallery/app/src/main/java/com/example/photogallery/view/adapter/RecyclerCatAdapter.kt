package com.example.photogallery.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.photogallery.databinding.RecyclerFactItemBinding
import com.example.photogallery.model.DataState
import com.example.photogallery.model.Fact

class RecyclerCatAdapter(
    private val data: DataState.Success<List<Fact>>
): RecyclerView.Adapter<RecyclerCatAdapter.CatFactViewHolder>() {

    class CatFactViewHolder(binding: RecyclerFactItemBinding): ViewHolder(binding.root) {
        private val textView = binding.tvFact
        private val imageView = binding.imgCat
        private val glide = Glide.with(itemView.context)

        fun bind(item: Fact, url: String, code: Int) {
            textView.text = item.fact
            glide.load(url + code)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatFactViewHolder {
        val binding = RecyclerFactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatFactViewHolder(binding)
    }

    override fun getItemCount(): Int = data.data.size

    override fun onBindViewHolder(holder: CatFactViewHolder, position: Int) {
        val item = data.data[position]
        holder.bind(item, data.url, data.code)
    }
}