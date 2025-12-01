package com.example.traveltoandroid.views.utils

import android.content.Context
import com.example.traveltoandroid.models.UserModel
import com.example.traveltoandroid.viewModels.user.interfaces.IUserViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class FakeUserViewModel : IUserViewModel {
    override val user = MutableStateFlow<UserModel?>(null)
    override val isLoading = MutableStateFlow(false)
    override val error = MutableStateFlow<String?>(null)

    var buttonClicked = false

    override fun getUser(context: Context) {}
    override fun refreshUser(context: Context) {}
    override fun editUserData(
        context: Context,
        name: String?,
        surname: String?,
        email: String?,
        phone: String?,
        onSuccess: () -> Unit
    ) {buttonClicked = true}

}
