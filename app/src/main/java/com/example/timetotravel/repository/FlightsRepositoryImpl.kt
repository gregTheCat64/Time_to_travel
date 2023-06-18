package com.example.timetotravel.repository

import com.example.timetotravel.api.Api
import com.example.timetotravel.db.FlightDao
import com.example.timetotravel.db.FlightEntity
import com.example.timetotravel.db.toModel
import com.example.timetotravel.api.RequestCodeBody
import com.example.timetotravel.models.Flight
import com.example.timetotravel.toFlightEntity
import com.example.timetotravel.utils.ApiError
import com.example.timetotravel.utils.DbError
import com.example.timetotravel.utils.NetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.sql.SQLException
import javax.inject.Inject

class FlightsRepositoryImpl @Inject constructor(
    private val dao: FlightDao,
    private val api: Api
    ): FlightsRepository {
    override val data = dao.getAll()
        .map (List<FlightEntity>::toModel)
        .flowOn(Dispatchers.Default)


    override suspend fun getAll(requestCodeBody: RequestCodeBody) {
        try {
            val response = api.getAll(requestCodeBody)
            if (!response.isSuccessful) throw ApiError(response.code(), response.message())
            val body = response.body()?:throw ApiError(response.code(), response.message())

            dao.insert(body.flights.map {
                it.copy(
                    startCity = it.startCity,
                    endCity = it.endCity,
                    startDate = it.startDate,
                    endDate = it.endDate,
                    startLocationCode = it.startLocationCode,
                    endLocationCode = it.endLocationCode,
                    price = it.price,
                    searchToken = it.searchToken,
                    seats = it.seats,
                    serviceClass = it.serviceClass,
                    favStatus = getByToken(it.searchToken)?.isFav
                ).toFlightEntity()
            })

        }catch (e: IOException){
            throw NetworkError
        }
    }

    override suspend fun getByToken(token: String): Flight? {
        try {
            return dao.getByToken(token)?.toModel()
        } catch (e: SQLException) {
            throw DbError
        }
        }



    override suspend fun likeFlight(token: String) {
        try {
            dao.setFav(token)
        } catch (e: SQLException) {
            throw DbError
        }
    }


}