package com.example.photogallery.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.photogallery.R
import com.example.photogallery.model.DataState
import com.example.photogallery.model.Fact

class RecyclerCatAdapter(
    private val data: DataState.Success<List<Fact>>
): RecyclerView.Adapter<RecyclerCatAdapter.CatFactViewHolder>() {

    class CatFactViewHolder(itemView: View): ViewHolder(itemView) {
        private val textView = itemView.findViewById<TextView>(R.id.tv_fact)
        private val imageView = itemView.findViewById<ImageView>(R.id.img_cat)
        private val glide = Glide.with(itemView.context)

        fun bind(item: Fact, url: String) {
            textView.text = item.fact
            glide.load(url + item.code).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatFactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_photo_item, parent, false)
        return CatFactViewHolder(view)
    }

    override fun getItemCount(): Int = data.data.size

    override fun onBindViewHolder(holder: CatFactViewHolder, position: Int) {
        val item = data.data[position]

        holder.bind(item, data.url)
    }
}