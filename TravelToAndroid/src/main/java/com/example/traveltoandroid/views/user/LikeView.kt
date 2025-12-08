package com.example.traveltoandroid.views.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.traveltoandroid.attractions.AttractionPreviewCard
import com.example.traveltoandroid.models.AttractionModel

@Composable
fun LikeView(navController: NavController) {

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AttractionPreviewCard(
            attraction = attraction,
            hasMoreButton = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            navController = navController
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}
