package com.example.timetotravel.repository

import com.example.timetotravel.api.FlightResponse
import com.example.timetotravel.models.Flight
import com.example.timetotravel.api.RequestCodeBody
import com.example.timetotravel.models.FlightList
import kotlinx.coroutines.flow.Flow

interface FlightsRepository {
    val data: Flow<List<Flight>>

    suspend fun getAll(requestCodeBody: RequestCodeBody): List<FlightResponse>?


    fun likeFlight(token: String)
}