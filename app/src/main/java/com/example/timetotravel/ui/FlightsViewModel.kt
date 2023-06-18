package com.example.timetotravel.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.timetotravel.models.Flight
import com.example.timetotravel.api.RequestCodeBody
import com.example.timetotravel.db.AppDb
import com.example.timetotravel.models.LoadState
import com.example.timetotravel.repository.FlightsRepository
import com.example.timetotravel.repository.FlightsRepositoryImpl
import com.example.timetotravel.toFlightModel
import com.example.timetotravel.utils.NetworkError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightsViewModel @Inject constructor(
    private val repository: FlightsRepository,
) : ViewModel() {

    private val requestCodeBody = RequestCodeBody("LED")


    val data: LiveData<List<Flight>> = repository.data.asLiveData(Dispatchers.Default)

    private val _state = MutableLiveData<LoadState>()
    val state: LiveData<LoadState>
        get() = _state

    private val _currentFlight = MutableLiveData<Flight>()
    val currentFlight: LiveData<Flight>
        get() = _currentFlight

    init {
    }

    fun loadFlightList() {
        viewModelScope.launch {
            _state.value = LoadState(loading = true)
            try {
                repository.getAll(requestCodeBody)
                _state.value = LoadState(idle = true)
            }catch (e: NetworkError){
              _state.value = LoadState(error = true)
            }
           
            println("LOADING FLIGHTS")
        }
    }

    fun getByToken(searchToken: String) {
        viewModelScope.launch {
            _currentFlight.value = repository.getByToken(searchToken)
        }
    }

    fun setFav(searchToken: String){
        viewModelScope.launch {
            repository.likeFlight(searchToken)
        }
    }

}