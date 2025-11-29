package com.example.traveltoandroid.views.attraction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.traveltoandroid.R
import com.example.traveltoandroid.attractions.PreviewAttractionCard
import com.example.traveltoandroid.viewModels.attraction.AttractionsViewModel
import com.example.traveltoandroid.viewModels.attraction.IAttractionsViewModel

@Composable
fun AttractionsView(
    navController: NavController,
    viewModel: IAttractionsViewModel = viewModel<AttractionsViewModel>()
) {
    val attractions by viewModel.attractions.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPublishedAttractions()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("attractions_screen")
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("loading_indicator"),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
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
                        text = stringResource(R.string.error_loading_attractions),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = error ?: stringResource(R.string.unknown_error),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.refreshAttractions() },
                        modifier = Modifier.testTag("retry_button")
                    ) {
                        Text(stringResource(R.string.try_again))
                    }
                }
            }
        } else if (attractions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("empty_state"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_attractions_found),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("attractions_list"),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = attractions,
                    key = { attraction -> attraction.name!! }
                ) { attraction ->
                    PreviewAttractionCard(
                        attraction = attraction,
                        hasMoreButton = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("attraction_item_${attraction.name}"),
                        navController = navController
                    )
                }
            }
        }
    }
}