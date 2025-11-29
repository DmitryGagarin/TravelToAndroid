package com.example.traveltoandroid.views.attraction

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.traveltoandroid.models.AttractionModel
import com.example.traveltoandroid.viewModels.attraction.interfaces.IAttractionsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private class FakeAttractionsViewModel : IAttractionsViewModel {
    override val attractions = MutableStateFlow<List<AttractionModel>>(emptyList())
    override val isLoading = MutableStateFlow(false)
    override val error = MutableStateFlow<String?>(null)

    var refreshCalled = false
    var loadCalled = false

    override fun refreshAttractions() {
        refreshCalled = true
    }

    override fun loadPublishedAttractions() {
        loadCalled = true
    }
}

class AttractionsViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: FakeAttractionsViewModel

    @Before
    fun setUp() {
        viewModel = FakeAttractionsViewModel()
    }

    private fun setComposeContent() {
        composeTestRule.setContent {
            AttractionsView(
                navController = rememberNavController(),
                viewModel = viewModel
            )
        }
    }

    @Test
    fun whenLoading_shouldShowLoadingIndicator() {
        viewModel.isLoading.value = true
        setComposeContent()

        composeTestRule.onNodeWithTag("loading_indicator").assertExists()
        composeTestRule.onNodeWithTag("attractions_list").assertDoesNotExist()
        composeTestRule.onNodeWithTag("error_state").assertDoesNotExist()
        composeTestRule.onNodeWithTag("empty_state").assertDoesNotExist()
    }

    @Test
    fun whenError_shouldShowErrorState() {
        viewModel.error.value = "Network error"

        setComposeContent()

        composeTestRule.onNodeWithTag("error_state").assertExists()
        composeTestRule.onNodeWithText("Error loading attractions").assertExists()
        composeTestRule.onNodeWithText("Network error").assertExists()
        composeTestRule.onNodeWithTag("retry_button").assertExists()
    }

    @Test
    fun whenEmptyAttractions_shouldShowEmptyState() {
        viewModel.isLoading.value = false
        viewModel.error.value = null
        viewModel.attractions.value = emptyList()

        setComposeContent()

        composeTestRule.onNodeWithTag("empty_state").assertExists()
    }

    @Test
    fun whenAttractionsLoaded_shouldShowAttractionsList() {
        viewModel.attractions.value = listOf(
            AttractionModel(
                "",
                "A",
                "",
                "",
                emptyList(),
                emptyList(),
                "",
                "",
                "",
                false,
                "",
                "",
                0.0,
                ""
            ),
            AttractionModel(
                "",
                "B",
                "",
                "",
                emptyList(),
                emptyList(),
                "",
                "",
                "",
                false,
                "",
                "",
                0.0,
                ""
            )
        )

        setComposeContent()

        composeTestRule.onNodeWithTag("attractions_list").assertExists()
    }

    @Test
    fun whenRetryButtonClicked_shouldCallRefreshAttractions() {
        viewModel.error.value = "Network error"
        setComposeContent()

        composeTestRule.onNodeWithTag("retry_button").performClick()

        assert(viewModel.refreshCalled)
    }
}
