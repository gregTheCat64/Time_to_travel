package com.example.timetotravel.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.timetotravel.R
import com.example.timetotravel.databinding.FragmentFlightsBinding
import com.example.timetotravel.models.Adapter
import com.example.timetotravel.api.RequestCodeBody
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class FlightsFragment: Fragment(R.layout.fragment_flights) {
    private val viewModel: FlightsViewModel by viewModels()
    lateinit var binding: FragmentFlightsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFlightsBinding.bind(view)

        val mAnimator = binding.flightsList.itemAnimator as SimpleItemAnimator
        mAnimator.supportsChangeAnimations = false




        val adapter = Adapter()

        binding.flightsList.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }
}