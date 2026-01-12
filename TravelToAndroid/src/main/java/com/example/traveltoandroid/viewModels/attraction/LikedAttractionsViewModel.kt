package com.example.traveltoandroid.viewModels.user

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traveltoandroid.models.AttractionModel
import com.example.traveltoandroid.repository.attraction.AttractionRepository
import com.example.traveltoandroid.repository.attraction.LikeRepository
import com.example.traveltoandroid.utils.RetrofitClient
import com.example.traveltoandroid.utils.getAccessToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LikedAttractionsViewModel(
    private val likeRepository: LikeRepository = RetrofitClient.likeRepository,
    private val attractionRepository: AttractionRepository = RetrofitClient.attractionRepository
) : ViewModel() {

    private val _likedAttractions = MutableStateFlow<List<AttractionModel>>(emptyList())
    val likedAttractions: StateFlow<List<AttractionModel>> get() = _likedAttractions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error.asStateFlow()

    fun loadLikedAttractions(context: Context) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val token = getAccessToken(context)

                // Get liked attraction names
                val likedNames = likeRepository.getUserLikedAttractions(token)
                    .mapNotNull { it.name }

                // Fetch full attraction details for each liked attraction
                val attractions = mutableListOf<AttractionModel>()
                for (name in likedNames) {
                    try {
                        val attraction = attractionRepository.getAttractionByName(token, name)
                        attraction?.let { attractions.add(it) }
                    } catch (e: Exception) {
                        // Skip if we can't fetch this attraction
                        continue
                    }
                }

                _likedAttractions.value = attractions
            } catch (e: Exception) {
                _error.value = "Failed to load liked attractions: ${e.message}"
                _likedAttractions.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refresh(context: Context) {
        _likedAttractions.value = emptyList()
        loadLikedAttractions(context)
    }
}