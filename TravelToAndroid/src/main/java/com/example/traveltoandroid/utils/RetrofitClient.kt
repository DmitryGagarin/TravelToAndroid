package com.example.traveltoandroid.utils

import com.example.traveltoandroid.repository.attraction.AttractionRepository
import com.example.traveltoandroid.repository.discussion.DiscussionRepository
import com.example.traveltoandroid.repository.user.UserRepository
import com.example.traveltoandroid.service.AttractionService
import com.example.traveltoandroid.service.DiscussionService
import com.example.traveltoandroid.service.UserService
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

    private val attractionService: AttractionService by lazy {
        retrofit.create(AttractionService::class.java)
    }

    val attractionRepository: AttractionRepository by lazy {
        AttractionRepository(attractionService)
    }

    private val discussionService: DiscussionService by lazy {
        retrofit.create(DiscussionService::class.java)
    }

    val discussionRepository: DiscussionRepository by lazy {
        DiscussionRepository(discussionService)
    }

    private val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val userRepository: UserRepository by lazy {
        UserRepository(userService)
    }

}