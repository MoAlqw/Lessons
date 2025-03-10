package com.example.a01_criminalintent.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.a01_criminalintent.R
import com.example.a01_criminalintent.model.Crime
import java.text.DateFormat

class AdapterCrimeList:
    ListAdapter<Crime, AdapterCrimeList.CrimeHolder>(CrimeDiffUtilCallBack())
{
    class CrimeDiffUtilCallBack: DiffUtil.ItemCallback<Crime>() {
        override fun areItemsTheSame(oldItem: Crime, newItem: Crime) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Crime, newItem: Crime) = oldItem == newItem
    }

    class CrimeHolder(itemView: View) : ViewHolder(itemView) {
        private val crimeTitle: TextView = itemView.findViewById(R.id.tv_crime_title)
        private val crimeData: TextView = itemView.findViewById(R.id.tv_crime_date)
        private val imgSolved: ImageView = itemView.findViewById(R.id.img_solved)

        fun bind(crime: Crime) {
            crimeTitle.text = crime.title
            crimeData.text = DateFormat.getDateInstance(DateFormat.FULL).format(crime.date)
            imgSolved.visibility = if (crime.isSolved) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.crime_item_list, parent, false)
        return CrimeHolder(view)
    }

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = getItem(position)
        holder.bind(crime)
    }
}