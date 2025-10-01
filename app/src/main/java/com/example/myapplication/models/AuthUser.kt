package com.example.myapplication.models

data class AuthUser(
    val email: String?,
    val password: String?,
    val accessToken: String?,
    val refreshToken: String?,
    val name: String?,
    val authorities: List<Authority>?,
    val privacyPoliceAgreed: Boolean?,
    val userAgreement: Boolean?,
    val mailingAgreement: Boolean?,
    val username: String?,
    val verified: Boolean?,
    val enabled: Boolean?,
    val accountNonExpired: Boolean?,
    val accountNonLocked: Boolean?,
    val credentialsNonExpired: Boolean?,
)