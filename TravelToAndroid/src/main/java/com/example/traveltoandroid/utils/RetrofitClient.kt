package com.example.traveltoandroid.utils

import com.example.traveltoandroid.repository.attraction.AttractionRepository
import com.example.traveltoandroid.repository.discussion.DiscussionRepository
import com.example.traveltoandroid.repository.user.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val attractionRepository : AttractionRepository by lazy {
        retrofit.create(AttractionRepository::class.java)
    }

    val discussionRepository : DiscussionRepository by lazy {
        retrofit.create(DiscussionRepository::class.java)
    }

    val userRepository : UserRepository by lazy {
        retrofit.create(UserRepository::class.java)
    }
}