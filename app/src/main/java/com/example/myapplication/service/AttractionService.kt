package com.example.myapplication.service

import com.example.myapplication.attractions.AttractionCreateForm
import com.example.myapplication.models.AttractionModel
import com.example.myapplication.models.PagedAttractionResponse
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

    @GET("attraction/search/{type}/name")
    suspend fun getAttractionBySearchByTypeAndName(@Path("type") type: String): AttractionModel

    @GET("attraction/search/name")
    suspend fun getAttractionBySearchByName(): AttractionModel

    @GET("attraction/{type}/address")
    suspend fun getAttractionByTypeAndAddress(@Path("type") type: String): AttractionModel

    @GET("attraction/my")
    suspend fun getMyAttraction(): List<AttractionModel>

    @POST("attraction/register-business")
    suspend fun createAttraction(
        @Header("Authorization") token: String,
        @Body attraction: AttractionCreateForm,
    ): AttractionModel

    @POST("attraction/delete/{name}")
    suspend fun postDeleteAttraction(@Path("name") name: String): AttractionModel

    @POST("attraction/edit/{currentAttractionName}")
    suspend fun postEditAttraction(@Path("currentAttractionName") currentAttractionName: String): String

    @POST("like/add/{attractionName}")
    suspend fun likeAttraction(
        @Header("Authorization") token: String,
        @Path("attractionName") attractionName: String,
    )
}