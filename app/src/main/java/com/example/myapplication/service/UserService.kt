package com.example.myapplication.service

import com.example.myapplication.form.UserProfileForm
import com.example.myapplication.form.UserSignInForm
import com.example.myapplication.form.UserSignUpFormFirst
import com.example.myapplication.form.UserSignUpFormSecond
import com.example.myapplication.models.AuthUser
import com.example.myapplication.models.UserModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @POST("signin/verify-account/{email}")
    suspend fun sendVerificationEmail(
        @Header("Authorization") token: String,
        @Path("email") email: String
    )

    @GET("user/get")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): UserModel

    @POST("setting/save-changes")
    suspend fun saveUserChanges(
        @Header("Authorization") token: String,
        @Body form: UserProfileForm
    ): AuthUser
}