package com.example.traveltoandroid.service

import com.example.traveltoandroid.models.AttractionModel
import com.example.traveltoandroid.models.PagedAttractionResponse
import com.example.traveltoandroid.views.attraction.features.models.ParkFacilityModel
import com.example.traveltoandroid.views.attraction.features.models.PosterModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface AttractionService {

    @GET("attraction/published")
    suspend fun getPublishedAttractions(): PagedAttractionResponse

    @GET("attraction/{name}")
    suspend fun getAttractionByName(
        @Header("Authorization") token: String,
        @Path("name") name: String
    ): AttractionModel

    @Multipart
    @POST("attraction/register-business")
    suspend fun createAttraction(
        @Header("Authorization") token: String,
        @Part("attractionCreateForm") attraction: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): AttractionModel

    @POST("like/add/{attractionName}")
    suspend fun likeAttraction(
        @Header("Authorization") token: String,
        @Path("attractionName") attractionName: String,
    )

    @Multipart
    @POST("attraction-feature/{attractionName}/create-park-facility")
    suspend fun saveParkFacilities(
        @Header("Authorization") token: String,
        @Path("attractionName") attractionName: String,
        @Part("parkFacilityCreateForm") facilities: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): List<ParkFacilityModel>

    @POST("attraction-feature/{attractionName}/create-poster")
    suspend fun savePosters(
        @Header("Authorization") token: String,
        @Path("attractionName") attractionName: String,
        @Body posters: List<MultipartBody.Part>
    ): List<PosterModel>
}