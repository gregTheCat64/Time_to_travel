package com.example.timetotravel.db

import androidx.room.Embedded
import androidx.room.Relation

data class FlightWithSeatsEntity (
    @Embedded val flight: FlightEntity,
    @Relation(
        parentColumn = "searchToken",
        entityColumn = "flightSearchToken"
    )
    val liked: LikedFlightEntity?,
    @Relation(
        parentColumn = "searchToken",
        entityColumn = "flightSearchToken"
    )
    val seats: List<SeatEntity>
)