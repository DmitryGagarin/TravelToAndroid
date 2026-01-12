package com.example.traveltoandroid.viewModels.attraction

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traveltoandroid.models.AttractionModel
import com.example.traveltoandroid.repository.attraction.AttractionRepository
import com.example.traveltoandroid.repository.attraction.LikeRepository
import com.example.traveltoandroid.utils.RetrofitClient
import com.example.traveltoandroid.utils.getAccessToken
import com.example.traveltoandroid.viewModels.attraction.interfaces.IAttractionsViewModel
import com.example.traveltoandroid.viewModels.attraction.interfaces.ILikeViewModel
import com.example.traveltoandroid.viewModels.attraction.interfaces.ILikeStateViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AttractionsViewModel(
    private val attractionRepository: AttractionRepository = RetrofitClient.attractionRepository,
    private val likeRepository: LikeRepository = RetrofitClient.likeRepository
) : ViewModel(), IAttractionsViewModel, ILikeViewModel, ILikeStateViewModel {

    private val _attractions = MutableStateFlow<List<AttractionModel>>(emptyList())
    override val attractions: StateFlow<List<AttractionModel>> get() = _attractions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    override val error: StateFlow<String?> get() = _error.asStateFlow()

    // Like-related states
    private val _likedAttractionNames = MutableStateFlow<Set<String>>(emptySet())
    override val likedAttractionNames: StateFlow<Set<String>> get() = _likedAttractionNames.asStateFlow()

    private val _isLoadingLikes = MutableStateFlow(false)
    override val isLoadingLikes: StateFlow<Boolean> get() = _isLoadingLikes.asStateFlow()

    private val _likeError = MutableStateFlow<String?>(null)
    override val likeError: StateFlow<String?> get() = _likeError.asStateFlow()

    init {
        loadPublishedAttractions()
    }

    override fun loadPublishedAttractions() {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val response = attractionRepository.getPublishedAttractions()
                _attractions.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = when {
                    e.message?.contains("EPERM") == true -> "Network permission denied."
                    e.message?.contains("failed to connect") == true -> "Cannot connect to server."
                    e.message?.contains("timeout") == true -> "Connection timeout."
                    else -> "Failed to load attractions: ${e.message}"
                }
                _attractions.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    override fun refreshAttractions() {
        _attractions.value = emptyList()
        loadPublishedAttractions()
    }

    override fun loadUserLikes(context: Context) {
        _isLoadingLikes.value = true
        _likeError.value = null

        viewModelScope.launch {
            try {
                val token = getAccessToken(context)
                val likedAttractions = likeRepository.getUserLikedAttractions(token)
                _likedAttractionNames.value = likedAttractions.mapNotNull { it.name }.toSet()
            } catch (e: Exception) {
                _likeError.value = "Failed to load liked attractions: ${e.message}"
            } finally {
                _isLoadingLikes.value = false
            }
        }
    }

    override fun likeAttraction(context: Context, attractionName: String) {
        viewModelScope.launch {
            try {
                // Optimistically update UI
                _likedAttractionNames.update { currentSet ->
                    currentSet + attractionName
                }

                // Call API
                val token = getAccessToken(context)
                likeRepository.likeAttraction(token, attractionName)
            } catch (e: Exception) {
                // Revert on error
                _likedAttractionNames.update { currentSet ->
                    currentSet - attractionName
                }
                _likeError.value = "Failed to like attraction: ${e.message}"
            }
        }
    }

    override fun dislikeAttraction(context: Context, attractionName: String) {
        viewModelScope.launch {
            try {
                // Optimistically update UI
                _likedAttractionNames.update { currentSet ->
                    currentSet - attractionName
                }

                // Call API
                val token = getAccessToken(context)
                likeRepository.dislikeAttraction(token, attractionName)
            } catch (e: Exception) {
                // Revert on error
                _likedAttractionNames.update { currentSet ->
                    currentSet + attractionName
                }
                _likeError.value = "Failed to remove like: ${e.message}"
            }
        }
    }

    override fun isAttractionLiked(attractionName: String): Boolean {
        return _likedAttractionNames.value.contains(attractionName)
    }

    override fun isAttractionLikedFlow(attractionName: String): StateFlow<Boolean> {
        return _likedAttractionNames.map { likedNames ->
            likedNames.contains(attractionName)
        }.stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
    }

    override fun getLikedAttractionsFlow(): StateFlow<List<String>> {
        return _likedAttractionNames.map { likedNames ->
            likedNames.toList()
        }.stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    override fun refreshLikes(context: Context) {
        _likedAttractionNames.value = emptySet()
        loadUserLikes(context)
    }

    // Helper method to get attraction by name
    fun getAttractionByName(name: String): AttractionModel? {
        return _attractions.value.find { it.name == name }
    }
}