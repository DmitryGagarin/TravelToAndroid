package com.example.traveltoandroid.viewModels.attraction

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traveltoandroid.models.AttractionModel
import com.example.traveltoandroid.models.DiscussionModel
import com.example.traveltoandroid.repository.attraction.AttractionRepository
import com.example.traveltoandroid.repository.discussion.DiscussionRepository
import com.example.traveltoandroid.utils.RetrofitClient
import com.example.traveltoandroid.utils.getAccessToken
import com.example.traveltoandroid.viewModels.attraction.interfaces.IAttractionViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AttractionViewModel(
    private val attractionName: String,
    private val attractionRepository: AttractionRepository = RetrofitClient.attractionRepository,
    private val discussionRepository: DiscussionRepository = RetrofitClient.discussionRepository
) : ViewModel(), IAttractionViewModel {

    private val _attraction = MutableStateFlow<AttractionModel?>(null)
    override val attraction: StateFlow<AttractionModel?> get() = _attraction.asStateFlow()

    private val _isLoadingAttraction = MutableStateFlow(false)
    override val isLoadingAttraction: StateFlow<Boolean> get() = _isLoadingAttraction.asStateFlow()

    private val _errorAttraction = MutableStateFlow<String?>(null)
    override val errorAttraction: StateFlow<String?> get() = _errorAttraction.asStateFlow()

    private val _discussions = MutableStateFlow<List<DiscussionModel>>(emptyList())
    override val discussions: StateFlow<List<DiscussionModel>> get() = _discussions.asStateFlow()

    private val _isLoadingDiscussions = MutableStateFlow(false)
    override val isLoadingDiscussions: StateFlow<Boolean> get() = _isLoadingDiscussions.asStateFlow()

    private val _errorDiscussions = MutableStateFlow<String?>(null)
    override val errorDiscussions: StateFlow<String?> get() = _errorDiscussions.asStateFlow()

    private val _isLoadingLike = MutableStateFlow(false)
    override val isLoadingLike: StateFlow<Boolean> get() = _isLoadingAttraction.asStateFlow()

    private val _errorLike = MutableStateFlow<String?>(null)
    override val errorLike: StateFlow<String?> get() = _errorLike.asStateFlow()


    override fun loadAttractionData(context: Context) {
        loadAttractionByName(context)
        loadDiscussionsForAttraction(context)
    }

    private fun loadAttractionByName(
        context: Context
    ) {
        _isLoadingAttraction.value = true
        _errorAttraction.value = null

        viewModelScope.launch {
            try {
//                val response = RetrofitClient.attractionService.getAttractionByName(
//                    getAccessToken(context),
//                    attractionName
//                )
                val response = attractionRepository.getAttractionByName(
                    getAccessToken(context),
                    attractionName
                )
                _attraction.value = response
            } catch (e: Exception) {
                e.printStackTrace()

                _errorAttraction.value = when {
                    e.message?.contains("EPERM") == true -> "Network permission denied. Check internet permissions."
                    e.message?.contains("failed to connect") == true -> "Cannot connect to server. Check URL and server status."
                    e.message?.contains("timeout") == true -> "Connection timeout. Server might be down."
                    else -> "Failed to load attractions: ${e.message}"
                }

                _attraction.value = null
            } finally {
                _isLoadingAttraction.value = false
            }
        }
    }

    private fun loadDiscussionsForAttraction(
        context: Context
    ) {
        _isLoadingDiscussions.value = true
        _errorDiscussions.value = null

        viewModelScope.launch {
            try {
//                val response = RetrofitClient.discussionService.getDiscussionsByAttractionName(attractionName)
                val response = discussionRepository.getDiscussionsByAttractionName(attractionName, getAccessToken(context))
                _discussions.value = response._embedded.discussionModelList
            } catch (e: Exception) {
                e.printStackTrace()

                _errorDiscussions.value = when {
                    e.message?.contains("EPERM") == true -> "Network permission denied. Check internet permissions."
                    e.message?.contains("failed to connect") == true -> "Cannot connect to server. Check URL and server status."
                    e.message?.contains("timeout") == true -> "Connection timeout. Server might be down."
                    else -> "Failed to load discussions: ${e.message}"
                }

                _discussions.value = emptyList()
            } finally {
                _isLoadingDiscussions.value = false
            }
        }
    }

    override fun likeAttraction(
        context: Context
    ) {
        _isLoadingLike.value = true
        _errorLike.value = null
        viewModelScope.launch {
            try {
//                RetrofitClient.attractionService.likeAttraction(
//                    getAccessToken(context),
//                    attractionName
//                )
                attractionRepository.likeAttraction(
                    getAccessToken(context),
                    attractionName
                )
            } catch (e: Exception) {
                e.printStackTrace()

                _errorLike.value = when {
                    e.message?.contains("EPERM") == true -> "Network permission denied. Check internet permissions."
                    e.message?.contains("failed to connect") == true -> "Cannot connect to server. Check URL and server status."
                    e.message?.contains("timeout") == true -> "Connection timeout. Server might be down."
                    else -> "Failed to load attractions: ${e.message}"
                }
            } finally {
                _isLoadingLike.value = false
            }
        }
    }

    override fun refreshAttractionAndDiscussions(context: Context) {
        _attraction.value = null
        _discussions.value = emptyList()
        loadAttractionData(context)
    }

}