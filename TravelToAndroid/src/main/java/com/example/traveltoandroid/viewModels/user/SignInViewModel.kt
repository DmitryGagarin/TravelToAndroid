package com.example.traveltoandroid.viewModels.user

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traveltoandroid.form.UserSignInForm
import com.example.traveltoandroid.models.AuthUser
import com.example.traveltoandroid.repository.user.UserRepository
import com.example.traveltoandroid.utils.RetrofitClient
import com.example.traveltoandroid.viewModels.user.interfaces.ISignInViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val repository: UserRepository = RetrofitClient.userRepository
) : ViewModel(), ISignInViewModel {
    private val _user = MutableStateFlow<AuthUser?>(null)
    override val user: StateFlow<AuthUser?> get() = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    override val error: StateFlow<String?> get() = _error.asStateFlow()

    override fun signInUser(
        login: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit
    ) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val form = UserSignInForm(login, password)
//                val response = RetrofitClient.userService.signIn(form)
                val response = repository.signIn(form)
                _user.value = response
                saveUserToSharedPrefs(context, response)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Login failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun saveUserToSharedPrefs(context: Context, user: AuthUser) {
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("access_token", user.accessToken)
            putString("user_email", user.email)
            putString("user_name", user.name)
            putBoolean("is_logged_in", true)
            apply()
        }
    }
}