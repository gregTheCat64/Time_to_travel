package com.example.timetotravel.repository

import com.example.timetotravel.models.Flight
import com.example.timetotravel.api.FlightsApi
import com.example.timetotravel.api.RequestCodeBody
import com.example.timetotravel.models.FlightList
import com.example.timetotravel.utils.ApiError
import com.example.timetotravel.utils.NetworkError
import java.io.IOException

class FlightsRepositoryImpl(): FlightsRepository {

    override suspend fun getAll(requestCodeBody: RequestCodeBody): FlightList {
        try {
            val response = FlightsApi.api.getAll(requestCodeBody)
            if (!response.isSuccessful) throw ApiError(response.code(), response.message())

            return  response.body()?: throw ApiError(response.code(), response.message())
        }catch (e: IOException){
            throw NetworkError
        }
    }

    override fun likeFlight(token: String) {
        TODO("Not yet implemented")
    }
}