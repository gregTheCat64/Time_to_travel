package com.example.timetotravel.repository

import com.example.timetotravel.models.Flight
import com.example.timetotravel.models.api.RequestCodeBody
import kotlinx.coroutines.flow.Flow

interface FlightsRepository {

    suspend fun getAll(requestCodeBody: RequestCodeBody): List<Flight>

    fun likeFlight(token: String)
}