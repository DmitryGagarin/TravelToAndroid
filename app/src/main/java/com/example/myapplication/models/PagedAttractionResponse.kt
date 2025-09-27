package com.example.myapplication.models

data class PagedAttractionResponse(
    val _embedded: EmbeddedAttractionList
)

data class EmbeddedAttractionList(
    val attractionModelList: List<AttractionModel>
)