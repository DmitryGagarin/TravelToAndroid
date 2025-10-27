package com.example.myapplication.viewModels.attraction

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.attractions.AttractionCreateForm
import com.example.myapplication.form.ParkFacilityCreateForm
import com.example.myapplication.utils.RetrofitClient
import com.example.myapplication.utils.getAccessToken
import com.example.myapplication.views.attraction.features.models.ParkFacilityModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

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
        closeTime: String,
        // TODO don't want to have empty lists, better to pass something
        parkFacilities: List<ParkFacilityModel> = emptyList(),
        posters: List<MultipartBody.Part> = emptyList()
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
                handleAttractionFeatureSave(
                    attractionType,
                    context,
                    attractionName,
                    parkFacilities,
                    posters
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

    private suspend fun handleAttractionFeatureSave(
        attractionType: String,
        context: Context,
        attractionName: String,
        parkFacilities: List<ParkFacilityModel>,
        posters: List<MultipartBody.Part>
    ) {
        when (attractionType) {
            "park" -> {
                try {
                    val names = mutableListOf<String>()
                    val descriptions = mutableListOf<String>()
                    val openTimes = mutableListOf<String>()
                    val closeTimes = mutableListOf<String>()

                    for (item in parkFacilities) {
                        item.name?.let { names.add(it) }
                        item.description?.let { descriptions.add(it) }
                        item.openTime?.let { openTimes.add(it) }
                        item.closeTime?.let { closeTimes.add(it) }
                    }

                    val parkFacilityCreateForm = ParkFacilityCreateForm(
                        names, descriptions, openTimes, closeTimes
                    )

                    RetrofitClient.attractionService.saveParkFacilities(
                        getAccessToken(context),
                        attractionName,
                        parkFacilityCreateForm
                    )
                } catch (e: Exception) {
                    e.printStackTrace()

                    _errorAttraction.value = when {
                        e.message?.contains("EPERM") == true -> "Network permission denied. Check internet permissions."
                        e.message?.contains("failed to connect") == true -> "Cannot connect to server. Check URL and server status."
                        e.message?.contains("timeout") == true -> "Connection timeout. Server might be down."
                        else -> "Failed to save park facilities: ${e.message}"
                    }
                } finally {
                    _isLoadingAttraction.value = false
                }
            }

            "theater", "gallery", "museum" -> {
                try {
                    RetrofitClient.attractionService.savePosters(
                        getAccessToken(context),
                        attractionName,
                        posters
                    )
                } catch (e: Exception) {
                    e.printStackTrace()

                    _errorAttraction.value = when {
                        e.message?.contains("EPERM") == true -> "Network permission denied. Check internet permissions."
                        e.message?.contains("failed to connect") == true -> "Cannot connect to server. Check URL and server status."
                        e.message?.contains("timeout") == true -> "Connection timeout. Server might be down."
                        else -> "Failed to save posters: ${e.message}"
                    }
                } finally {
                    _isLoadingAttraction.value = false
                }
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