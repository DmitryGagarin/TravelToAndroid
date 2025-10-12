package com.example.myapplication.views.attraction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.attractions.AttractionCard
import com.example.myapplication.attractions.AttractionDiscussionCard
import com.example.myapplication.viewModels.attraction.AttractionViewModel
import com.example.myapplication.viewModels.attraction.AttractionViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionView(
    navController: NavController,
    onBackClick: () -> Unit,
    attractionName: String
) {
    val viewModel: AttractionViewModel = viewModel(
        factory = AttractionViewModelFactory(attractionName)
    )

    val attraction by viewModel.attraction.collectAsState()
    val isLoadingAttraction by viewModel.isLoadingAttraction.collectAsState()
    val errorAttraction by viewModel.errorAttraction.collectAsState()

    val discussions by viewModel.discussions.collectAsState()
    val isLoadingDiscussions by viewModel.isLoadingDiscussions.collectAsState()
    val errorDiscussions by viewModel.errorDiscussions.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadAttractionData(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Back") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isLoadingAttraction) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (errorAttraction != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error loading attraction",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorAttraction ?: "Unknown error",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.refreshAttractionAndDiscussions(context) }
                        ) {
                            Text("Try Again")
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        attraction?.let {
                            AttractionCard(
                                attraction = it,
                                hasMoreButton = false,
                                modifier = Modifier.fillMaxWidth(),
                                navController = navController
                            )
                        }
                    }

                    item {
                        Button(onClick = { TODO() }) {
                            Text(text = "Leave discussion")
                        }
                    }

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
                                        text = "Error loading discussions",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = errorDiscussions ?: "Unknown error",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = { viewModel.refreshAttractionAndDiscussions(context) }
                                    ) {
                                        Text("Try Again")
                                    }
                                }
                            }
                        }

                        else -> {
                            items(
                                items = discussions,
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
            }
        }
    }
}