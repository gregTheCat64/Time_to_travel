package com.example.timetotravel.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TimeToTravelApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}