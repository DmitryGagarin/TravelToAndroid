package com.example.myapplication.models

data class PagedDiscussionResponse(
    val _embedded: EmbeddedDiscussionList
)

data class EmbeddedDiscussionList(
    val discussionModelList: List<DiscussionModel>
)