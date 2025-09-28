package com.example.myapplication.viewModels.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.form.UserSignUpFormFirst
import com.example.myapplication.form.UserSignUpFormSecond
import com.example.myapplication.models.AuthUser
import com.example.myapplication.utils.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _user = MutableStateFlow<AuthUser?>(null)
    val user: StateFlow<AuthUser?> get() = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error.asStateFlow()

    fun signUpUserFirst(
        email: String,
        password: String,
        privacyPoliceAgreed: Boolean,
        userAgreement: Boolean,
        mailingAgreement: Boolean,
        onSuccess: () -> Unit
    ) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val form = UserSignUpFormFirst(
                    email = email,
                    password = password,
                    privacyPoliceAgreed = privacyPoliceAgreed,
                    userAgreement = userAgreement,
                    mailingAgreement = mailingAgreement
                )
                val response = RetrofitClient.userService.signUpFirst(form)
                _user.value = response
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "First step of signup failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signUpUserSecond(
        name: String,
        surname: String,
        onSuccess: () -> Unit
    ) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val form = UserSignUpFormSecond(name, surname)
                val response = RetrofitClient.userService.signUpSecond(form)
                _user.value = response
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Second step of signup failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}