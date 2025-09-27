package com.example.myapplication.views.auth

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
fun RegistrationView(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = "", onValueChange = {}, label = { Text("First name") })
        TextField(value = "", onValueChange = {}, label = { Text("Second name") })
        TextField(value = "", onValueChange = {}, label = { Text("Email") })
        TextField(value = "", onValueChange = {}, label = { Text("Telephone") })
        TextField(value = "", onValueChange = {}, label = { Text("Password") })
        Button(
            onClick = { navController.navigate("login") }
        ) { Text(text = "Registration") }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { navController.navigate("login") }
        ) { Text(text = "Already have an account") }
    }
}