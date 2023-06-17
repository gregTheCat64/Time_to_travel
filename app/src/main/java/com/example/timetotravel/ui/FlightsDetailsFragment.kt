package com.example.timetotravel.ui

import android.os.Bundle
import android.view.View

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.timetotravel.R

import com.example.timetotravel.asOnlyDate
import com.example.timetotravel.databinding.FragmentFlightDetailsBinding
import com.example.timetotravel.models.Seat
import com.example.timetotravel.models.SeatsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
class FlightsDetailsFragment: Fragment(R.layout.fragment_flight_details) {

    private val viewModel: FlightsViewModel by viewModels()
    private lateinit var binding: FragmentFlightDetailsBinding
    private lateinit var seatsAdapter: SeatsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFlightDetailsBinding.bind(view)


        val args = arguments
        val token = args?.getString("FL_TOKEN")
        println("token: $token")

        val seatsArray: ArrayList<Seat> = arrayListOf()

        viewModel.getAll()
        viewModel.data.observe(viewLifecycleOwner){
            if (token!=null){
                val currentFlight = it.find { it.searchToken == token }
                println("currentFlight: $currentFlight")
                binding.apply {
                    currentFlight.let {
                        priceText.text = it?.price.toString()
                        routeText.text = it?.startCity + "->" + it?.endCity
                        departureTime.text = it?.startDate?.asOnlyDate()
                        returnTime.text = it?.endDate?.asOnlyDate()
                        serviceClass.text = it?.serviceClass
                    }
                }

                if (currentFlight != null) {
                    for (i in 0 until currentFlight.seats.size){
                        seatsArray.add(currentFlight.seats[i])
                    }
                }
                println("seatsArray = :$seatsArray")
                seatsAdapter = SeatsAdapter(requireContext(), seatsArray)
                binding.seatList.adapter = seatsAdapter
            }
        }



    }
}