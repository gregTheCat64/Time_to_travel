package com.example.timetotravel

import com.example.timetotravel.api.FlightResponse
import com.example.timetotravel.db.FlightEntity
import com.example.timetotravel.db.FlightWithSeatsEntity
import com.example.timetotravel.db.SeatEntity
import com.example.timetotravel.models.Flight
import com.example.timetotravel.models.Seat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun FlightWithSeatsEntity.toFlightModel() = Flight(
    flight.startCity,flight.endCity,
    startDate = flight.startDate.toLocalDateTime(),
    endDate = flight.endDate.toLocalDateTime(),
    flight.startLocationCode,flight.endLocationCode,flight.price,flight.searchToken,
    seats.map { it.toModel() }, flight.serviceClass, liked != null
)

fun SeatEntity.toModel() =
    Seat(count, passengerType)

fun Seat.toEntity(flightSearchToken: String) =
    SeatEntity(flightSearchToken, passengerType, count)

fun FlightResponse.toFlightEntity() = FlightEntity(
    startCity,endCity,
    startDate,
    endDate,
    startLocationCode,endLocationCode,price,searchToken,  serviceClass
)

fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss xxxx zz"))

fun LocalDateTime.asOnlyDate(): String = this.let {
    format(DateTimeFormatter.ofPattern("dd MMMM"))
}