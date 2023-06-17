package com.example.timetotravel.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.timetotravel.R

class SeatsAdapter(context: Context, private val arrayList: ArrayList<Seat>): ArrayAdapter<Seat>(context, R.layout.seats_card, arrayList) {

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?:LayoutInflater.from(context).inflate(R.layout.seats_card,parent,false)
        val listData = getItem(position)

        val categoryText = view?.findViewById<TextView>(R.id.categoryType)
        val countText = view?.findViewById<TextView>(R.id.count)

        categoryText?.text = listData?.passengerType
        countText?.text = listData?.count.toString()

        return view
    }
}


