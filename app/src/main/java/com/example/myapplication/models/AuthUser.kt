package com.example.myapplication.models

class AuthUser(
val uuid: String?,
val email: String?,
val phone: String?,
val password: String?,
val accessToken: String?,
val refreshToken: String?,
val name: String?,
val surname: String?,
val isVerified: Boolean?,
val authorities: Set<String>?,
val privacyPoliceAgreed: Boolean?,
val userAgreement: Boolean?,
val mailingAgreement: Boolean?,
)