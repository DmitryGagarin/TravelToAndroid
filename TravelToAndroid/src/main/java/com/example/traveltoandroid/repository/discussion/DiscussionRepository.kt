package com.example.traveltoandroid.repository.discussion

import com.example.traveltoandroid.form.CreateDiscussionForm
import com.example.traveltoandroid.models.DiscussionModel
import com.example.traveltoandroid.service.DiscussionService

class DiscussionRepository(
    private val service: DiscussionService
) {
    suspend fun getDiscussionsByAttractionName(
        attractionName: String,
        token: String
    ): List<DiscussionModel> {
        val result =  service.getDiscussionsByAttractionName(attractionName, token)
        return result._embedded.discussionModelList
    }

    suspend fun sendDiscussion(
        attractionName: String,
        token: String,
        form: CreateDiscussionForm
    ): DiscussionModel {
        return service.sendDiscussion(attractionName, token, form)
    }
}