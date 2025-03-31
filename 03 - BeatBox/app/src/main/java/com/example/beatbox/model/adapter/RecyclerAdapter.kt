package com.example.beatbox.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.beatbox.R
import com.example.beatbox.model.Sound

class RecyclerAdapter(private val sounds: List<Sound>, private val callbacks: Callbacks): RecyclerView.Adapter<RecyclerAdapter.BeatBoxViewHolder>() {


    class BeatBoxViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val button: Button = itemView.findViewById(R.id.btn_sound)

        fun bind(item: Sound, callbacks: Callbacks) {
            button.text = item.name
            button.setOnClickListener { callbacks.clicked(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeatBoxViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.beat_box_item, parent, false)
        return BeatBoxViewHolder(view)
    }

    override fun getItemCount() = sounds.size

    override fun onBindViewHolder(holder: BeatBoxViewHolder, position: Int) {
        val item = sounds[position]
        holder.bind(item, callbacks)
    }
}