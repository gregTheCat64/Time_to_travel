package com.example.timetotravel.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.timetotravel.repository.FlightsRepository
import com.example.timetotravel.repository.FlightsRepositoryImpl

class FlightDetailsViewModel(application: Application): AndroidViewModel(application) {

    private val repository: FlightsRepository = FlightsRepositoryImpl()
}