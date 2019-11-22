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
import com.example.dmiryz.nasa.layouts.PhotoAcivity
import com.example.dmiryz.nasa.models.Photo

class PhotoListAdapter(list: ArrayList<Photo>): RecyclerView.Adapter<PhotoListAdapter.PhotoHolder>() {


    private var lists: ArrayList<Photo> = list
    private var dates: Photo = Photo()
    private val EXTRA = "EXTRA_URL"



    fun setDates(dates: ArrayList<Photo>) {
        Log.i("TAG",dates.toString())
        this.lists.clear()
        this.lists.addAll(dates)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        dates = lists[position]
        holder.dateText.text = dates.date
        holder.bind(dates)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    inner class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateText: TextView = itemView.findViewById(R.id.textdate)
        var cardView:CardView = itemView.findViewById(R.id.cardView)

        lateinit var currenDate:Photo

        init {
            cardView.setOnClickListener {
                val intent = Intent(it.context, PhotoAcivity::class.java)
                intent.putExtra(EXTRA, currenDate.imageUrl)
                Log.i("TAG",currenDate.date)
                it.context.startActivity(intent)
            }
        }
        fun bind(date: Photo) {
            this.currenDate = date
        }

    }
}