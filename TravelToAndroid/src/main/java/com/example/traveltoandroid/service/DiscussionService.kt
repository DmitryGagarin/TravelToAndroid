package com.example.traveltoandroid.service

import com.example.traveltoandroid.models.DiscussionModel
import com.example.traveltoandroid.models.PagedDiscussionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface DiscussionService {

    @GET("attraction-discussion/{attractionName}")
    suspend fun getDiscussionsByAttractionName(
        @Path("attractionName") name: String,
        @Header("Authorization") token: String,
    ): PagedDiscussionResponse

    @Multipart
    @POST("attraction-discussion/create/{attractionName}")
    suspend fun sendDiscussion(
        @Header("Authorization") token: String,
        @Path("attractionName") name: String,
        @Part images: List<MultipartBody.Part>?,
        @Part("attractionDiscussionCreateForm") form: RequestBody
    ): DiscussionModel
}