package com.example.myapplication.views.attraction.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.views.attraction.features.models.ParkFacilityModel

@Composable
fun ParkFeatureView() {
    val facilities = remember { mutableStateListOf(ParkFacilityModel()) } // start with one facility

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        facilities.forEachIndexed { index, facility ->
            FacilityCard(
                facility = facility,
                onUpdate = { updated ->
                    facilities[index] = updated
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(onClick = { facilities.add(ParkFacilityModel()) }) {
            Text(text = "Add Facility")
        }
    }
}
