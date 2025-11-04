package com.example.traveltoandroid.attractions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.traveltoandroid.models.AttractionModel

@Composable
fun AttractionDataCard(
    attraction: AttractionModel,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .size(width = 85.dp, height = 180.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            attraction.address?.let {
                Text(
                    text = "address: $it",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                )
            }
            attraction.phone?.let {
                Text(
                    text =  "phone: $it",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                )
            }
            attraction.website?.let {
                Text(
                    text = "website: $it",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                )
            }
            if (attraction.isRoundTheClock == true) {
                Text(
                    text = "Is round the clock: ✅",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                )
            } else {
                attraction.openTime?.let {
                    Text(
                        text = "open time: $it",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                    )
                }
                attraction.closeTime?.let {
                    Text(
                        text = "close time: $it",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}