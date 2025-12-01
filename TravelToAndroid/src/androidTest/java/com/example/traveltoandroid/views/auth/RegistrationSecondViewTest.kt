package com.example.traveltoandroid.views.auth

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegistrationSecondViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: FakeSignUpViewModel

    @Before
    fun setUp() {
        viewModel = FakeSignUpViewModel()
    }

    private fun setComposeContent() {
        composeTestRule.setContent {
            RegistrationSecondView(
                navController = rememberNavController(),
                viewModel = viewModel
            )
        }
        composeTestRule
            .onNodeWithTag("name_input")
            .performTextClearance()
        composeTestRule
            .onNodeWithTag("surname_input")
            .performTextClearance()
    }

    private fun setName() {
        composeTestRule
            .onNodeWithTag("name_input")
            .performTextInput("name_input")
    }

    private fun setSurname() {
        composeTestRule
            .onNodeWithTag("surname_input")
            .performTextInput("name_input")
    }

    @Test
    fun whenBothFilled_shouldSuccess() {
        setComposeContent()
        setName()
        setSurname()
        composeTestRule.onNodeWithTag("end_registration_button").performClick()
        composeTestRule.onNodeWithTag("error_text").assertDoesNotExist()
    }

    @Test
    fun whenBothEmpty_shouldSuccess() {
        setComposeContent()
        composeTestRule.onNodeWithTag("end_registration_button").performClick()
        composeTestRule.onNodeWithTag("error_text").assertDoesNotExist()
    }
}