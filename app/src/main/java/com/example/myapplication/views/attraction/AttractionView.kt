package com.example.myapplication.views.attraction

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
import com.example.myapplication.attractions.AttractionCard
import com.example.myapplication.attractions.AttractionDiscussion
import com.example.myapplication.models.AttractionModel
import com.example.myapplication.models.DiscussionModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionView(
    navController: NavController,
    onBackClick: () -> Unit
) {
    val attraction = AttractionModel(
        "123",
        "123",
        "123",
        "123",
        emptyList(),
        emptyList(),
        "123",
        "123",
        "123",
        true,
        "123",
        "123",
        12.3,
        "123",
    )

    val discussion = DiscussionModel(
        "123",
        "123",
        "123",
        "123",
        12.33,
        "123",
        "123",
        emptyList(),
        emptyList(),
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AttractionCard(
                attraction = attraction,
                hasMoreButton = false,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                navController = navController
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = { TODO() }) {
                Text(text = "Leave discussion")
            }
            AttractionDiscussion(
                discussion = discussion,
                modifier = Modifier,
            )
        }
    }
}