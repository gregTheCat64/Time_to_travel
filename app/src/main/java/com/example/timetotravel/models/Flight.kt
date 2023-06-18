package com.example.timetotravel.models

import java.time.LocalDateTime

data class Flight(
    val startCity: String,
    val endCity: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val startLocationCode: String,
    val endLocationCode: String,
    val price: Int,
    val searchToken: String,
    val seats: List<Seat>,
    val serviceClass: String,
    val isFav: Boolean?
)