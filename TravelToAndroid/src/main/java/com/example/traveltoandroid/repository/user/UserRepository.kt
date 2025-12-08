package com.example.traveltoandroid.repository.user

import com.example.traveltoandroid.form.UserProfileForm
import com.example.traveltoandroid.form.UserSignInForm
import com.example.traveltoandroid.form.UserSignUpFirstForm
import com.example.traveltoandroid.form.UserSignUpSecondForm
import com.example.traveltoandroid.models.AuthUser
import com.example.traveltoandroid.models.UserModel
import com.example.traveltoandroid.service.UserService

class UserRepository (
    private val service: UserService
) {
    suspend fun signIn(
        signInForm: UserSignInForm
    ): AuthUser {
        return service.signIn(signInForm)
    }

    suspend fun signUpFirst(
        form: UserSignUpFirstForm
    ): AuthUser {
        return service.signUpFirst(form)
    }

    suspend fun signUpSecond(
        token: String,
        form: UserSignUpSecondForm
    ): AuthUser {
        return service.signUpSecond(token, form)
    }

    suspend fun sendVerificationEmail(
        token: String,
        email: String
    ) {
        return service.sendVerificationEmail(token, email)
    }

    suspend fun getUser(
        token: String
    ): UserModel {
        return service.getUser(token)
    }

    suspend fun saveUserChanges(
        token: String,
        form: UserProfileForm
    ): AuthUser {
        return service.saveUserChanges(token, form)
    }

}