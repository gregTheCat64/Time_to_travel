package com.example.timetotravel.repository

import com.example.timetotravel.models.Flight
import com.example.timetotravel.models.api.FlightsApi
import com.example.timetotravel.models.api.RequestCodeBody

class FlightsRepositoryImpl(): FlightsRepository {

    override suspend fun getAll(requestCodeBody: RequestCodeBody): List<Flight> {
        try {
            val response = FlightsApi.api.getAll(requestCodeBody)
            return  response
        }catch (e: Exception){
            e.printStackTrace()
        }
        return TODO("Provide the return value")
    }

    override fun likeFlight(token: String) {
        TODO("Not yet implemented")
    }
}