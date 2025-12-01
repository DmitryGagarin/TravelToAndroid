package com.example.traveltoandroid.viewModels.discussion

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traveltoandroid.form.CreateDiscussionForm
import com.example.traveltoandroid.repository.discussion.DiscussionRepository
import com.example.traveltoandroid.utils.RetrofitClient
import com.example.traveltoandroid.utils.getAccessToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiscussionViewModel(
    private val repository: DiscussionRepository = RetrofitClient.discussionRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error.asStateFlow()

    fun sendDiscussion(
        attractionName: String,
        context: Context,
        title: String,
        liked: String,
        disliked: String,
        content: String,
        rating: String,
        onSuccess: () -> Unit
    ) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val form = CreateDiscussionForm(
                    title,
                    liked,
                    disliked,
                    content,
                    rating
                )
//                RetrofitClient.discussionService.sendDiscussion(
//                    attractionName,
//                    getAccessToken(context),
//                    form
//                )

                repository.sendDiscussion(
                    attractionName,
                    getAccessToken(context),
                    form
                )

                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Impossible to leave discussion: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}