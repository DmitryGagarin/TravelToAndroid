package com.example.myapplication.utils

import com.example.myapplication.attractions.AttractionService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
//    private const val BASE_URL = "http://192.168.1.100:8080/"
    const val BASE_URL = "http://10.0.2.2:8080/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val attractionService: AttractionService by lazy {
        retrofit.create(AttractionService::class.java)
    }
}