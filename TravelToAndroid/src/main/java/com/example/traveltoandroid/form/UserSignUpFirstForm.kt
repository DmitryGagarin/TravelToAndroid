package com.example.traveltoandroid.form

data class UserSignUpFirstForm (
    val email: String?,
    val password: String?,
    val privacyPoliceAgreed: Boolean?,
    val userAgreement: Boolean?,
    val mailingAgreement: Boolean?,
)