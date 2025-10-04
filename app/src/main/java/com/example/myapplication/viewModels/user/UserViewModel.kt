package com.example.myapplication.viewModels.user

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.UserModel
import com.example.myapplication.utils.RetrofitClient
import com.example.myapplication.utils.getAccessToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _user = MutableStateFlow<UserModel?>(null)
    val user: StateFlow<UserModel?> get() = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error.asStateFlow()

    fun getUser(
        context: Context,
    ) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val response = RetrofitClient.userService.getUser(
                    getAccessToken(context)
                )
                _user.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Impossible to load profile data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshUser(context: Context) {
        _user.value = null
        getUser(context = context)
    }
}