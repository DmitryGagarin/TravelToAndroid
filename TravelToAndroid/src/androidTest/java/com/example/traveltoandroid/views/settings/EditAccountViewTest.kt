package com.example.traveltoandroid.views.settings

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.traveltoandroid.models.UserModel
import com.example.traveltoandroid.views.utils.FakeUserViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EditAccountViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: FakeUserViewModel

    @Before
    fun setUp() {
        viewModel = FakeUserViewModel()
    }

    private fun setComposeContent() {
        composeTestRule.setContent {
            EditAccountView(
                navController = rememberNavController(),
                onBackClick = { },
                viewModel = viewModel
            )
        }
    }

    @Test
    fun userFetched_shouldSuccess() {
        setComposeContent()
        viewModel.user.value = UserModel(
            "name",
            "surname",
            "email",
            "phone",
            "13-04-2005",
            emptyList(),
            "token",
            false
        )
        composeTestRule.onNodeWithTag("main_column").assertExists()
        composeTestRule.onNodeWithTag("name").assertTextContains("name")
        composeTestRule.onNodeWithTag("surname").assertTextContains("surname")
        composeTestRule.onNodeWithTag("email").assertTextContains("email")
        composeTestRule.onNodeWithTag("phone").assertTextContains("phone")
        composeTestRule.onNodeWithTag("edit_account_button").performClick()
        assert(viewModel.buttonClicked)
    }
}