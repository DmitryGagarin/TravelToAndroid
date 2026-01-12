package com.example.traveltoandroid.viewModels.attraction.interfaces

import kotlinx.coroutines.flow.StateFlow

interface ILikeStateViewModel {
    val likedAttractionNames: StateFlow<Set<String>>
    val isLoadingLikes: StateFlow<Boolean>
    val likeError: StateFlow<String?>
}