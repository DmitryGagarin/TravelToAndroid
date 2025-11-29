package com.example.traveltoandroid.views.attraction

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.example.traveltoandroid.models.AttractionModel
import com.example.traveltoandroid.models.DiscussionModel
import com.example.traveltoandroid.viewModels.attraction.IAttractionViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FakeAttractionViewModel : IAttractionViewModel {

    override val attraction = MutableStateFlow<AttractionModel?>(null)
    override val isLoadingAttraction = MutableStateFlow(false)
    override val errorAttraction = MutableStateFlow<String?>(null)

    override val discussions = MutableStateFlow<List<DiscussionModel>>(emptyList())
    override val isLoadingDiscussions = MutableStateFlow(false)
    override val errorDiscussions = MutableStateFlow<String?>(null)

    override val isLoadingLike = MutableStateFlow(false)
    override val errorLike = MutableStateFlow<String?>(null)

    var attractionLoaded = false
    var refreshed = false
    var liked = false

    override fun loadAttractionData(context: Context) {
        attractionLoaded = true
    }

    override fun refreshAttractionAndDiscussions(context: Context) {
        refreshed = true
    }

    override fun likeAttraction(context: Context) {
        liked = true
    }
}


class AttractionViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: FakeAttractionViewModel

    @Before
    fun setUp() {
        viewModel = FakeAttractionViewModel()
    }

    private fun setComposeContent() {
        composeTestRule.setContent {
            AttractionView(
                navController = rememberNavController(),
                onBackClick = { },
                attractionName = "Test",
                viewModel = viewModel
            )
        }
    }

    @Test
    fun whenLoading_shouldShowLoadingIndicator() {
        viewModel.isLoadingAttraction.value = true
        setComposeContent()
        composeTestRule.onNodeWithTag("loading_indicator").assertExists()
        composeTestRule.onNodeWithTag("attraction_filled").assertDoesNotExist()
        composeTestRule.onNodeWithTag("attraction_data").assertDoesNotExist()
        composeTestRule.onNodeWithTag("discussion_creation_is_visible").assertDoesNotExist()
    }

    @Test
    fun whenLoaded_shouldShowAttractionData() {
        viewModel.attraction.value = AttractionModel(
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
        )

        setComposeContent()
        composeTestRule.onNodeWithTag("loading_indicator").assertDoesNotExist()
        composeTestRule.onNodeWithTag("attraction_filled").assertExists()
        composeTestRule.onNodeWithTag("attraction_data").assertExists()
        composeTestRule.onNodeWithTag("discussion_creation_is_visible").assertExists()
    }

}
