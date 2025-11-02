package com.example.myapplication.views.attraction.features.models

import android.net.Uri

data class ParkFacilityModel(
    val name: String? = null,
    val description: String? = null,
    val imageUris: List<Uri> = emptyList(), // Store URIs instead of String
    val imageFormat: String? = null,
    val openTime: String? = null,
    val closeTime: String? = null,
)