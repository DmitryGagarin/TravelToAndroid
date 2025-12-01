package com.example.traveltoandroid.views.user

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.example.traveltoandroid.models.UserModel
import com.example.traveltoandroid.views.utils.FakeUserViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: FakeUserViewModel

    @Before
    fun setUp() {
        viewModel = FakeUserViewModel()
    }

    private fun setComposeContent() {
        composeTestRule.setContent {
            ProfileView(
                navController = rememberNavController(),
                viewModel = viewModel
            )
        }
    }

    @Test
    fun whenLoading_shouldShowSpinner() {
        viewModel.isLoading.value = true
        setComposeContent()
        composeTestRule.onNodeWithTag("loading_indicator").assertExists()
        composeTestRule.onNodeWithTag("error_shown").assertDoesNotExist()
        composeTestRule.onNodeWithTag("user_shown").assertDoesNotExist()
    }

    @Test
    fun whenLoaded_shouldShowUser() {
        viewModel.user.value = UserModel(
            "test",
            "test",
            "test",
            "test",
            "test",
            emptyList(),
            "test",
            false,
        )
        setComposeContent()
        composeTestRule.onNodeWithTag("loading_indicator").assertDoesNotExist()
        composeTestRule.onNodeWithTag("error_shown").assertDoesNotExist()
        composeTestRule.onNodeWithTag("user_shown").assertExists()
    }

    @Test
    fun whenLoaded_shouldShowError() {
        viewModel.error.value = "Network error"
        setComposeContent()
        composeTestRule.onNodeWithTag("loading_indicator").assertDoesNotExist()
        composeTestRule.onNodeWithTag("error_shown").assertExists()
        composeTestRule.onNodeWithTag("user_shown").assertDoesNotExist()
    }
}