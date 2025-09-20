package com.example.myapplication.screens

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
import com.example.myapplication.AttractionCard
import com.example.myapplication.R

@Composable
fun LikeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Adds scrolling if nee
    ) {
        AttractionCard(
            title = stringResource(R.string.title),
            description = stringResource(R.string.description),
            type = stringResource(R.string.type),
            modifier = Modifier.align(Alignment.CenterHorizontally) // This centers the card
        )
        Spacer(modifier = Modifier.height(10.dp))
        AttractionCard(
            title = stringResource(R.string.title),
            type = stringResource(R.string.type),
            description = stringResource(R.string.description),
            modifier = Modifier.align(Alignment.CenterHorizontally) // This centers the card
        )
        Spacer(modifier = Modifier.height(10.dp))
        AttractionCard(
            title = stringResource(R.string.title),
            type = stringResource(R.string.type),
            description = stringResource(R.string.description),
            modifier = Modifier.align(Alignment.CenterHorizontally) // This centers the card
        )
    }
}
