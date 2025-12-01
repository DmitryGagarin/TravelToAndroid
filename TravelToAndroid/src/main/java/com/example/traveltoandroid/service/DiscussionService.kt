package com.example.traveltoandroid.service

import com.example.traveltoandroid.form.CreateDiscussionForm
import com.example.traveltoandroid.models.DiscussionModel
import com.example.traveltoandroid.models.PagedDiscussionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DiscussionService {

    @GET("attraction-discussion/{attractionName}")
    suspend fun getDiscussionsByAttractionName(
        @Path("attractionName") name: String,
        @Header("Authorization") token: String,
    ): PagedDiscussionResponse

    @POST("attraction-discussion/create/{attractionName}")
    suspend fun sendDiscussion(
        @Path("attractionName") name: String,
        @Header("Authorization") token: String,
        @Body form: CreateDiscussionForm
    ): DiscussionModel
}