package com.example.traveltoandroid.viewModels.user

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traveltoandroid.form.UserProfileForm
import com.example.traveltoandroid.models.UserModel
import com.example.traveltoandroid.utils.RetrofitClient
import com.example.traveltoandroid.utils.getAccessToken
import com.example.traveltoandroid.utils.saveUserToSharedPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel(), IUserViewModel {
    private val _user = MutableStateFlow<UserModel?>(null)
    override val user: StateFlow<UserModel?> get() = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    override val error: StateFlow<String?> get() = _error.asStateFlow()

    override fun getUser(
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

    override fun refreshUser(context: Context) {
        _user.value = null
        getUser(context = context)
    }

    override fun editUserData(
        context: Context,
        name: String?,
        surname: String?,
        email: String?,
        phone: String?,
        onSuccess: () -> Unit
    ) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                val form = UserProfileForm(
                    name = name,
                    surname = surname,
                    email = email,
                    phone = phone
                )
                val response = RetrofitClient.userService.saveUserChanges(getAccessToken(context), form)
                onSuccess()
                saveUserToSharedPrefs(context, response)
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Impossible to do changes in profile data"
            } finally {
                _isLoading.value = false
            }
        }
    }
}