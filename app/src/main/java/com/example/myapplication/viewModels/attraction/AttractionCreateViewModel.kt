package com.example.myapplication.viewModels.attraction

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.attractions.AttractionCreateForm
import com.example.myapplication.form.ParkFacilityCreateForm
import com.example.myapplication.utils.RetrofitClient
import com.example.myapplication.utils.getAccessToken
import com.example.myapplication.views.attraction.features.models.ParkFacilityModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

class AttractionCreateViewModel : ViewModel() {

    private val _isLoadingAttraction = MutableStateFlow(false)
    val isLoadingAttraction: StateFlow<Boolean> get() = _isLoadingAttraction.asStateFlow()

    private val _errorAttraction = MutableStateFlow<String?>(null)
    val errorAttraction: StateFlow<String?> get() = _errorAttraction.asStateFlow()

    private val _attractionCreatedSuccessfully = MutableStateFlow(false)

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
        attractionImages: List<MultipartBody.Part> = emptyList(),
        parkFacilities: List<ParkFacilityModel> = emptyList(),
        posters: List<MultipartBody.Part> = emptyList()
    ) {
        _isLoadingAttraction.value = true
        _errorAttraction.value = null
        _attractionCreatedSuccessfully.value = false

        viewModelScope.launch {
            try {
                // Step 1: Create attraction
                val attractionResult = createAttractionStep(
                    context = context,
                    ownerTelegram = ownerTelegram,
                    attractionName = attractionName,
                    description = description,
                    address = address,
                    phone = phone,
                    website = website,
                    attractionType = attractionType,
                    isRoundTheClock = isRoundTheClock,
                    openTime = openTime,
                    closeTime = closeTime,
                    attractionImages = attractionImages
                )

                if (attractionResult) {
                    // Step 2: Convert facility URIs to MultipartBody.Part
                    val facilityImages = convertFacilityUrisToMultipart(context, parkFacilities)

                    // Step 3: Create features with the actual images
                    createFeaturesStep(
                        context = context,
                        attractionType = attractionType,
                        attractionName = attractionName,
                        parkFacilities = parkFacilities,
                        parkFacilityImages = facilityImages, // Now contains actual images
                        posters = posters
                    )

                    deleteFromCache(context)
                    _attractionCreatedSuccessfully.value = true
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _errorAttraction.value = "Operation failed: ${e.message}"
            } finally {
                _isLoadingAttraction.value = false
            }
        }
    }

    private suspend fun createAttractionStep(
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
        attractionImages: List<MultipartBody.Part>
    ): Boolean {
        return try {
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

            // Save to cache before attempting API call
            saveToContext(context, attractionCreateForm)

            val gson = Gson()
            val formJson = gson.toJson(attractionCreateForm)
            val attractionRequestBody = formJson.toRequestBody("application/json".toMediaTypeOrNull())

            RetrofitClient.attractionService.createAttraction(
                getAccessToken(context),
                attractionRequestBody,
                attractionImages
            )
            true // Success
        } catch (e: Exception) {
            e.printStackTrace()
            _errorAttraction.value = when {
                e.message?.contains("EPERM") == true -> "Failed to create attraction: Network permission denied."
                e.message?.contains("failed to connect") == true -> "Failed to create attraction: Cannot connect to server."
                e.message?.contains("timeout") == true -> "Failed to create attraction: Connection timeout."
                else -> "Failed to create attraction: ${e.message}"
            }
            false // Failure
        }
    }

    private suspend fun createFeaturesStep(
        context: Context,
        attractionType: String,
        attractionName: String,
        parkFacilities: List<ParkFacilityModel>,
        parkFacilityImages: List<MultipartBody.Part>,
        posters: List<MultipartBody.Part>
    ) {
        try {
            when (attractionType.lowercase()) {
                "park" -> {
                    if (parkFacilities.isNotEmpty()) {
                        createParkFacilities(
                            context = context,
                            attractionName = attractionName,
                            parkFacilities = parkFacilities,
                            parkFacilityImages = parkFacilityImages
                        )
                    }
                }
                "theater", "gallery", "museum" -> {
                    if (posters.isNotEmpty()) {
                        createPosters(
                            context = context,
                            attractionName = attractionName,
                            posters = posters
                        )
                    }
                }
                // Add other attraction types as needed
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Don't override attraction creation success, just log the feature error
            _errorAttraction.value = "Attraction created but features failed: ${e.message}"
        }
    }

    private suspend fun createParkFacilities(
        context: Context,
        attractionName: String,
        parkFacilities: List<ParkFacilityModel>,
        parkFacilityImages: List<MultipartBody.Part> // This should now contain actual images
    ) {
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

            val gson = Gson()
            val formJson = gson.toJson(parkFacilityCreateForm)
            val facilitiesRequestBody = formJson.toRequestBody("application/json".toMediaTypeOrNull())

            RetrofitClient.attractionService.saveParkFacilities(
                getAccessToken(context),
                attractionName,
                facilitiesRequestBody,
                parkFacilityImages // Now this contains the actual images
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to create park facilities: ${e.message}")
        }
    }

    private suspend fun createPosters(
        context: Context,
        attractionName: String,
        posters: List<MultipartBody.Part>
    ) {
        RetrofitClient.attractionService.savePosters(
            getAccessToken(context),
            attractionName,
            posters
        )
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
        val savedForm = extractSavedFromContent(context)
        createAttraction(
            context = context,
            ownerTelegram = savedForm.ownerTelegram,
            attractionName = savedForm.attractionName,
            description = savedForm.description,
            address = savedForm.address,
            phone = savedForm.phone,
            website = savedForm.website,
            attractionType = savedForm.attractionType,
            isRoundTheClock = savedForm.isRoundTheClock,
            openTime = savedForm.openTime,
            closeTime = savedForm.closeTime,
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
            closeTime = sharedPref.getString("closeTime", "") ?: "",
        )
    }

    private fun convertFacilityUrisToMultipart(
        context: Context,
        parkFacilities: List<ParkFacilityModel>
    ): List<MultipartBody.Part> {
        val multipartParts = mutableListOf<MultipartBody.Part>()

        parkFacilities.forEachIndexed { index, facility ->
            facility.imageUris.forEach { uri ->
                try {
                    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                    inputStream?.use { stream ->
                        // Get file name from URI or generate one
                        val fileName = getFileNameFromUri(context, uri) ?: "facility_${index}_image_${System.currentTimeMillis()}.jpg"

                        // Read stream into byte array
                        val fileBytes = stream.readBytes()

                        // Create request body
                        val requestBody = RequestBody.create(
                            "image/*".toMediaTypeOrNull(),
                            fileBytes
                        )

                        // Create multipart part
                        val part = MultipartBody.Part.createFormData(
                            "images", // This must match @RequestPart(value = "images") in backend
                            fileName,
                            requestBody
                        )

                        multipartParts.add(part)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Handle error - maybe log it or show to user
                }
            }
        }

        return multipartParts
    }

    private fun getFileNameFromUri(context: Context, uri: android.net.Uri): String? {
        return context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val displayNameIndex = cursor.getColumnIndex("_display_name")
                if (displayNameIndex != -1) {
                    cursor.getString(displayNameIndex)
                } else {
                    null
                }
            } else {
                null
            }
        }
    }
}

