package com.example.traveltoandroid.viewModels.attraction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.traveltoandroid.utils.RetrofitClient

@Suppress("UNCHECKED_CAST")
class AttractionViewModelFactory(
    private val attractionName: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AttractionViewModel::class.java)) {
            return AttractionViewModel(
                attractionName = attractionName,
                attractionRepository = RetrofitClient.attractionRepository,
                discussionRepository = RetrofitClient.discussionRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}