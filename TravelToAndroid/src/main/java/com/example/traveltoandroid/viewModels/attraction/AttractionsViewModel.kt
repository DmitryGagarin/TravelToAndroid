package com.example.traveltoandroid.viewModels.attraction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traveltoandroid.models.AttractionModel
import com.example.traveltoandroid.utils.RetrofitClient
import com.example.traveltoandroid.viewModels.attraction.interfaces.IAttractionsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AttractionsViewModel : ViewModel(), IAttractionsViewModel {
    private val _attractions = MutableStateFlow<List<AttractionModel>>(emptyList())
    override val attractions: StateFlow<List<AttractionModel>> get() = _attractions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    override val error: StateFlow<String?> get() = _error.asStateFlow()

    init {
        loadPublishedAttractions()
    }

    override fun loadPublishedAttractions() {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val response = RetrofitClient.attractionService.getPublishedAttractions()
                _attractions.value = response._embedded.attractionModelList
            } catch (e: Exception) {
                e.printStackTrace()

                _error.value = when {
                    e.message?.contains("EPERM") == true -> "Network permission denied. Check internet permissions."
                    e.message?.contains("failed to connect") == true -> "Cannot connect to server. Check URL and server status."
                    e.message?.contains("timeout") == true -> "Connection timeout. Server might be down."
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
}
