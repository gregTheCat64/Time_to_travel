package com.example.timetotravel.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timetotravel.asOnlyDate
import com.example.timetotravel.databinding.FlightCardBinding

class Adapter(): ListAdapter<Flight, FlightViewHolder>(FlightDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val binding = FlightCardBinding.inflate(LayoutInflater.from(parent.context))
        return FlightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = getItem(position)
        holder.bind(flight)
    }
}

class FlightViewHolder(private val binding: FlightCardBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(flight: Flight){
        val price = flight.price
        val startDate = flight.startDate.asOnlyDate()
        val endDate = flight.endDate.asOnlyDate()
        val startCity = flight.startCity
        val endCity = flight.endCity

        binding.priceText.text = price.toString()
        binding.routeText.text = "$startCity -> $endCity"
        binding.departureTime.text = "Туда: $startDate"
        binding.returnTime.text = "Обратно: $endDate"
    }
}

class FlightDiffCallback : DiffUtil.ItemCallback<Flight>() {
    override fun areItemsTheSame(oldItem: Flight, newItem: Flight): Boolean {
        return oldItem.searchToken == newItem.searchToken
    }

    override fun areContentsTheSame(oldItem: Flight, newItem: Flight): Boolean {
        return oldItem == newItem
    }
}
