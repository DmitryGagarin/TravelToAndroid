package com.example.myapplication.attractions

import com.example.myapplication.models.PagedAttractionResponse
import com.example.myapplication.models.AttractionModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AttractionService {

    @GET("attraction/published")
    suspend fun getPublishedAttractions(): PagedAttractionResponse

    @GET("attraction/{name}")
    suspend fun getAttractionByName(@Path("name") name: String): AttractionModel

    @GET("attraction/search/{type}/name")
    suspend fun getAttractionBySearchByTypeAndName(@Path("type") type: String): AttractionModel

    @GET("attraction/search/name")
    suspend fun getAttractionBySearchByName(): AttractionModel

    @GET("attraction/{type}/address")
    suspend fun getAttractionByTypeAndAddress(@Path("type") type: String): AttractionModel

    @GET("attraction/my")
    suspend fun getMyAttraction(): List<AttractionModel>

    @POST("attraction/register-business")
    suspend fun postCreateAttraction(@Body attraction: AttractionCreateForm): AttractionCreateForm

    @POST("attraction/delete/{name}")
    suspend fun postDeleteAttraction(@Path("name") name: String): AttractionModel

    @POST("attraction/edit/{currentAttractionName}")
    suspend fun postEditAttraction(@Path("currentAttractionName") currentAttractionName: String): String
}