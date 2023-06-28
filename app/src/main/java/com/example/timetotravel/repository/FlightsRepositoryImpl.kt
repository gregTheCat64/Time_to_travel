package com.example.timetotravel.repository

import com.example.timetotravel.api.Api
import com.example.timetotravel.api.RequestCodeBody
import com.example.timetotravel.db.FlightDao
import com.example.timetotravel.db.LikedFlightEntity
import com.example.timetotravel.models.Flight
import com.example.timetotravel.toEntity
import com.example.timetotravel.toFlightEntity
import com.example.timetotravel.toFlightModel
import com.example.timetotravel.utils.ApiError
import com.example.timetotravel.utils.DbError
import com.example.timetotravel.utils.NetworkError
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.sql.SQLException
import javax.inject.Inject

class FlightsRepositoryImpl @Inject constructor(
    private val dao: FlightDao,
    private val api: Api
    ): FlightsRepository {
    override val data = dao.getFlightsWithSeats()
        .map {list->
            list.map {flight->
            flight.toFlightModel()
        } }


    override suspend fun load(requestCodeBody: RequestCodeBody) {
        try {
            val response = api.getAll(requestCodeBody)

            val body = response.body()?: throw  ApiError(response.code(), response.message())

            val dbTokens = dao.getDbFlightTokens()

            val oldFlights = dbTokens.minus(body.flights.map { it.searchToken }.toSet())

            dao.removeNotActualTokens(oldFlights)

            val flights = response.body()?.flights?.map { it.toFlightEntity() }
            val seats = response.body()?.flights?.map {flight->
                flight.seats.map {
                    it.toEntity(flight.searchToken)
                }
            }?.flatten()

            if (flights != null && seats != null) {
                    dao.insert(flights, seats)
            }
        }catch (e: IOException){
            throw NetworkError
        }
    }

    override suspend fun getByToken(token: String): Flight? {
        try {

            return dao.getByToken(token)?.toFlightModel()
        } catch (e: SQLException) {
            throw DbError
        }
        }


    override suspend fun likeFlight(token: String) {
        try {
            dao.like(LikedFlightEntity(token))
        } catch (e: SQLException) {
            throw DbError
        }
    }

    override suspend fun unlikeFlight(token: String) {
        try {
            dao.unLike(LikedFlightEntity(token))
        } catch (e: SQLException) {
            throw DbError
        }
    }
}