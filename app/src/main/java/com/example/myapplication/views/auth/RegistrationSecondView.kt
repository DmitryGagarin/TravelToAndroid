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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.viewModels.user.SignUpViewModel

@Composable
fun RegistrationSecondView(
    navController: NavController
) {
    val viewModel: SignUpViewModel = viewModel()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = "",
            onValueChange = { name = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxSize()
        )
        TextField(
            value = "",
            onValueChange = { surname = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxSize()
        )

        Button(
            onClick = {
                viewModel.signUpUserSecond(
                    name,
                    surname
                ) {
                    navController.navigate("login")
                }
            }
        ) {
            Text(text = "Complete registration")
        }

        Button(
            onClick = { navController.navigate("login") }
        ) {
            Text(text = "Skip this step")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { navController.navigate("login") }
        ) {
            Text(text = "Already have an account")
        }
    }
}