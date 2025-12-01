package com.example.traveltoandroid.form

data class AttractionCreateForm (
    val ownerTelegram: String,
    val attractionName: String,
    val description: String,
    val address: String,
    val phone: String,
    val website: String,
    val attractionType: String,
    val isRoundTheClock: Boolean,
    val openTime: String,
    val closeTime: String,
)