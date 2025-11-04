package com.example.traveltoandroid.views.attraction.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.traveltoandroid.R
import com.example.traveltoandroid.views.attraction.features.models.ParkFacilityModel
import com.example.traveltoandroid.views.attraction.features.utils.ImagePicker

@Composable
fun ParkFacilityCard(
    facility: ParkFacilityModel,
    onUpdate: (ParkFacilityModel) -> Unit
) {
    var isRoundTheClock by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = facility.name ?: "",
                onValueChange = { onUpdate(facility.copy(name = it)) },
                label = { Text(stringResource(R.string.park_facility_name)) },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = facility.description ?: "",
                onValueChange = { onUpdate(facility.copy(description = it)) },
                label = { Text(stringResource(R.string.park_facility_description)) },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = isRoundTheClock,
                    onCheckedChange = {
                        isRoundTheClock = !isRoundTheClock
                        if (isRoundTheClock) {
                            onUpdate(facility.copy(openTime = null, closeTime = null))
                        }
                    }
                )
                Text(text = stringResource(R.string.park_facility_works_around_the_clock))
            }

            if (!isRoundTheClock) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = facility.openTime ?: "",
                        onValueChange = { onUpdate(facility.copy(openTime = it)) },
                        label = { Text(stringResource(R.string.open_time)) },
                        modifier = Modifier.weight(1f)
                    )

                    TextField(
                        value = facility.closeTime ?: "",
                        onValueChange = { onUpdate(facility.copy(closeTime = it)) },
                        label = { Text(stringResource(R.string.close_time)) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Image picker that updates the facility with selected URIs
            ImagePicker { uris ->
                onUpdate(facility.copy(imageUris = uris))
            }

            // Show selected image count
            if (facility.imageUris.isNotEmpty()) {
                Text(stringResource(R.string.selected_images, facility.imageUris.size))
            }
        }
    }
}