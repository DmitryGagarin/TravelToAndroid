package com.example.traveltoandroid.repository.discussion

import com.example.traveltoandroid.form.CreateDiscussionForm
import com.example.traveltoandroid.models.DiscussionModel
import com.example.traveltoandroid.models.PagedDiscussionResponse
import com.example.traveltoandroid.service.DiscussionService

class DiscussionRepository(
    private val service: DiscussionService
) {
    suspend fun getDiscussionsByAttractionName(
        attractionName: String,
        token: String
    ): PagedDiscussionResponse {
        return service.getDiscussionsByAttractionName(
            attractionName, token
        )
    }

    suspend fun sendDiscussion(
        attractionName: String,
        token: String,
        form: CreateDiscussionForm
    ): DiscussionModel {
        return service.sendDiscussion(attractionName, token, form)
    }
}