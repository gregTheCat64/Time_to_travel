package com.example.timetotravel.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.timetotravel.R
import com.example.timetotravel.asOnlyDate
import com.example.timetotravel.databinding.FragmentFlightDetailsBinding
import com.example.timetotravel.models.Seat
import com.example.timetotravel.models.SeatsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


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

        if (token != null) {
            viewModel.getByToken(token)
        }

        binding.toFavBtn.setOnClickListener {
            if (token != null) {
                viewModel.setFav(token)
            }
        }

        viewModel.currentFlight.observe(viewLifecycleOwner){currentFlight->
            if (token!=null){
                println("currentFlight: $currentFlight")

                binding.apply {
                    currentFlight.let {
                        val priceField = it.price.toString() + getString(R.string.rub)
                        val routeField = it.startCity + " -> " + it.endCity

                        priceText.text = priceField
                        routeText.text = routeField
                        departureTime.text = it?.startDate?.asOnlyDate()
                        returnTime.text = it?.endDate?.asOnlyDate()
                        serviceClass.text = it?.serviceClass
                        toFavBtn.isChecked = it.isFav == true
                        mainCollapsing.title = it.startLocationCode + " -> " + it.endLocationCode
                        mainToolbar.subtitle = routeField
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

        viewModel.state.observe(viewLifecycleOwner){
            println("state: $it")

            if (it.error){
                Snackbar.make(binding.root, getString(R.string.network_error), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.repeat)) {
                        viewModel.loadFlightList()
                    }
                    .show()
            }
        }

        binding.mainToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}