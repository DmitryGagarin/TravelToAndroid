package com.example.traveltoandroid.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.traveltoandroid.viewModels.attraction.interfaces.ILikeViewModel

@Composable
fun LikeButton(
    attractionName: String,
    viewModel: ILikeViewModel,
    modifier: Modifier = Modifier,
    onAuthRequired: () -> Unit = {}
) {
    val context = LocalContext.current
    var isHovering by remember { mutableStateOf(false) }

    // Use the synchronous version for immediate check
    val isLiked = viewModel.isAttractionLiked(attractionName)

    // Load likes when component is first composed
    LaunchedEffect(Unit) {
        viewModel.loadUserLikes(context)
    }

    Icon(
        imageVector = if (isLiked) {
            if (isHovering) {
                // Broken heart when hovering over liked attraction (for unlike hint)
                Icons.Default.FavoriteBorder
            } else {
                // Solid heart when liked
                Icons.Default.Favorite
            }
        } else {
            // Outline heart when not liked
            Icons.Default.FavoriteBorder
        },
        contentDescription = if (isLiked) "Unlike this attraction" else "Like this attraction",
        tint = if (isLiked) Color.Red else Color.Black,
        modifier = modifier
            .size(36.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    if (isLiked) {
                        viewModel.dislikeAttraction(context, attractionName)
                    } else {
                        viewModel.likeAttraction(context, attractionName)
                    }
                }
            )
            .testTag("like_button_$attractionName")
    )
}