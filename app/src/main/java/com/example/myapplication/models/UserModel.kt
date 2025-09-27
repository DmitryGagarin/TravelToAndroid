package com.example.myapplication.models

import java.time.LocalDateTime

class UserModel(
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val createdAt: LocalDateTime,
    val roles: Set<String>,
    val accessToken: String,
    val answeredUsabilityQuestionnaire: Boolean,
)