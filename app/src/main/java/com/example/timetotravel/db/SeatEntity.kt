package com.example.timetotravel.db

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "seats",
    foreignKeys = [
        ForeignKey(
            entity = FlightEntity::class,
            parentColumns = ["searchToken"],
            childColumns = ["flightSearchToken"]
        )
                  ],
    primaryKeys = ["flightSearchToken", "passengerType"]
)
data class SeatEntity (
    val flightSearchToken: String,
    val passengerType: String,
    val count: Int
)