package com.example.myapplication.attractions

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.models.AttractionModel
import com.example.myapplication.utils.Base64Image
import com.example.myapplication.viewModels.attraction.AttractionViewModel
import com.example.myapplication.viewModels.attraction.AttractionViewModelFactory

@Composable
fun PreviewAttractionCard(
    attraction: AttractionModel,
    hasMoreButton: Boolean,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AttractionViewModel = viewModel(
        factory = attraction.name?.let { AttractionViewModelFactory(it) }
    )
) {

    val context: Context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .size(width = 340.dp, height = 400.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            attraction.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
            }
            attraction.images?.firstOrNull()?.let { firstImage ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f) // or a fixed width like 300.dp
                        .aspectRatio(3f / 2f) // 3:2 aspect ratio (e.g. 300x200)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    Base64Image(
                        base64String = firstImage,
                        contentDescription = "Image of ${attraction.name}",
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }
            attraction.type?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
            }
            Text(
                text = attraction.rating.toString(),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
            if (hasMoreButton) {
                Button(
                    onClick = {
                        navController.navigate("attraction/${attraction.name}")
                    }
                ) {
                    Text(text = "More")
                }
            }

            IconButton(
                onClick = {viewModel.likeAttraction(context = context)}
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Like"
                )
            }
        }
    }
}