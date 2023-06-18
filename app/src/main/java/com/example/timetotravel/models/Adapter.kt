package com.example.timetotravel.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timetotravel.asOnlyDate
import com.example.timetotravel.databinding.FlightCardBinding

interface OnInteractionListener{
    fun onFlight(flight: Flight)

    fun onLike(flight: Flight)
}
class Adapter(private val onInteractionListener: OnInteractionListener): ListAdapter<Flight, FlightViewHolder>(FlightDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val binding = FlightCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlightViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = getItem(position)
        holder.bind(flight)
    }
}

class FlightViewHolder(private val binding: FlightCardBinding, private val onInteractionListener: OnInteractionListener): RecyclerView.ViewHolder(binding.root){
    fun bind(flight: Flight){
        val price = flight.price
        val startDate = flight.startDate.asOnlyDate()
        val endDate = flight.endDate.asOnlyDate()
        val startCity = flight.startCity
        val endCity = flight.endCity

        binding.priceText.text = price.toString() + " руб."
        binding.routeText.text = "$startCity -> $endCity"
        binding.departureTime.text = startDate
        binding.returnTime.text = endDate
        binding.favBtn.isChecked = flight.isFav == true

        binding.root.setOnClickListener {
            onInteractionListener.onFlight(flight)
        }
        binding.favBtn.setOnClickListener {
            onInteractionListener.onLike(flight)
        }
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
