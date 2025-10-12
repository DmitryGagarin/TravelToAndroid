package com.example.myapplication.viewModels.attraction

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.AttractionModel
import com.example.myapplication.models.DiscussionModel
import com.example.myapplication.utils.RetrofitClient
import com.example.myapplication.utils.getAccessToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AttractionViewModel(
    private val attractionName: String
) : ViewModel() {

    private val _attraction = MutableStateFlow<AttractionModel?>(null)
    val attraction: StateFlow<AttractionModel?> get() = _attraction.asStateFlow()

    private val _isLoadingAttraction = MutableStateFlow(false)
    val isLoadingAttraction: StateFlow<Boolean> get() = _isLoadingAttraction.asStateFlow()

    private val _errorAttraction = MutableStateFlow<String?>(null)
    val errorAttraction: StateFlow<String?> get() = _errorAttraction.asStateFlow()

    private val _discussions = MutableStateFlow<List<DiscussionModel>>(emptyList())
    val discussions: StateFlow<List<DiscussionModel>> get() = _discussions.asStateFlow()

    private val _isLoadingDiscussions = MutableStateFlow(false)
    val isLoadingDiscussions: StateFlow<Boolean> get() = _isLoadingDiscussions.asStateFlow()

    private val _errorDiscussions = MutableStateFlow<String?>(null)
    val errorDiscussions: StateFlow<String?> get() = _errorDiscussions.asStateFlow()

    internal fun loadAttractionData(context: Context) {
        loadAttractionByName(context)
        loadDiscussionsForAttraction()
    }

    private fun loadAttractionByName(
        context: Context
    ) {
        _isLoadingAttraction.value = true
        _errorAttraction.value = null

        viewModelScope.launch {
            try {
                val response = RetrofitClient.attractionService.getAttractionByName(
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

    private fun loadDiscussionsForAttraction() {
        _isLoadingDiscussions.value = true
        _errorDiscussions.value = null

        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.discussionService.getDiscussionsByAttractionName(attractionName)
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

    fun refreshAttractionAndDiscussions(context: Context) {
        _attraction.value = null
        _discussions.value = emptyList()
        loadAttractionData(context)
    }

}