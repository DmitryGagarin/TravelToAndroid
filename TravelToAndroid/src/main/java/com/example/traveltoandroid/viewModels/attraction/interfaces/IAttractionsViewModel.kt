package com.example.traveltoandroid.viewModels.attraction.interfaces

import com.example.traveltoandroid.models.AttractionModel
import kotlinx.coroutines.flow.StateFlow

interface IAttractionsViewModel {
    val attractions: StateFlow<List<AttractionModel>>
    val isLoading: StateFlow<Boolean>
    val error: StateFlow<String?>
    fun refreshAttractions()
    fun loadPublishedAttractions()
}
