package com.example.traveltoandroid.viewModels.user.interfaces

import android.content.Context
import com.example.traveltoandroid.models.AuthUser
import kotlinx.coroutines.flow.StateFlow

interface ISignInViewModel {
    val user: StateFlow<AuthUser?>
    val isLoading: StateFlow<Boolean>
    val error: StateFlow<String?>
    fun signInUser(
        login: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit
    )
}