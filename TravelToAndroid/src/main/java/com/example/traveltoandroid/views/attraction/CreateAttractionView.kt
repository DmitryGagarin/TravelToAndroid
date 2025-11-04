package com.example.traveltoandroid.views.attraction

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.traveltoandroid.R
import com.example.traveltoandroid.utils.Chip
import com.example.traveltoandroid.viewModels.attraction.AttractionCreateViewModel
import com.example.traveltoandroid.views.attraction.features.AttractionFeaturesView
import com.example.traveltoandroid.views.attraction.features.models.ParkFacilityModel
import com.example.traveltoandroid.views.attraction.features.utils.ImagePicker
import com.example.traveltoandroid.views.attraction.features.utils.urisToMultipartParts
import okhttp3.MultipartBody

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAttractionView(onBackClick: () -> Unit) {

    val context = LocalContext.current

    val viewModel: AttractionCreateViewModel = viewModel()

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
    var attractionType by remember { mutableStateOf<String?>(null) }
    var isRoundTheClock by remember { mutableStateOf(false) }
    var openTime by remember { mutableStateOf("") }
    var closeTime by remember { mutableStateOf("") }
    var attractionImages by remember { mutableStateOf<List<MultipartBody.Part>>(emptyList()) }

    val parkFacilities = remember { mutableStateListOf<ParkFacilityModel>() }

    val posters by remember { mutableStateOf(mutableListOf<MultipartBody.Part>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
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
                            text = stringResource(R.string.error_creating_attraction),
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
                            onClick = { viewModel.refreshSavedAttraction(context) }
                        ) {
                            Text(stringResource(R.string.try_again))
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = ownerTelegram,
                        onValueChange = { ownerTelegram = it },
                        label = { Text(stringResource(R.string.attraction_owner_telegram)) },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = attractionName,
                        onValueChange = { attractionName = it },
                        label = { Text(stringResource(R.string.attraction_name)) },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text(stringResource(R.string.attraction_description)) },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text(stringResource(R.string.attraction_address_city)) },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = street,
                        onValueChange = { street = it },
                        label = { Text(stringResource(R.string.attraction_address_street)) },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = household,
                        onValueChange = { household = it },
                        label = { Text(stringResource(R.string.attraction_address_household)) },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text(stringResource(R.string.attraction_phone)) },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = website,
                        onValueChange = { website = it },
                        label = { Text(stringResource(R.string.attraction_website)) },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val attractionTypes = listOf(
                        stringResource(R.string.museum),
                        stringResource(R.string.gallery),
                        stringResource(R.string.park),
                        stringResource(R.string.religious),
                        stringResource(R.string.cafe),
                        stringResource(R.string.restaurant),
                        stringResource(R.string.theater),
                        stringResource(R.string.hotel)
                    )

                    LazyRow(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(attractionTypes) { type ->
                            Chip(
                                label = type,
                                isSelected = attractionType == type,
                                onClick = { attractionType = type },
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isRoundTheClock,
                            onCheckedChange = { isRoundTheClock = !isRoundTheClock },
                        )
                        Text(text = stringResource(R.string.is_your_attraction_works_around_the_clock))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (!isRoundTheClock) {
                        TextField(
                            value = openTime,
                            onValueChange = { openTime = it },
                            label = { Text(stringResource(R.string.open_time)) },
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = closeTime,
                            onValueChange = { closeTime = it },
                            label = { Text(stringResource(R.string.close_time)) },
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    ImagePicker { uris ->
                        // convert URIs to MultipartBody.Part for upload
                        attractionImages = urisToMultipartParts(context, uris)
                    }

                    attractionType?.let { AttractionFeaturesView(it, context, parkFacilities) }

                    Button(
                        onClick = {
                            viewModel.createAttraction(
                                context = context,
                                ownerTelegram = ownerTelegram,
                                attractionName = attractionName,
                                description = description,
                                address = "$city, $street, $household",
                                phone = phone,
                                website = website,
                                attractionType = attractionType!!,
                                isRoundTheClock = isRoundTheClock,
                                openTime = openTime,
                                closeTime = closeTime,
                                attractionImages = attractionImages,
                                parkFacilities = parkFacilities, // This now contains facilities with image URIs
                                posters = posters
                            )
                        }
                    ) {
                        Text(text = stringResource(R.string.save_attraction))
                    }
                }
            }
        }
    }
}