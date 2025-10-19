package com.example.myapplication.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale

@Composable
fun Base64Image(
    base64String: String?,
    contentDescription: String? = null,
    modifier: Modifier
) {
    val imageBitmap = remember(base64String) {
        base64String?.let { decodeBase64ToBitmap(it) }?.asImageBitmap()
    }

    imageBitmap?.let { bitmap ->
        Image(
            bitmap = bitmap,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
}

private fun decodeBase64ToBitmap(base64String: String): Bitmap? {
    return try {
        val pureBase64 = base64String.substringAfterLast(",")
        val decodedBytes = android.util.Base64.decode(pureBase64, android.util.Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        null
    }
}