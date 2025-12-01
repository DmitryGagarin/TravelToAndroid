package com.example.traveltoandroid.views.settings

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.traveltoandroid.viewModels.user.UserViewModel
import com.example.traveltoandroid.viewModels.user.interfaces.IUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccountView(
    navController: NavController,
    onBackClick: () -> Unit,
    viewModel: IUserViewModel = viewModel<UserViewModel>()
) {
    val context: Context = LocalContext.current
    val user by viewModel.user.collectAsState()

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        name = user?.name ?: ""
        surname = user?.surname ?: ""
        email = user?.email ?: ""
        phone = user?.phone ?: ""
    }

    LaunchedEffect(Unit) {
        viewModel.getUser(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .testTag("main_column")
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.name)) },
                modifier = Modifier.testTag("name")
            )

            TextField(
                value = surname,
                onValueChange = { surname = it },
                label = { Text(stringResource(R.string.surname)) },
                modifier = Modifier.testTag("surname")
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.email)) },
                modifier = Modifier.testTag("email")
            )

            TextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text(stringResource(R.string.phone)) },
                modifier = Modifier.testTag("phone")
            )

            Button(
                onClick = {
                    viewModel.editUserData(
                        context = context,
                        name, surname, email, phone
                    ) {
                        navController.navigate("edit_account")
                    }
                },
                modifier = Modifier.testTag("edit_account_button")
            )
            {
                Text(text = stringResource(R.string.save_changed))
            }
        }
    }
}