package com.example.myapplication

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.screens.AttractionScreen
import com.example.myapplication.screens.LikeScreen
import com.example.myapplication.screens.ProfileScreen
import com.example.myapplication.screens.SettingsScreen
import com.example.myapplication.screens.settings.CreateArticleScreen
import com.example.myapplication.screens.settings.CreateAttractionScreen
import com.example.myapplication.screens.settings.EditAccountScreen
import com.example.myapplication.screens.settings.ModerationScreen
import com.example.myapplication.screens.settings.RateScreen

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,

        // set the start destination as home
        startDestination = "attractions",

        // Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),

        builder = {
            // route : Home
            composable("attractions") {
                AttractionScreen()
            }
            // route : search
            composable("like") {
                LikeScreen()
            }
            // route : profile
            composable("profile") {
                ProfileScreen(navController = navController)
            }
            composable("settings") {
                SettingsScreen(
                    onBackClick = { navController.popBackStack() },
                    navController = navController
                )
            }
            composable("edit_account") {
                EditAccountScreen(
                    onBackClick = { navController.popBackStack() },
                )
            }
            composable("create_attraction") {
                CreateAttractionScreen(
                    onBackClick = { navController.popBackStack() },
                )
            }
            composable("create_article") {
                CreateArticleScreen(
                    onBackClick = { navController.popBackStack() },
                )
            }
            composable("rate_app") {
                RateScreen(
                    onBackClick = { navController.popBackStack() },
                )
            }
            composable("moderation") {
                ModerationScreen(
                    onBackClick = { navController.popBackStack() },
                )
            }
        }
    )
}
