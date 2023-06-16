package com.example.timetotravel.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.timetotravel.R
import com.example.timetotravel.api.RequestCodeBody
import com.example.timetotravel.asOnlyDate
import com.example.timetotravel.databinding.FragmentFlightDetailsBinding

class FlightsDetailsFragment: Fragment(R.layout.fragment_flight_details) {
    private val viewModel: FlightsViewModel by viewModels()
    lateinit var binding: FragmentFlightDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFlightDetailsBinding.bind(view)


        val args = arguments
        val token = args?.getString("FL_TOKEN")
        println(token)

        if (token!=null) {
            viewModel.getAll()
            val currentFlight = viewModel.getByToken(token)
            binding.apply {
                currentFlight.let {
                    priceText.text = it?.price.toString()
                    routeText.text = it?.startCity + "->" + it?.endCity
                    departureTime.text = it?.startDate?.asOnlyDate()
                    returnTime.text = it?.endDate?.asOnlyDate()
                    serviceClass.text = it?.serviceClass

                }
            }
        }

    }
}