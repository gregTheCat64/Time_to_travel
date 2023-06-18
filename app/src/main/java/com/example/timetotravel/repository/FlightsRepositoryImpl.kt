package com.example.timetotravel.repository

import com.example.timetotravel.api.Api
import com.example.timetotravel.api.FlightResponse
import com.example.timetotravel.db.FlightDao
import com.example.timetotravel.db.FlightEntity
import com.example.timetotravel.db.toModel
import com.example.timetotravel.api.RequestCodeBody
import com.example.timetotravel.models.Flight
import com.example.timetotravel.models.FlightList
import com.example.timetotravel.toFlightEntity
import com.example.timetotravel.toFlightModel
import com.example.timetotravel.utils.ApiError
import com.example.timetotravel.utils.NetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class FlightsRepositoryImpl @Inject constructor(
    private val dao: FlightDao,
    private val api: Api
    ): FlightsRepository {
    override val data = dao.getAll()
        .map (List<FlightEntity>::toModel)
        .flowOn(Dispatchers.Default)


    override suspend fun getAll(requestCodeBody: RequestCodeBody): List<FlightResponse> {
        try {
            val response = api.getAll(requestCodeBody)
            if (!response.isSuccessful) throw ApiError(response.code(), response.message())
            val body = response.body()?:throw ApiError(response.code(), response.message())
            dao.insert(body.map {
                it.toFlightEntity()
            })
            return  response.body()?: throw ApiError(response.code(), response.message())
        }catch (e: IOException){
            throw NetworkError
        }
    }


    override fun likeFlight(token: String) {
        TODO("Not yet implemented")
    }


}