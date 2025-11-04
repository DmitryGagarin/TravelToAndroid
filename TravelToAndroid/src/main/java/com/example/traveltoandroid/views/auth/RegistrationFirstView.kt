package com.example.traveltoandroid.views.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.traveltoandroid.R
import com.example.traveltoandroid.viewModels.user.SignUpViewModel

@Composable
fun RegistrationFirstView(
    navController: NavController
) {
    val viewModel: SignUpViewModel = viewModel()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val context = LocalContext.current

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
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email)) },
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Checkbox(
                checked = privacyPoliceAgreed,
                onCheckedChange = { privacyPoliceAgreed = !privacyPoliceAgreed },
            )
            Text(text = stringResource(R.string.are_you_agreed_with_out_privacy_policy))
        }

        Row {
            Checkbox(
                checked = userAgreement,
                onCheckedChange = { userAgreement = !userAgreement },
            )
            Text(text = stringResource(R.string.are_you_agreed_with_getting_advertisements_emails))
        }

        Row {
            Checkbox(
                checked = mailingAgreement,
                onCheckedChange = { mailingAgreement = !mailingAgreement },
            )
            Text(text = stringResource(R.string.are_you_agreed_with_out_user_agreement))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.signUpUserFirst(
                    email,
                    password,
                    privacyPoliceAgreed,
                    userAgreement,
                    mailingAgreement,
                    context
                ) {
                    navController.navigate("registration_second")
                }
            }
        ) {
            Text(
                text = if (isLoading) stringResource(R.string.registration) + "..." else stringResource(
                    R.string.registration
                )
            )
        }

        if (error != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = error ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { navController.navigate("login") }
        ) {
            Text(text = stringResource(R.string.already_have_an_account))
        }
    }
}