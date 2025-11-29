package com.example.traveltoandroid.views.auth

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextReplacement
import androidx.navigation.compose.rememberNavController
import com.example.traveltoandroid.models.AuthUser
import com.example.traveltoandroid.viewModels.user.ISignInViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private class FakeSignInViewModel() : ISignInViewModel {
    override val user = MutableStateFlow<AuthUser?>(null)
    override val isLoading = MutableStateFlow(false)
    override val error = MutableStateFlow<String?>(null)

    var buttonCalled = false

    override fun signInUser(
        login: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit
    ) {
        if (login.isNotEmpty() && password.isNotEmpty()) {
            buttonCalled = true
        }
    }
}

class LoginViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: FakeSignInViewModel

    @Before
    fun setUp() {
        viewModel = FakeSignInViewModel()
    }

    private fun setComposeContent() {
        composeTestRule.setContent {
            LoginView(
                navController = rememberNavController(),
                viewModel = viewModel
            )
        }
        composeTestRule
            .onNodeWithTag("login_value")
            .performTextClearance()

        composeTestRule
            .onNodeWithTag("password_value")
            .performTextClearance()
    }

    private fun setLogin() {
        composeTestRule
            .onNodeWithTag("login_value")
            .performTextReplacement("login_value")
    }

    private fun setPassword() {
        composeTestRule
            .onNodeWithTag("password_value")
            .performTextReplacement("password_value")
    }

    private fun setBoth() {
        setLogin()
        setPassword()
    }

    @Test
    fun whenBoth_shouldSuccessfullyLogin() {
        setComposeContent()
        setBoth()
        composeTestRule.onNodeWithTag("login_value").assertExists()
        composeTestRule.onNodeWithTag("password_value").assertExists()
        composeTestRule.onNodeWithTag("sign_in_button").performClick()
        assert(viewModel.buttonCalled)
    }

    @Test
    fun whenOnlyLogin_shouldFail() {
        setComposeContent()
        setPassword()
        composeTestRule.onNodeWithTag("login_value").assertExists()
        composeTestRule.onNodeWithTag("password_value").assertExists()
        composeTestRule.onNodeWithTag("sign_in_button").performClick()
        assert(!viewModel.buttonCalled)
    }

    @Test
    fun whenOnlyPassword_shouldFail() {
        setComposeContent()
        setLogin()
        composeTestRule.onNodeWithTag("login_value").assertExists()
        composeTestRule.onNodeWithTag("password_value").assertExists()
        composeTestRule.onNodeWithTag("sign_in_button").performClick()
        assert(!viewModel.buttonCalled)
    }

    @Test
    fun whenTotallyEmpty_shouldFail() {
        setComposeContent()
        composeTestRule.onNodeWithTag("login_value").assertExists()
        composeTestRule.onNodeWithTag("password_value").assertExists()
        composeTestRule.onNodeWithTag("sign_in_button").performClick()
        assert(!viewModel.buttonCalled)
    }
}