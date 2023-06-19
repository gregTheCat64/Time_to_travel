package com.example.timetotravel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.timetotravel.R
import com.example.timetotravel.databinding.FragmentFlightsBinding
import com.example.timetotravel.models.Adapter
import com.example.timetotravel.models.Flight
import com.example.timetotravel.models.OnInteractionListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FlightsFragment: Fragment(R.layout.fragment_flights) {

    private val viewModel: FlightsViewModel by viewModels()
    lateinit var binding: FragmentFlightsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFlightsBinding.bind(view)
        println("onViewCreated")

        val mAnimator = binding.flightsList.itemAnimator as SimpleItemAnimator
        mAnimator.supportsChangeAnimations = false


        val adapter = Adapter(object : OnInteractionListener{
            override fun onFlight(flight: Flight) {
                val bundle = Bundle()
                bundle.putString("FL_TOKEN", flight.searchToken)
                findNavController().navigate(R.id.flightsDetailsFragment, bundle)
            }

            override fun onLike(flight: Flight) {
                if (flight.isFav != true){
                    Toast.makeText(requireContext(), getString(R.string.addToFavs), Toast.LENGTH_SHORT).show()
                } else { Toast.makeText(requireContext(), getString(R.string.removeFromFavs), Toast.LENGTH_SHORT).show()}
                viewModel.setFav(flight.searchToken)

            }
        })

        binding.flightsList.adapter = adapter

        lifecycleScope.launch {
            println("LOADING FLIGHTS")
            viewModel.loadFlightList()
        }

        viewModel.state.observe(viewLifecycleOwner){
            println("state: $it")
            binding.progressBar.isVisible = it.loading
            if (it.error){
                Snackbar.make(binding.root, getString(R.string.network_error), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.repeat)) {
                        viewModel.loadFlightList()
                    }
                    .show()
            }
        }

        viewModel.data.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }
}