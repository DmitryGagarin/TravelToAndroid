package com.example.traveltoandroid.viewModels.attraction

import android.content.Context
import com.example.traveltoandroid.models.AttractionModel
import com.example.traveltoandroid.models.DiscussionModel
import kotlinx.coroutines.flow.StateFlow

interface IAttractionViewModel {
    val attraction: StateFlow<AttractionModel?>
    val isLoadingAttraction: StateFlow<Boolean>
    val errorAttraction: StateFlow<String?>
    val discussions: StateFlow<List<DiscussionModel>>
    val isLoadingDiscussions: StateFlow<Boolean>
    val errorDiscussions: StateFlow<String?>
    val isLoadingLike: StateFlow<Boolean>
    val errorLike: StateFlow<String?>

    fun loadAttractionData(context: Context)
    fun refreshAttractionAndDiscussions(context: Context)
    fun likeAttraction(context: Context)
}