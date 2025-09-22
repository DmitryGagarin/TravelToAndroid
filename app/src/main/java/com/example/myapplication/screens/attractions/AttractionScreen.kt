package com.example.myapplication.screens.attractions

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AttractionScreen(
    navController: NavController,
    onBackClick: () -> Unit
) {
    Column {
        Text(text = "Attraction Screen")
    }
}