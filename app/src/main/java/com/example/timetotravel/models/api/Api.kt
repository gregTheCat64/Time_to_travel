package com.example.timetotravel.models.api

import com.example.timetotravel.models.Flight
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://vmeste.wildberries.ru/api/avia-service/twirp/aviaapijsonrpcv1.WebAviaService/GetCheap/"

private val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val okttp = OkHttpClient.Builder()
    .addInterceptor(logging)
    .connectTimeout(10, TimeUnit.SECONDS)
    .build()



private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okttp)
    .build()

interface Api {
    @POST
    suspend fun getAll(@Body requestCodeBody: RequestCodeBody):List<Flight>
}

object FlightsApi {
    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
}