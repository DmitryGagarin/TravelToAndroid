package com.example.myapplication.viewModels.attraction

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.attractions.AttractionCreateForm
import com.example.myapplication.utils.RetrofitClient
import com.example.myapplication.utils.getAccessToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AttractionCreateViewModel : ViewModel() {

    private val _isLoadingAttraction = MutableStateFlow(false)
    val isLoadingAttraction: StateFlow<Boolean> get() = _isLoadingAttraction.asStateFlow()

    private val _errorAttraction = MutableStateFlow<String?>(null)
    val errorAttraction: StateFlow<String?> get() = _errorAttraction.asStateFlow()

    fun createAttraction(
        context: Context,
        ownerTelegram: String,
        attractionName: String,
        description: String,
        address: String,
        phone: String,
        website: String,
        attractionType: String,
        isRoundTheClock: Boolean,
        openTime: String,
        closeTime: String
    ) {

        val attractionCreateForm = AttractionCreateForm(
            ownerTelegram,
            attractionName,
            description,
            address,
            phone,
            website,
            attractionType,
            isRoundTheClock,
            openTime,
            closeTime,
        )

        _isLoadingAttraction.value = true
        _errorAttraction.value = null

        viewModelScope.launch {
            saveToContext(
                context,
                attractionCreateForm
            )
            var success = false
            try {
                RetrofitClient.attractionService.createAttraction(
                    getAccessToken(context),
                    attractionCreateForm
                )
                success = true
            } catch (e: Exception) {
                e.printStackTrace()

                _errorAttraction.value = when {
                    e.message?.contains("EPERM") == true -> "Network permission denied. Check internet permissions."
                    e.message?.contains("failed to connect") == true -> "Cannot connect to server. Check URL and server status."
                    e.message?.contains("timeout") == true -> "Connection timeout. Server might be down."
                    else -> "Failed to save attractions: ${e.message}"
                }
            } finally {
                _isLoadingAttraction.value = false
            }

            if (success) {
                deleteFromCache(context)
            }
        }
    }

    private fun deleteFromCache(context: Context) {
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("ownerTelegram")
            remove("attractionName")
            remove("description")
            remove("address")
            remove("phone")
            remove("website")
            remove("attractionType")
            remove("isRoundTheClock")
            remove("openTime")
            remove("closeTime")

            apply()
        }
    }

    fun refreshSavedAttraction(context: Context) {
        createAttraction(
            context,
            extractSavedFromContent(context).ownerTelegram,
            extractSavedFromContent(context).attractionName,
            extractSavedFromContent(context).description,
            extractSavedFromContent(context).address,
            extractSavedFromContent(context).phone,
            extractSavedFromContent(context).website,
            extractSavedFromContent(context).attractionType,
            extractSavedFromContent(context).isRoundTheClock,
            extractSavedFromContent(context).openTime,
            extractSavedFromContent(context).closeTime,
        )
    }

    private fun saveToContext(context: Context, form: AttractionCreateForm) {
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("ownerTelegram", form.ownerTelegram)
            putString("attractionName", form.attractionName)
            putString("description", form.description)
            putString("address", form.address)
            putString("phone", form.phone)
            putString("website", form.website)
            putString("attractionType", form.attractionType)
            putBoolean("isRoundTheClock", form.isRoundTheClock)
            putString("openTime", form.openTime)
            putString("closeTime", form.closeTime)

            apply()
        }
    }

    private fun extractSavedFromContent(context: Context): AttractionCreateForm {
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        return AttractionCreateForm(
            ownerTelegram = sharedPref.getString("ownerTelegram", "") ?: "",
            attractionName = sharedPref.getString("attractionName", "") ?: "",
            description = sharedPref.getString("description", "") ?: "",
            address = sharedPref.getString("address", "") ?: "",
            phone = sharedPref.getString("phone", "") ?: "",
            website = sharedPref.getString("website", "") ?: "",
            attractionType = sharedPref.getString("attractionType", "") ?: "",
            isRoundTheClock = sharedPref.getBoolean("isRoundTheClock", false),
            openTime = sharedPref.getString("openTime", "") ?: "",
            closeTime = sharedPref.getString("closeTime", "") ?: ""
        )
    }
}