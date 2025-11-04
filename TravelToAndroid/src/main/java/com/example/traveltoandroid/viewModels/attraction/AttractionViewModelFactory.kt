package com.example.traveltoandroid.viewModels.attraction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class AttractionViewModelFactory(
    private val attractionName: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AttractionViewModel::class.java)) {
            return AttractionViewModel(attractionName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
