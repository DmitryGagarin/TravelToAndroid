package com.example.myapplication.screens.utils

import com.example.myapplication.screens.attractions.AttractionService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://localhost:8080/"

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