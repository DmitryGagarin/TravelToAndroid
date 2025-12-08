package com.example.traveltoandroid.views.settings

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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.traveltoandroid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(onBackClick: () -> Unit, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.account)) },
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
                .padding(16.dp)
        ) {
            SettingItem(
                text = stringResource(R.string.edit_account),
                icon = Icons.Filled.Person,
                route = "edit_account",
                navController = navController
            )
            SettingItem(
                text = stringResource(R.string.i_m_business_owner),
                icon = Icons.Filled.Add,
                route = "create_attraction",
                navController = navController
            )
            SettingItem(
                text = stringResource(R.string.write_article),
                icon = Icons.Filled.Create,
                route = "create_article",
                navController = navController
            )
            SettingItem(
                text = stringResource(R.string.rate_app),
                icon = Icons.Filled.ThumbUp,
                route = "rate_app",
                navController = navController
            )
            SettingItem(
                text = stringResource(R.string.moderation),
                icon = Icons.AutoMirrored.Filled.Send,
                route = "moderation",
                navController = navController
            )
            SettingItem(
                text = stringResource(R.string.leave_account),
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
    icon: ImageVector,
    route: String,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = {
                navController.navigate(route)
            }
        ) {
            Row {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(end = 16.dp)
                )
                Text(
                    text = text,
                    modifier = Modifier.weight(1f)
                )
            }

        }
    }
}