package com.example.myapplication.views.attraction.features.models

data class ParkFacility (
    var name: String = "",
    var description: String = "",
    var isRoundTheClock: Boolean = false,
    var openTime: String = "",
    var closeTime: String = ""
)