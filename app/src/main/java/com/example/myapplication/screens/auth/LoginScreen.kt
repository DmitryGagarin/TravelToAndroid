package com.example.myapplication.screens.auth

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = "", onValueChange = {}, label = { Text("Login") })
        TextField(value = "", onValueChange = {}, label = { Text("Password") })
        Button(
            onClick = { navController.navigate("attractions") }
        ) { Text(text = "Login") }
        Spacer(modifier =  Modifier.height(10.dp))
        Button(
            onClick = { navController.navigate("registration") }
        ) { Text(text = "Registration") }
        Spacer(modifier =  Modifier.height(10.dp))
        Button(
            onClick = { navController.navigate("reset_password") }
        ) { Text(text = "Reset password") }
    }
}