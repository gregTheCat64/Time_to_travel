package com.example.timetotravel.di

import com.example.timetotravel.repository.FlightsRepository
import com.example.timetotravel.repository.FlightsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindsRepository(impl: FlightsRepositoryImpl):FlightsRepository
}