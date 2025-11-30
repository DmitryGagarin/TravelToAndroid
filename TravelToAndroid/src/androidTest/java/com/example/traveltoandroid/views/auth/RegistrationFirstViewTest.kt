package com.example.traveltoandroid.views.auth

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import com.example.traveltoandroid.models.AuthUser
import com.example.traveltoandroid.viewModels.user.interfaces.ISignUpViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private class FakeSignUpViewModel() : ISignUpViewModel {
    override val user = MutableStateFlow<AuthUser?>(null)
    override val isLoading = MutableStateFlow(false)
    override val error = MutableStateFlow<String?>(null)
    override val verificationIsLoading = MutableStateFlow(false)
    override val verificationError = MutableStateFlow<String?>(null)

    var buttonIsClicked = false

    override fun signUpUserFirst(
        email: String,
        password: String,
        privacyPoliceAgreed: Boolean,
        userAgreement: Boolean,
        mailingAgreement: Boolean,
        context: Context,
        onSuccess: () -> Unit
    ) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            buttonIsClicked = true
        }
    }

    override fun signUpUserSecond(
        name: String,
        surname: String,
        context: Context,
        onSuccess: () -> Unit
    ) {}
}

class RegistrationFirstViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: FakeSignUpViewModel

    @Before
    fun setUp() {
        viewModel = FakeSignUpViewModel()
    }

    private fun setComposeContent() {
        composeTestRule.setContent {
            RegistrationFirstView(
                navController = rememberNavController(),
                viewModel = viewModel
            )
        }
        composeTestRule
            .onNodeWithTag("email_input")
            .performTextClearance()
        composeTestRule
            .onNodeWithTag("password_input")
            .performTextClearance()
    }

    private fun setEmail() {
        composeTestRule
            .onNodeWithTag("email_input")
            .performTextInput("email")
    }

    private fun setPassword() {
        composeTestRule
            .onNodeWithTag("password_input")
            .performTextInput("password")
    }

    private fun setPrivacyPoliceAgreed() {
        composeTestRule
            .onNodeWithTag("privacy_police_agreed")
            .performClick()
    }

    private fun setUserAgreement() {
        composeTestRule
            .onNodeWithTag("user_agreement")
            .performClick()
    }

    private fun setMailingAgreement() {
        composeTestRule
            .onNodeWithTag("mailing_agreement")
            .performClick()
    }

    @Test
    fun whenEmpty_shouldFail() {
        setComposeContent()
        viewModel.error.value = "Not all necessary fields completed"
        composeTestRule.onNodeWithTag("registration_button").performClick()
        assert(!viewModel.buttonIsClicked)
        composeTestRule.onNodeWithTag("error_occurred").assertExists()
    }

    @Test
    fun whenOnlyPass_shouldFail() {
        setComposeContent()
        setPassword()
        viewModel.error.value = "Not all necessary fields completed"
        composeTestRule.onNodeWithTag("registration_button").performClick()
        assert(!viewModel.buttonIsClicked)
        composeTestRule.onNodeWithTag("error_occurred").assertExists()
    }

    @Test
    fun whenOnlyLogin_shouldFail() {
        setComposeContent()
        setEmail()
        viewModel.error.value = "Not all necessary fields completed"
        composeTestRule.onNodeWithTag("registration_button").performClick()
        assert(!viewModel.buttonIsClicked)
        composeTestRule.onNodeWithTag("error_occurred").assertExists()
    }

    @Test
    fun whenNotAgree_shouldFail() {
        setComposeContent()
        setEmail()
        setPassword()
        viewModel.error.value = "Privacy policy is obligatory"
        viewModel.error.value = "User agreement is obligatory"
        assert(!viewModel.buttonIsClicked)
        viewModel.error.value = "Not all necessary fields completed"
        composeTestRule.onNodeWithTag("error_occurred").assertExists()
    }

    @Test
    fun whenNecessaryAgree_shouldSuccess() {
        setComposeContent()
        setEmail()
        setPassword()
        setPrivacyPoliceAgreed()
        setUserAgreement()
        setMailingAgreement()
        composeTestRule.onNodeWithTag("registration_button").performClick()
        assert(viewModel.buttonIsClicked)
        composeTestRule.onNodeWithTag("error_occurred").assertDoesNotExist()
    }

    @Test
    fun whenOnlyPrivacyPoliceAgreed_shouldFail() {
        setComposeContent()
        setEmail()
        setPassword()
        setPrivacyPoliceAgreed()
        composeTestRule.onNodeWithTag("registration_button").performClick()
        viewModel.error.value = "Privacy policy is obligatory"
        viewModel.error.value = "User agreement is obligatory"
        composeTestRule.onNodeWithTag("error_occurred").assertExists()
    }
}