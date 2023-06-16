package com.example.timetotravel.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.timetotravel.R
import com.example.timetotravel.databinding.FragmentFlightsBinding

class FlightsFragment: Fragment(R.layout.fragment_flights) {

    lateinit var binding: FragmentFlightsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFlightsBinding.bind(view)

        val mAnimator = binding.flightsList.itemAnimator as SimpleItemAnimator
        mAnimator.supportsChangeAnimations = false

    }
}