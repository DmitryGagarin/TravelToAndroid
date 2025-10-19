package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class AttractionModel(
    val uuid: String?,
    val name: String?,
    val description: String?,
    val address: String?,
    val images: List<String>?,
    val imagesFormats: List<String>?,
    val phone: String?,
    val website: String?,
    val type: String?,
    @SerializedName("isRoundTheClock")
    val isRoundTheClock: Boolean?,
    val openTime: String?,
    val closeTime: String?,
    val rating: Double?,
    val status: String?,
)