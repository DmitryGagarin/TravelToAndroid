package com.example.myapplication.service

import com.example.myapplication.form.CreateDiscussionForm
import com.example.myapplication.models.DiscussionModel
import com.example.myapplication.models.PagedDiscussionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DiscussionService {

    @GET("attraction-discussion/{attractionName}")
    suspend fun getDiscussionsByAttractionName(@Path("attractionName") name: String): PagedDiscussionResponse

    @POST("attraction-discussion/create/{attractionName}")
    suspend fun sendDiscussion(
        @Path("attractionName") name: String,
        @Header("Authorization") token: String,
        @Body form: CreateDiscussionForm
    ): DiscussionModel
}