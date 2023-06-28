package com.example.timetotravel.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "liked_flights",
    foreignKeys = [
        ForeignKey(
            entity = FlightEntity::class,
            parentColumns = ["searchToken"],
            childColumns = ["flightSearchToken"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]

)
data class LikedFlightEntity (
    @PrimaryKey val flightSearchToken: String
)