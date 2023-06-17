package com.example.timetotravel.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [
    FlightEntity::class
], version = 1, exportSchema = false)

@TypeConverters(Converters::class)
abstract class AppDb: RoomDatabase() {
    abstract fun flightDao(): FlightDao

}