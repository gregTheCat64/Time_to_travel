package com.example.timetotravel.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.timetotravel.models.Flight
import com.example.timetotravel.api.RequestCodeBody
import com.example.timetotravel.models.FlightList
import com.example.timetotravel.repository.FlightsRepository
import com.example.timetotravel.repository.FlightsRepositoryImpl
import com.example.timetotravel.toFlightModel
import com.example.timetotravel.utils.NetworkError
import kotlinx.coroutines.launch

class FlightsViewModel(application: Application): AndroidViewModel(application) {

    private val repository: FlightsRepository = FlightsRepositoryImpl()

    private val _data = MutableLiveData<List<Flight>>()
    val data: LiveData<List<Flight>>
        get() = _data

    init {
        getAll(RequestCodeBody("LED"))
    }

    fun getAll(requestCodeBody: RequestCodeBody){
        viewModelScope.launch {
            val result = repository.getAll(requestCodeBody)?:throw NetworkError
            _data.postValue(result.flights.map { it.toFlightModel() })
        }

    }
}