package com.example.a01_criminalintent.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.a01_criminalintent.R
import com.example.a01_criminalintent.model.Crime

class AdapterCrimeList(
    private val dataSet: List<Crime>
): RecyclerView.Adapter<AdapterCrimeList.CrimeHolder>() {

    class CrimeHolder(itemView: View) : ViewHolder(itemView) {
        private val crimeTitle: TextView = itemView.findViewById(R.id.tv_crime_title)
        private val crimeData: TextView = itemView.findViewById(R.id.tv_crime_date)

        fun bind(crime: Crime) {
            crimeTitle.text = crime.title
            crimeData.text = crime.date.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.crime_item_list, parent, false)
        return CrimeHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        holder.bind(dataSet[position])
    }
}