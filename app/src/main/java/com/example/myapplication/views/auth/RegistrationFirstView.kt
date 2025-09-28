package com.example.myapplication.views.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
fun RegistrationFirstView(
    navController: NavController
) {
    val viewModel: SignUpViewModel = viewModel()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var privacyPoliceAgreed by remember { mutableStateOf(false) }
    var userAgreement by remember { mutableStateOf(false) }
    var mailingAgreement by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = "",
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxSize()
        )
        TextField(
            value = "",
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxSize()
        )
        Checkbox(
            checked = false,
            onCheckedChange = { privacyPoliceAgreed = !privacyPoliceAgreed },
            modifier = Modifier.fillMaxSize(),
            enabled = false
        )
        Checkbox(
            checked = false,
            onCheckedChange = { userAgreement = !userAgreement },
            modifier = Modifier.fillMaxSize(),
            enabled = false
        )
        Checkbox(
            checked = false,
            onCheckedChange = { mailingAgreement = !mailingAgreement },
            modifier = Modifier.fillMaxSize(),
            enabled = false
        )

        Button(
            onClick = {
                viewModel.signUpUserFirst(
                    email,
                    password,
                    privacyPoliceAgreed,
                    userAgreement,
                    mailingAgreement
                ) {
                    navController.navigate("registration_second")
                }
            }
        ) {
            Text(text = "Registration")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { navController.navigate("login") }
        ) {
            Text(text = "Already have an account")
        }
    }
}