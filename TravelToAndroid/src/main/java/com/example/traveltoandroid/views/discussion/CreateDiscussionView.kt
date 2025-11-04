package com.example.traveltoandroid.views.discussion

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.traveltoandroid.R
import com.example.traveltoandroid.viewModels.discussion.DiscussionViewModel

@Composable
fun CreateDiscussionView(
    modifier: Modifier,
    navController: NavController,
    attractionName: String,
    onDismiss: () -> Unit = {}
) {
    var title by remember { mutableStateOf("") }
    var liked by remember { mutableStateOf("") }
    var disliked by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val rating by remember { mutableStateOf("") }

    val viewModel: DiscussionViewModel = viewModel()
    val context: Context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .size(width = 340.dp, height = 200.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(stringResource(R.string.discussion_title)) },
        )
        TextField(
            value = liked,
            onValueChange = { liked = it },
            label = { Text(stringResource(R.string.discussion_liked)) },
        )
        TextField(
            value = disliked,
            onValueChange = { disliked = it },
            label = { Text(stringResource(R.string.discussion_disliked)) },
        )
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text(stringResource(R.string.discussion_content)) },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onDismiss
            ) {
                Text(text = stringResource(R.string.cancel))
            }

            Button(
                onClick = {
                    viewModel.sendDiscussion(
                        attractionName = attractionName,
                        context = context,
                        title = title,
                        liked = liked,
                        disliked = disliked,
                        content = content,
                        rating = rating,
                        onSuccess = {
                            onDismiss() // Dismiss after success
                            navController.navigate("attractions")
                        }
                    )
                }
            ) {
                Text(text = stringResource(R.string.send_discussion))
            }
        }
    }
}