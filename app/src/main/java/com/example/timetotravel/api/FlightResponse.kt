package com.example.timetotravel.api

import com.example.timetotravel.models.Seat
import java.time.LocalDateTime

data class FlightResponse (
    val startCity: String,
    val endCity: String,
    val startDate: String,
    val endDate: String,
    val startLocationCode: String,
    val endLocationCode: String,
    val price: Int,
    val searchToken: String,
    val seats: List<Seat>,
    val serviceClass: String,
        )