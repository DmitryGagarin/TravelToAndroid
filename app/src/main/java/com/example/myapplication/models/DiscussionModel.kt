package com.example.myapplication.models

data class DiscussionModel (
    val title: String?,
    val contentLike: String?,
    val contentDislike: String?,
    val content: String?,
    val rating: Double?,
    val author: String?,
    val createdAt: String?,
    val images: List<ByteArray>?,
    val imageFormats: List<String>?
)