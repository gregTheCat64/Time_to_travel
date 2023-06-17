package com.example.timetotravel.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.timetotravel.models.Flight
import com.example.timetotravel.models.Seat
import com.example.timetotravel.toLocalDateTime

@Entity
data class FlightEntity (
    val startCity: String,
    val endCity: String,
    val startDate: String,
    val endDate: String,
    val startLocationCode: String,
    val endLocationCode: String,
    val price: Int,
    @PrimaryKey
    val searchToken: String,
    val seats: List<Seat>,
    val serviceClass: String,
        ) {
    fun toModel() = Flight(
        startCity, endCity, startDate.toLocalDateTime(), endDate.toLocalDateTime(),startLocationCode,endLocationCode,price,searchToken, seats, serviceClass
    )

    companion object{
        fun fromModel(flight: Flight) =
            FlightEntity(
                flight.startCity, flight.endCity, flight.startDate.toString(), flight.endDate.toString(), flight.startLocationCode, flight.endLocationCode,
                flight.price, flight.searchToken, flight.seats, flight.serviceClass
            )
    }
}

fun List<FlightEntity>.toModel(): List<Flight> = map(FlightEntity::toModel)
fun List<Flight>.toEntity(): List<FlightEntity> = map(FlightEntity::fromModel)