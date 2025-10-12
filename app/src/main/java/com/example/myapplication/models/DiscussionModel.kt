package com.example.myapplication.models

data class DiscussionModel (
    val title: String?,
    val contentLike: String?,
    val contentDislike: String?,
    val content: String?,
    val rating: Double?,
    val author: String?,
    val createdAt: String?,
    val images: List<String>?,
    val imageFormats: List<String>?
)