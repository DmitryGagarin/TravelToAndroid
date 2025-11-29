package com.example.traveltoandroid.views.auth

import android.content.Context
import com.example.traveltoandroid.models.AuthUser
import com.example.traveltoandroid.viewModels.user.ISignUpViewModel
import kotlinx.coroutines.flow.MutableStateFlow

private class FakeSignUpViewModel() : ISignUpViewModel {
    override val user = MutableStateFlow<AuthUser?>(null)
    override val isLoading = MutableStateFlow(false)
    override val error = MutableStateFlow<String?>(null)
    override val verificationIsLoading = MutableStateFlow(false)
    override val verificationError = MutableStateFlow<String?>(null)
    override fun signUpUserFirst(
        email: String,
        password: String,
        privacyPoliceAgreed: Boolean,
        userAgreement: Boolean,
        mailingAgreement: Boolean,
        context: Context,
        onSuccess: () -> Unit
    ) {}

    override fun signUpUserSecond(
        name: String,
        surname: String,
        context: Context,
        onSuccess: () -> Unit
    ) {}
}

class RegistrationFirstViewTest {

}