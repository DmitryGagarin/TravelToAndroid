package com.example.traveltoandroid.viewModels.user

import android.content.Context
import com.example.traveltoandroid.models.AuthUser
import kotlinx.coroutines.flow.StateFlow

interface ISignUpViewModel {
    val user: StateFlow<AuthUser?>
    val isLoading: StateFlow<Boolean>
    val error: StateFlow<String?>
    val verificationIsLoading: StateFlow<Boolean>
    val verificationError: StateFlow<String?>

    fun signUpUserFirst(
        email: String,
        password: String,
        privacyPoliceAgreed: Boolean,
        userAgreement: Boolean,
        mailingAgreement: Boolean,
        context: Context,
        onSuccess: () -> Unit
    )

    fun signUpUserSecond(
        name: String,
        surname: String,
        context: Context,
        onSuccess: () -> Unit
    )
}