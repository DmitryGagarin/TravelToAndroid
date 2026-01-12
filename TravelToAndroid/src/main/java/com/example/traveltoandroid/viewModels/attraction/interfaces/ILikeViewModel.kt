package com.example.traveltoandroid.viewModels.attraction.interfaces

import android.content.Context
import kotlinx.coroutines.flow.StateFlow

interface ILikeViewModel {
    // Get flow for a specific attraction's like status
    fun isAttractionLikedFlow(attractionName: String): StateFlow<Boolean>

    // Load all user likes
    fun loadUserLikes(context: Context)

    // Like an attraction
    fun likeAttraction(context: Context, attractionName: String)

    // Dislike an attraction
    fun dislikeAttraction(context: Context, attractionName: String)

    // Get all liked attractions
    fun getLikedAttractionsFlow(): StateFlow<List<String>>

    // Check if attraction is liked (non-flow version)
    fun isAttractionLiked(attractionName: String): Boolean

    // Refresh likes
    fun refreshLikes(context: Context)
}