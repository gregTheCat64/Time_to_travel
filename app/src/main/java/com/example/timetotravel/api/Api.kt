package com.example.timetotravel.api

import com.example.timetotravel.models.Flight
import com.example.timetotravel.models.FlightList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


interface Api {
    @POST("GetCheap")
    suspend fun getAll(@Body requestCodeBody: RequestCodeBody): Response<FlightList>
}
