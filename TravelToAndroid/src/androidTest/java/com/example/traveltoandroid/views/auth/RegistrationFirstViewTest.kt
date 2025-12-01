package com.example.traveltoandroid.views.auth

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import com.example.traveltoandroid.views.utils.FakeSignUpViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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