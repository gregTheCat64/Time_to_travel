package com.example.timetotravel.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetotravel.models.Flight
import com.example.timetotravel.api.RequestCodeBody
import com.example.timetotravel.db.AppDb
import com.example.timetotravel.repository.FlightsRepository
import com.example.timetotravel.repository.FlightsRepositoryImpl
import com.example.timetotravel.toFlightModel
import com.example.timetotravel.utils.NetworkError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightsViewModel @Inject constructor(
    private val repository: FlightsRepository,
): ViewModel() {

    private val requestCodeBody = RequestCodeBody("LED")


    private val _data = MutableLiveData<List<Flight>>()
    val data: LiveData<List<Flight>>
        get() = _data

    private val _currentFlight = MutableLiveData<Flight>()
    val currentFlight: LiveData<Flight>
        get() = _currentFlight

    init {
        //getAll()
    }

    fun getAll(){
        viewModelScope.launch {
            val result = repository.getAll(requestCodeBody)?:throw NetworkError
            _data.postValue(result.flights.map { it.toFlightModel() })
        }

    }

    fun getByToken(searchToken: String): Flight?{
        var currentFlight: Flight? = null
        viewModelScope.launch {
                val apiResult = repository.getAll(requestCodeBody)?:throw NetworkError
                println("data: ${data.value}")
                currentFlight = apiResult.flights.findLast { it.searchToken == searchToken }?.toFlightModel()

        }
        return currentFlight

    }


}