package com.example.timetotravel.repository

import com.example.timetotravel.models.Flight
import com.example.timetotravel.api.RequestCodeBody
import com.example.timetotravel.models.FlightList
import kotlinx.coroutines.flow.Flow

interface FlightsRepository {

    suspend fun getAll(requestCodeBody: RequestCodeBody): FlightList?


    fun likeFlight(token: String)
}