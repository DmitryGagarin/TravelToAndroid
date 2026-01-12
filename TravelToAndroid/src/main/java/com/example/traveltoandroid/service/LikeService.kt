package com.example.traveltoandroid.service

import com.example.traveltoandroid.models.PagedAttractionResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeService {

    @GET("like")
    suspend fun getUserLikedAttractions(
        @Header("Authorization") token: String,
    ): PagedAttractionResponse

    @POST("like/add/{attractionName}")
    suspend fun likeAttraction(
        @Header("Authorization") token: String,
        @Path("attractionName") attractionName: String
    )

    @POST("like/delete/{attractionName}")
    suspend fun dislikeAttraction(
        @Header("Authorization") token: String,
        @Path("attractionName") attractionName: String
    )

    @GET("like/is-liked/{attractionName}")
    suspend fun isAttractionLiked(
        @Header("Authorization") token: String,
        @Path("attractionName") attractionName: String
    ): Boolean
}