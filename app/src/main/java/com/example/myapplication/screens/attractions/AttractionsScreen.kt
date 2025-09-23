package com.example.myapplication.screens.attractions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R

@Composable
fun AttractionsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Adds scrolling if nee
    ) {
        AttractionCard(
            title = stringResource(R.string.title),
            description = stringResource(R.string.description),
            type = stringResource(R.string.type),
            hasMoreButton = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            navController = navController
        )
        Spacer(modifier = Modifier.height(10.dp))
        AttractionCard(
            title = stringResource(R.string.title),
            description = stringResource(R.string.description),
            type = stringResource(R.string.type),
            hasMoreButton = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            navController = navController
        )
        Spacer(modifier = Modifier.height(10.dp))
        AttractionCard(
            title = stringResource(R.string.title),
            description = stringResource(R.string.description),
            type = stringResource(R.string.type),
            hasMoreButton = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            navController = navController
        )
    }
}
