package com.example.traveltoandroid.views.user

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.example.traveltoandroid.models.UserModel
import com.example.traveltoandroid.viewModels.user.interfaces.IUserViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FakeUserViewModel : IUserViewModel {
    override val user = MutableStateFlow<UserModel?>(null)
    override val isLoading = MutableStateFlow(false)
    override val error = MutableStateFlow<String?>(null)

    override fun getUser(context: Context) {}
    override fun refreshUser(context: Context) {}
    override fun editUserData(
        context: Context,
        name: String?,
        surname: String?,
        email: String?,
        phone: String?,
        onSuccess: () -> Unit
    ) {
    }
}

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