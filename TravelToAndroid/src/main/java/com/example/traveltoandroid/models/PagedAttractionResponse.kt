package com.example.traveltoandroid.models

data class PagedAttractionResponse(
    val _embedded: EmbeddedAttractionList
)

data class EmbeddedAttractionList(
    val attractionModelList: List<AttractionModel>
)