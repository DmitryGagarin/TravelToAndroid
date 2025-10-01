package com.example.myapplication.service

import com.example.myapplication.form.UserSignInForm
import com.example.myapplication.form.UserSignUpFormFirst
import com.example.myapplication.form.UserSignUpFormSecond
import com.example.myapplication.models.AuthUser
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {

    @POST("signin")
    suspend fun signIn(@Body signInForm: UserSignInForm): AuthUser

    @POST("signup")
    suspend fun signUpFirst(@Body signUpFormFirst: UserSignUpFormFirst): AuthUser

    @POST("signup/name")
    suspend fun signUpSecond(
        @Header("Authorization") token: String,
        @Body signUpFormSecond: UserSignUpFormSecond
    ): AuthUser

}