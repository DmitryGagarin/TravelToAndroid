package com.example.myapplication.service

import com.example.myapplication.attractions.AttractionCreateForm
import com.example.myapplication.form.ParkFacilityCreateForm
import com.example.myapplication.models.AttractionModel
import com.example.myapplication.models.PagedAttractionResponse
import com.example.myapplication.views.attraction.features.models.ParkFacilityModel
import com.example.myapplication.views.attraction.features.models.PosterModel
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AttractionService {

    @GET("attraction/published")
    suspend fun getPublishedAttractions(): PagedAttractionResponse

    @GET("attraction/{name}")
    suspend fun getAttractionByName(
        @Header("Authorization") token: String,
        @Path("name") name: String
    ): AttractionModel

    @POST("attraction/register-business")
    suspend fun createAttraction(
        @Header("Authorization") token: String,
        @Body attraction: AttractionCreateForm,
    ): AttractionModel

    @POST("like/add/{attractionName}")
    suspend fun likeAttraction(
        @Header("Authorization") token: String,
        @Path("attractionName") attractionName: String,
    )

    @POST("attraction-feature/{attractionName}/create-park-facility")
    suspend fun saveParkFacilities(
        @Header("Authorization") token: String,
        @Path("attractionName") attractionName: String,
        @Body attraction: ParkFacilityCreateForm,
    ): List<ParkFacilityModel>

    @POST("attraction-feature/{attractionName}/create-poster")
    suspend fun savePosters(
        @Header("Authorization") token: String,
        @Path("attractionName") attractionName: String,
        @Body posters: List<MultipartBody.Part>
    ): List<PosterModel>
}