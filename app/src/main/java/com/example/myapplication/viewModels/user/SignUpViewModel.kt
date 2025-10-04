package com.example.myapplication.viewModels.user

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.form.UserSignUpFormFirst
import com.example.myapplication.form.UserSignUpFormSecond
import com.example.myapplication.models.AuthUser
import com.example.myapplication.utils.RetrofitClient
import com.example.myapplication.utils.getAccessToken
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

    private val _verificationIsLoading = MutableStateFlow(false)
    val verificationIsLoading: StateFlow<Boolean> get() = _verificationIsLoading.asStateFlow()

    private val _verificationError = MutableStateFlow<String?>(null)
    val verificationError: StateFlow<String?> get() = _verificationError.asStateFlow()

    fun signUpUserFirst(
        email: String,
        password: String,
        privacyPoliceAgreed: Boolean,
        userAgreement: Boolean,
        mailingAgreement: Boolean,
        context: Context,
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
                saveUserToSharedPrefs(context, response)
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
        context: Context,
        onSuccess: () -> Unit
    ) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val form = UserSignUpFormSecond(name, surname)
                val response =
                    RetrofitClient.userService.signUpSecond(getAccessToken(context), form)
                _user.value = response
                onSuccess()
                saveUserToSharedPrefs(context, response)
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Second step of signup failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun sendVerificationEmail(
        context: Context,
    ) {
        _verificationIsLoading.value = true
        _verificationError.value = null

        viewModelScope.launch {
            try {
                RetrofitClient.userService.sendVerificationEmail(
                    getAccessToken(context),
                    getEmail(context)
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _verificationError.value = "Sending verification email failed: ${e.message}"
            } finally {
                _verificationIsLoading.value = false
            }
        }
    }

    private fun saveUserToSharedPrefs(context: Context, user: AuthUser) {
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("access_token", user.accessToken)
            putString("email", user.email)
            putString("user_name", user.name)
            putBoolean("is_logged_in", true)
            apply()
        }
    }

    private fun getEmail(context: Context): String {
        return context
            .getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .getString("email", "email_not_found").toString()
    }
}