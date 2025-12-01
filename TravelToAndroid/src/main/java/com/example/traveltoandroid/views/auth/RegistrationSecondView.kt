package com.example.traveltoandroid.views.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.traveltoandroid.R
import com.example.traveltoandroid.viewModels.user.interfaces.ISignUpViewModel
import com.example.traveltoandroid.viewModels.user.SignUpViewModel

@Composable
fun RegistrationSecondView(
    navController: NavController,
    viewModel: ISignUpViewModel = viewModel<SignUpViewModel>()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val context = LocalContext.current

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
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.name)) },
            modifier = Modifier.testTag("name_input")
        )
        TextField(
            value = surname,
            onValueChange = { surname = it },
            label = { Text(stringResource(R.string.surname)) },
            modifier = Modifier.testTag("surname_input")
        )

        Button(
            onClick = {
                viewModel.signUpUserSecond(
                    name,
                    surname,
                    context
                ) {
                    navController.navigate("account_verification")
                }
            }, modifier = Modifier.testTag("end_registration_button")
        ) {
            Text(
                text = if (isLoading) {
                    stringResource(R.string.complete_registration) + "..."
                } else {
                    stringResource(R.string.complete_registration)
                }
            )
        }

        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier.testTag("skip_stage_button")
        ) {
            Text(text = stringResource(R.string.skip_this_stage))
        }

        if (error != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = error ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("error_text")
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