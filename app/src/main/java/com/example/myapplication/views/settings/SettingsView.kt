package com.example.myapplication.views.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(onBackClick: () -> Unit, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Account") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
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
                .padding(16.dp)
        ) {
            SettingItem(
                text = "Edit Account",
                icon = Icons.Filled.Person,
                route = "edit_account",
                navController = navController
            )
            SettingItem(
                text = "I'm business owner",
                icon = Icons.Filled.Add,
                route = "create_attraction",
                navController = navController
            )
            SettingItem(
                text = "Write article",
                icon = Icons.Filled.Create,
                route = "create_article",
                navController = navController
            )
            SettingItem(
                text = "Rate app",
                icon = Icons.Filled.ThumbUp,
                route = "rate_app",
                navController = navController
            )
            SettingItem(
                text = "Moderation",
                icon = Icons.AutoMirrored.Filled.Send,
                route = "moderation",
                navController = navController
            )
            SettingItem(
                text = "Leave Account",
                icon = Icons.Filled.Warning,
                route = "login",
                navController = navController
            )
        }
    }
}

@Composable
fun SettingItem(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    route: String,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                navController.navigate(route)
            }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 16.dp)
            )
        }
        Text(
            text = text,
            modifier = Modifier.weight(1f)
        )
    }
}