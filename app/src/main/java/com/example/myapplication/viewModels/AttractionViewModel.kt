package com.example.myapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.AttractionModel
import com.example.myapplication.utils.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AttractionViewModel : ViewModel() {
    private val _attractions = MutableStateFlow<List<AttractionModel>>(emptyList())
    val attractions: StateFlow<List<AttractionModel>> get() = _attractions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error.asStateFlow()

    init {
        loadPublishedAttractions()
    }

    fun loadPublishedAttractions() {
        println("🔄 Attempting to connect to: ${RetrofitClient.BASE_URL}")
//        if (_attractions.value.isNotEmpty()) return

        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                println("Testing network connection...")

                // Test with a simple API call first
                val response = RetrofitClient.attractionService.getPublishedAttractions()
                println("✅ API call successful!")
                println("Response: $response")

                _attractions.value = response._embedded.attractionModelList
                println("Loaded ${_attractions.value.size} attractions")

            } catch (e: Exception) {
                println("❌ API call failed: ${e.message}")
                e.printStackTrace()

                // Provide more specific error messages
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

    fun refreshAttractions() {
        _attractions.value = emptyList()
        loadPublishedAttractions()
    }
}