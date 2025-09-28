package com.example.myapplication.service

import com.example.myapplication.models.PagedDiscussionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DiscussionService {

    @GET("attraction-discussion/{attractionName}")
    suspend fun getDiscussionsByAttractionName(@Path("attractionName") name: String): PagedDiscussionResponse
}