package com.example.traveltoandroid.views.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.traveltoandroid.R
import com.example.traveltoandroid.viewModels.user.ISignInViewModel
import com.example.traveltoandroid.viewModels.user.SignInViewModel

@Composable
fun LoginView(
    navController: NavController,
    viewModel: ISignInViewModel = viewModel<SignInViewModel>()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val context = LocalContext.current

    var login by remember { mutableStateOf("admin@travel.com") }
    var password by remember { mutableStateOf("password") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = login,
            onValueChange = { login = it },
            label = { Text(stringResource(R.string.login)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("login_value")
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("password_value")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.signInUser(
                    login = login,
                    password = password,
                    context = context,
                    onSuccess = {
                        navController.navigate("attractions")
                    }
                )
            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("sign_in_button")
        ) {
            Text(text = if (isLoading) stringResource(R.string.logging_in) else stringResource(R.string.login))
        }

        if (error != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = error ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("registration") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.registration))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { navController.navigate("reset_password") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.reset_password))
        }
    }
}
