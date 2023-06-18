package com.example.timetotravel

import com.example.timetotravel.api.FlightResponse
import com.example.timetotravel.db.FlightEntity
import com.example.timetotravel.models.Flight
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun FlightResponse.toFlightModel() = Flight(
    startCity,endCity,
    startDate = startDate.toLocalDateTime(),
    endDate = endDate.toLocalDateTime(),
    startLocationCode,endLocationCode,price,searchToken, seats, serviceClass, favStatus
)

fun FlightResponse.toFlightEntity() = FlightEntity(
    startCity,endCity,
    startDate,
    endDate,
    startLocationCode,endLocationCode,price,searchToken, seats, serviceClass, favStatus
)

fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss xxxx zz"))

fun LocalDateTime.asOnlyDate(): String = this.let {
    format(DateTimeFormatter.ofPattern("dd MMMM"))
}