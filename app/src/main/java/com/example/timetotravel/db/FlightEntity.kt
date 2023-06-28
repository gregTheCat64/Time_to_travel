package com.example.timetotravel.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.timetotravel.models.Flight
import com.example.timetotravel.models.Seat
import com.example.timetotravel.toLocalDateTime

@Entity(tableName = "flights")
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
    val serviceClass: String,
        )

