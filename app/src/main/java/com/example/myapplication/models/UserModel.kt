package com.example.myapplication.models

data class UserModel(
    val name: String?,
    val surname: String?,
    val email: String?,
    val phone: String?,
    val createdAt: String?,
    val roles: List<Authority>?,
    val accessToken: String?,
    val answeredUsabilityQuestionnaire: Boolean?,
)