package com.example.traveltoandroid.repository.attraction

import com.example.traveltoandroid.models.AttractionModel
import com.example.traveltoandroid.service.LikeService

class LikeRepository(
    private val service: LikeService
) {
    suspend fun getUserLikedAttractions(token: String): List<AttractionModel> {
        return try {
            val response = service.getUserLikedAttractions(token)
            // Return empty list if response is null
            // or if _embedded is null
            // or if attractionModelList is null/empty
            response?._embedded?.attractionModelList ?: emptyList()
        } catch (e: Exception) {
            // Return empty list on any error
            emptyList()
        }
    }

    suspend fun likeAttraction(token: String, attractionName: String) {
        service.likeAttraction(token, attractionName)
    }

    suspend fun dislikeAttraction(token: String, attractionName: String) {
        service.dislikeAttraction(token, attractionName)
    }

    suspend fun isAttractionLiked(token: String, attractionName: String): Boolean {
        return service.isAttractionLiked(token, attractionName)
    }
}