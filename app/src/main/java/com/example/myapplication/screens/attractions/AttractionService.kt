package com.example.myapplication.screens.attractions

import com.example.myapplication.screens.attractions.models.FullAttractionModel
import com.example.myapplication.screens.attractions.models.SimpleAttractionModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AttractionService {

    @GET("attraction/published")
    suspend fun getPublishedAttractions(): List<FullAttractionModel>

    @GET("attraction/{name}")
    suspend fun getAttractionByName(@Path("name") name: String): SimpleAttractionModel

    @GET("attraction/search/{type}/name")
    suspend fun getAttractionBySearchByTypeAndName(@Path("type") type: String): SimpleAttractionModel

    @GET("attraction/search/name")
    suspend fun getAttractionBySearchByName(): SimpleAttractionModel

    @GET("attraction/{type}/address")
    suspend fun getAttractionByTypeAndAddress(@Path("type") type: String): SimpleAttractionModel

    @GET("attraction/my")
    suspend fun getMyAttraction(): List<SimpleAttractionModel>

    @POST("attraction/register-business")
    suspend fun postCreateAttraction(@Body attraction: AttractionCreateForm): AttractionCreateForm

    @POST("attraction/delete/{name}")
    suspend fun postDeleteAttraction(@Path("name") name: String): SimpleAttractionModel

    @POST("attraction/edit/{currentAttractionName}")
    suspend fun postEditAttraction(@Path("currentAttractionName") currentAttractionName: String): String
}