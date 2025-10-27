package com.example.myapplication.views.attraction.features

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.views.attraction.features.models.ParkFacility


@Composable
fun FacilityCard(
    facility: ParkFacility,
    onUpdate: (ParkFacility) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = facility.name,
                onValueChange = { onUpdate(facility.copy(name = it)) },
                label = { Text("Facility name") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = facility.description,
                onValueChange = { onUpdate(facility.copy(description = it)) },
                label = { Text("Facility description") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = facility.isRoundTheClock,
                    onCheckedChange = { onUpdate(facility.copy(isRoundTheClock = it)) }
                )
                Text(text = "Works around the clock?")
            }

            if (!facility.isRoundTheClock) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = facility.openTime,
                        onValueChange = { onUpdate(facility.copy(openTime = it)) },
                        label = { Text("Open time") },
                        modifier = Modifier.weight(1f)
                    )
                    TextField(
                        value = facility.closeTime,
                        onValueChange = { onUpdate(facility.copy(closeTime = it)) },
                        label = { Text("Close time") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
