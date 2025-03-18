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
import com.example.a01_criminalintent.model.room.Crime
import com.example.a01_criminalintent.view.fragment.CrimeListFragment
import java.text.DateFormat

class AdapterCrimeList(private val callback: CrimeListFragment.Callbacks?):
    ListAdapter<Crime, AdapterCrimeList.CrimeHolder>(CrimeDiffUtilCallBack())
{
    class CrimeDiffUtilCallBack: DiffUtil.ItemCallback<Crime>() {
        override fun areItemsTheSame(oldItem: Crime, newItem: Crime) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Crime, newItem: Crime) = oldItem == newItem
    }

    class CrimeHolder(itemView: View, private val callback: CrimeListFragment.Callbacks?) : ViewHolder(itemView), View.OnClickListener {
        private val crimeTitle: TextView = itemView.findViewById(R.id.tv_crime_title)
        private val crimeData: TextView = itemView.findViewById(R.id.tv_crime_date)
        private val imgSolved: ImageView = itemView.findViewById(R.id.img_solved)
        private lateinit var crime: Crime

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            this.crime = crime
            crimeTitle.text = this.crime.title
            crimeData.text = DateFormat.getDateInstance(DateFormat.FULL).format(this.crime.date)
            imgSolved.visibility = if (this.crime.isSolved) View.VISIBLE else View.GONE
        }

        override fun onClick(v: View?) {
            callback?.onCrimeSelected(this.crime.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.crime_item_list, parent, false)
        return CrimeHolder(view, callback)
    }

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = getItem(position)
        holder.bind(crime)
    }
}