package com.example.traveltoandroid.repository.attraction

import com.example.traveltoandroid.models.AttractionModel
import com.example.traveltoandroid.service.AttractionService
import com.example.traveltoandroid.views.attraction.features.models.ParkFacilityModel
import com.example.traveltoandroid.views.attraction.features.models.PosterModel
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AttractionRepository(
    private val service: AttractionService
) {
    suspend fun getPublishedAttractions(): List<AttractionModel> {
        return service.getPublishedAttractions()._embedded.attractionModelList
    }

    suspend fun createAttraction(
        token: String,
        json: RequestBody,
        images: List<MultipartBody.Part>
    ): AttractionModel {
        return service.createAttraction(token, json, images)
    }

    suspend fun saveParkFacilities(
        token: String,
        attractionName: String,
        facilitiesRequestBody: RequestBody,
        parkFacilityImages: List<MultipartBody.Part>
    ): List<ParkFacilityModel> {
        return service.saveParkFacilities(
            token, attractionName, facilitiesRequestBody, parkFacilityImages
        )
    }

    suspend fun savePosters(
        token: String,
        attractionName: String,
        posters: List<MultipartBody.Part>
    ): List<PosterModel> {
        return service.savePosters(token, attractionName, posters)
    }

    suspend fun getAttractionByName(
        token: String,
        attractionName: String
    ): AttractionModel {
        return service.getAttractionByName(token, attractionName)
    }

    suspend fun likeAttraction(
        token: String,
        attractionName: String
    ) {
        return service.likeAttraction(token, attractionName)
    }
}