package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon on the screen
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            tint = Color(0xFF0F9D58)
        )
        Text(
            text = "NAME: value", color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.Gray, RectangleShape)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "SURNAME: value", color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.Gray, RectangleShape)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "PHONE: value", color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.Gray, RectangleShape)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "EMAIL: value", color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.Gray, RectangleShape)
        )
    }
}
