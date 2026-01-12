package com.example.traveltoandroid.views.attraction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.traveltoandroid.R
import com.example.traveltoandroid.attractions.AttractionDataCard
import com.example.traveltoandroid.attractions.AttractionDiscussionCard
import com.example.traveltoandroid.attractions.AttractionPreviewCard
import com.example.traveltoandroid.viewModels.attraction.AttractionsViewModel
import com.example.traveltoandroid.viewModels.attraction.AttractionViewModelFactory
import com.example.traveltoandroid.viewModels.attraction.interfaces.IAttractionViewModel
import com.example.traveltoandroid.viewModels.attraction.interfaces.ILikeViewModel
import com.example.traveltoandroid.views.discussion.CreateDiscussionView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionView(
    navController: NavController,
    onBackClick: () -> Unit,
    attractionName: String,
    attractionViewModel: IAttractionViewModel = viewModel(
        factory = AttractionViewModelFactory(attractionName)
    ),
    likeViewModel: ILikeViewModel = viewModel<AttractionsViewModel>()
) {
    val attraction by attractionViewModel.attraction.collectAsState()
    val isLoadingAttraction by attractionViewModel.isLoadingAttraction.collectAsState()
    val errorAttraction by attractionViewModel.errorAttraction.collectAsState()

    val discussions by attractionViewModel.discussions.collectAsState(emptyList()) // Provide default value
    val isLoadingDiscussions by attractionViewModel.isLoadingDiscussions.collectAsState()
    val errorDiscussions by attractionViewModel.errorDiscussions.collectAsState()

    var discussionCreationIsVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        attractionViewModel.loadAttractionData(context)
        likeViewModel.loadUserLikes(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.back)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("attraction_screen")
        ) {
            if (isLoadingAttraction) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("loading_indicator"),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (errorAttraction != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("error_state"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.error_loading_attraction),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorAttraction ?: stringResource(R.string.unknown_error),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { attractionViewModel.refreshAttractionAndDiscussions(context) }
                        ) {
                            Text(stringResource(R.string.try_again))
                        }
                    }
                }
            } else {
                // Use a single Column with proper weighting
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Attraction card and button (fixed height)
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            attraction?.let {
                                AttractionPreviewCard(
                                    attraction = it,
                                    hasMoreButton = false,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("attraction_filled"),
                                    navController = navController,
                                    viewModel = likeViewModel
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }
                        item {
                            attraction?.let {
                                AttractionDataCard(
                                    attraction = it,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .testTag("attraction_data"),
                                )
                            }
                        }

                        item {
                            Button(
                                onClick = {
                                    discussionCreationIsVisible = !discussionCreationIsVisible
                                },
                                modifier = Modifier.testTag("discussion_creation_is_visible")
                            ) {
                                Text(
                                    text = if (discussionCreationIsVisible) {
                                        stringResource(R.string.cancel)
                                    } else {
                                        stringResource(R.string.send_discussion)
                                    }
                                )
                            }
                        }

                        // Show discussions below
                        when {
                            isLoadingDiscussions -> {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 32.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }

                            errorDiscussions != null -> {
                                item {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = stringResource(R.string.error_loading_discussions),
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = errorDiscussions
                                                ?: stringResource(R.string.unknown_error),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Button(
                                            onClick = {
                                                attractionViewModel.refreshAttractionAndDiscussions(
                                                    context
                                                )
                                            }
                                        ) {
                                            Text(stringResource(R.string.try_again))
                                        }
                                    }
                                }
                            }
                            else -> {
                                val safeDiscussions = discussions ?: emptyList()
                                items(
                                    items = safeDiscussions,
                                    key = { discussion -> discussion.title ?: "" }
                                ) { discussion ->
                                    AttractionDiscussionCard(
                                        discussion = discussion,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }

                    // Show CreateDiscussionView as an overlay when visible
                    if (discussionCreationIsVisible) {
                        CreateDiscussionView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            navController = navController,
                            attractionName = attractionName,
                            onDismiss = { discussionCreationIsVisible = false }
                        )
                    }
                }
            }
        }
    }
}