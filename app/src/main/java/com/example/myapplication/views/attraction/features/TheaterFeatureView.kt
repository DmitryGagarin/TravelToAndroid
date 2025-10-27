package com.example.myapplication.views.attraction.features

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.views.attraction.features.utils.ImagePicker
import com.example.myapplication.views.attraction.features.utils.urisToMultipartParts

@Composable
fun TheaterFeatureView(context: Context) {
    var selectedUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    ImagePicker { uris ->
        selectedUris = uris
    }

    urisToMultipartParts(context, selectedUris, "images")

}