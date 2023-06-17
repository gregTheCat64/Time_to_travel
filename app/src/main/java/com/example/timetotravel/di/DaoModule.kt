package com.example.timetotravel.di

import com.example.timetotravel.db.AppDb
import com.example.timetotravel.db.FlightDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DaoModule {
    @Provides
    fun provideDao(db: AppDb): FlightDao = db.flightDao()
}