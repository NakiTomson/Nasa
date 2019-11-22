package com.example.dmiryz.nasa.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dmiryz.nasa.R
import com.example.dmiryz.nasa.layouts.PhotoListActivity
import com.example.dmiryz.nasa.models.Dates
import com.example.dmiryz.nasa.models.Photo


class DateAdapter(list: ArrayList<Dates>) : RecyclerView.Adapter<DateAdapter.DateHolder>() {


    val EXTRA_DATE = "EXTRA_DATE"
    private var lists: ArrayList<Dates> = list
    private var dates: Dates? = null


    fun setDates(dates: List<Dates>) {
        this.lists.clear()
        this.lists.addAll(dates)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return DateHolder(view)
    }

    override fun onBindViewHolder(holder: DateHolder, position: Int) {
        dates = lists[position]
        holder.dateText.text = dates?.date
        holder.bind(dates!!)
    }

    override fun getItemCount(): Int {
        return lists.size
    }


    inner class DateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateText: TextView = itemView.findViewById(R.id.textdate)
        var cardView: CardView = itemView.findViewById(R.id.cardView)

        lateinit var currenDate:Dates

        init {
            cardView.setOnClickListener {
                val intent = Intent(it.context, PhotoListActivity::class.java)
                intent.putExtra(EXTRA_DATE, currenDate.date)
                Log.i("TAG",currenDate.date)
                it.context.startActivity(intent)
            }
        }

        fun bind(date: Dates) {
            this.currenDate = date
        }
    }

}