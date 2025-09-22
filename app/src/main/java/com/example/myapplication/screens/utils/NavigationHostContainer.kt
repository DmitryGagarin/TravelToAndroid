package com.example.myapplication.screens.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.screens.attractions.AttractionScreen
import com.example.myapplication.screens.attractions.AttractionsScreen
import com.example.myapplication.screens.main.LikeScreen
import com.example.myapplication.screens.main.ProfileScreen
import com.example.myapplication.screens.settings.SettingsScreen
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
        startDestination = "attractions",
        modifier = Modifier.padding(paddingValues = padding),

        builder = {
            composable("attractions") {
                AttractionsScreen(navController = navController)
            }
            composable("attraction") {
                AttractionScreen(
                    navController = navController,
                    onBackClick = { navController.popBackStack() },
                )
            }
            composable("like") {
                LikeScreen(navController = navController)
            }
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
