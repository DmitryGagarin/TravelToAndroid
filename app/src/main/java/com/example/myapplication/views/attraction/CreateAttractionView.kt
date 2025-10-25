package com.example.myapplication.views.attraction

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.utils.Chip
import com.example.myapplication.viewModels.attraction.CreateAttractionViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAttractionView(onBackClick: () -> Unit) {

    val context = LocalContext.current

    val viewModel: CreateAttractionViewModel = viewModel()

    val isLoadingAttraction by viewModel.isLoadingAttraction.collectAsState()
    val errorAttraction by viewModel.errorAttraction.collectAsState()

    var ownerTelegram by remember { mutableStateOf("") }
    var attractionName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var household by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    var attractionType by remember { mutableStateOf("") }
    var isRoundTheClock by remember { mutableStateOf(false) }
    var openTime by remember { mutableStateOf("") }
    var closeTime by remember { mutableStateOf("") }

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
        Box(
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
                            onClick = { viewModel.refreshSavedAttraction(context) }
                        ) {
                            Text("Try Again")
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = ownerTelegram,
                        onValueChange = { ownerTelegram = it },
                        label = { Text("Owner telegram") },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = attractionName,
                        onValueChange = { attractionName = it },
                        label = { Text("Attraction name") },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Attraction description") },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("Attraction address - city") },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = street,
                        onValueChange = { street = it },
                        label = { Text("Attraction address - street") },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = household,
                        onValueChange = { household = it },
                        label = { Text("Attraction address - household") },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Attraction phone") },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = website,
                        onValueChange = { website = it },
                        label = { Text("Attraction website") },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val attractionTypes = listOf("Museum", "Gallery", "Park", "Religious", "Cafe", "Restaurant", "Theater", "Hotel")

                    Row {
                        attractionTypes.forEach { type ->
                            Chip(
                                label = type,
                                isSelected = attractionType == type,
                                onClick = {
                                    attractionType = type // Use the captured 'type' from forEach
                                },
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Checkbox(
                        checked = isRoundTheClock,
                        onCheckedChange = { isRoundTheClock = !isRoundTheClock },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = openTime,
                        onValueChange = { openTime = it },
                        label = { Text("Open time") },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = closeTime,
                        onValueChange = { closeTime = it },
                        label = { Text("Close time") },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.createAttraction(
                                context,
                                ownerTelegram,
                                attractionName,
                                description,
                                "$city, $street, $household",
                                phone,
                                website,
                                attractionType,
                                isRoundTheClock,
                                openTime,
                                closeTime
                            )
                        }
                    ) {
                        Text(text = "Save attraction")
                    }
                }
            }
        }
    }
}