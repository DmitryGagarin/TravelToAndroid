package com.example.traveltoandroid.views.auth

import android.content.Context
import com.example.traveltoandroid.models.AuthUser
import com.example.traveltoandroid.viewModels.user.interfaces.ISignUpViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSignUpViewModel() : ISignUpViewModel {
    override val user = MutableStateFlow<AuthUser?>(null)
    override val isLoading = MutableStateFlow(false)
    override val error = MutableStateFlow<String?>(null)
    override val verificationIsLoading = MutableStateFlow(false)
    override val verificationError = MutableStateFlow<String?>(null)

    var buttonIsClicked = false

    override fun signUpUserFirst(
        email: String,
        password: String,
        privacyPoliceAgreed: Boolean,
        userAgreement: Boolean,
        mailingAgreement: Boolean,
        context: Context,
        onSuccess: () -> Unit
    ) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            buttonIsClicked = true
        }
    }

    override fun signUpUserSecond(
        name: String,
        surname: String,
        context: Context,
        onSuccess: () -> Unit
    ) {}
}
