package com.example.traveltoandroid.viewModels.user

import android.content.Context
import com.example.traveltoandroid.models.UserModel
import kotlinx.coroutines.flow.StateFlow

interface IUserViewModel {
    val user: StateFlow<UserModel?>
    val isLoading: StateFlow<Boolean>
    val error: StateFlow<String?>

    fun getUser(context: Context)
    fun refreshUser(context: Context)
    fun editUserData(
        context: Context,
        name: String?,
        surname: String?,
        email: String?,
        phone: String?,
        onSuccess: () -> Unit
    )
}