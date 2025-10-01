package com.example.myapplication.viewModels.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.form.UserSignInForm
import com.example.myapplication.models.AuthUser
import com.example.myapplication.utils.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    private val _user = MutableStateFlow<AuthUser?>(null)
    val user: StateFlow<AuthUser?> get() = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error.asStateFlow()

    fun signInUser(
        login: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val form = UserSignInForm(login, password)
                val response = RetrofitClient.userService.signIn(form)
                _user.value = response
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Login failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}