package com.example.timetotravel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.timetotravel.R
import com.example.timetotravel.databinding.FragmentFlightsBinding
import com.example.timetotravel.models.Adapter
import com.example.timetotravel.api.RequestCodeBody
import com.example.timetotravel.models.Flight
import com.example.timetotravel.models.OnInteractionListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
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
                Snackbar.make(binding.root, "Ошибка подключения", Snackbar.LENGTH_SHORT)
                    .setAction("Повторить") {
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