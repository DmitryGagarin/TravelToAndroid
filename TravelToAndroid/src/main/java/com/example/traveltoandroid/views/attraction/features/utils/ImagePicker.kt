package com.example.traveltoandroid.views.attraction.features.utils

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.traveltoandroid.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

@Composable
fun ImagePicker(
    onImagesSelected: (List<Uri>) -> Unit
) {
    // State for selected images
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    // Launcher for picking multiple images
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri> ->
            imageUris = uris
            onImagesSelected(uris)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text(stringResource(R.string.pick_images))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display all selected images
        imageUris.forEach { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = stringResource(R.string.selected_image),
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

/**
 * Converts a list of Uri to a list of MultipartBody.Part suitable for Retrofit upload
 */
fun urisToMultipartParts(
    context: Context,
    uris: List<Uri>,
    partName: String = "images"
): List<MultipartBody.Part> {
    return uris.map { uri ->
        val bytes = context.contentResolver.openInputStream(uri)?.readBytes() ?: byteArrayOf()
        val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), bytes)
        val fileName = "upload_${System.currentTimeMillis()}.jpg"
        MultipartBody.Part.createFormData(partName, fileName, requestBody)
    }
}
