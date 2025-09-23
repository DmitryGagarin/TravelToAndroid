package com.example.myapplication.screens.attractions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.screens.attractions.models.FullAttractionModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionScreen(
    navController: NavController,
    onBackClick: () -> Unit
) {
    val attractionModel = FullAttractionModel(
        attractionName = "attractionName",
        attractionDescription = "attractionDescription",
        attractionType = "attractionType",
        attractionDiscussionTitle = "attractionDiscussionTitle",
        attractionDiscussionAuthor = "attractionDiscussionAuthor",
        attractionDiscussionLiked = "attractionDiscussionLiked",
        attractionDiscussionDisliked = "attractionDiscussionDisliked",
        attractionDiscussionContent = "attractionDiscussionContent",
        attractionDiscussionRating = 5
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AttractionCard(
                title = attractionModel.attractionName,
                description = attractionModel.attractionDescription,
                type = attractionModel.attractionType,
                hasMoreButton = false,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                navController = navController
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {TODO()}) {
                Text(text = "Leave discussion")
            }
            AttractionDiscussion(
                attractionModel.attractionDiscussionAuthor,
                attractionModel.attractionDiscussionTitle,
                attractionModel.attractionDiscussionLiked,
                attractionModel.attractionDiscussionDisliked,
                attractionModel.attractionDiscussionContent,
                attractionModel.attractionDiscussionRating,
                modifier = Modifier,
                navController = navController
            )
        }
    }
}