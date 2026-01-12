package com.example.traveltoandroid.repository.discussion

import com.example.traveltoandroid.form.CreateDiscussionForm
import com.example.traveltoandroid.models.DiscussionModel
import com.example.traveltoandroid.service.DiscussionService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class DiscussionRepository(
    private val service: DiscussionService
) {
    suspend fun getDiscussionsByAttractionName(
        attractionName: String,
        token: String
    ): List<DiscussionModel> {
        return try {
            val result = service.getDiscussionsByAttractionName(attractionName, token)
            result?._embedded?.attractionDiscussionModelList ?: emptyList()
        } catch (e: Exception) {
            // Return empty list on any error
            emptyList()
        }
    }

    suspend fun sendDiscussion(
        attractionName: String,
        token: String,
        form: CreateDiscussionForm
    ): DiscussionModel {
        val gson = Gson()
        val formJson = gson.toJson(form)
        val attractionRequestBody = formJson.toRequestBody("application/json".toMediaTypeOrNull())

        return service.sendDiscussion(token, attractionName, null, attractionRequestBody)
    }

}