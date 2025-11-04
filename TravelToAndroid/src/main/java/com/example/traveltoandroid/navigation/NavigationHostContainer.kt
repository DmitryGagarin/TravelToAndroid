package com.example.traveltoandroid.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.traveltoandroid.views.attraction.AttractionView
import com.example.traveltoandroid.views.attraction.AttractionsView
import com.example.traveltoandroid.views.auth.LoginView
import com.example.traveltoandroid.views.auth.RegistrationFirstView
import com.example.traveltoandroid.views.auth.ResetPasswordView
import com.example.traveltoandroid.views.user.LikeView
import com.example.traveltoandroid.views.user.ProfileView
import com.example.traveltoandroid.views.settings.SettingsView
import com.example.traveltoandroid.views.article.CreateArticleView
import com.example.traveltoandroid.views.attraction.CreateAttractionView
import com.example.traveltoandroid.views.settings.EditAccountView
import com.example.traveltoandroid.views.admin.ModerationView
import com.example.traveltoandroid.views.RateScreen
import com.example.traveltoandroid.views.auth.AccountVerificationView
import com.example.traveltoandroid.views.auth.RegistrationSecondView

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = Modifier.padding(paddingValues = padding),

        builder = {
            composable("login") {
                LoginView(navController = navController)
            }
            composable("registration") {
                RegistrationFirstView(navController = navController)
            }
            composable("registration_second") {
                RegistrationSecondView(navController = navController)
            }
            composable("account_verification") {
                AccountVerificationView(navController = navController)
            }
            composable("reset_password") {
                ResetPasswordView(navController = navController)
            }
            composable("attractions") {
                AttractionsView(navController = navController)
            }
            composable("attraction/{attractionName}") {
                val attractionName: String = it.arguments?.getString("attractionName").toString()
                AttractionView(
                    navController = navController,
                    onBackClick = { navController.popBackStack() },
                    attractionName = attractionName
                )
            }
            composable("like") {
                LikeView(navController = navController)
            }
            composable("profile") {
                ProfileView(navController = navController)
            }
            composable("settings") {
                SettingsView(
                    onBackClick = { navController.popBackStack() },
                    navController = navController
                )
            }
            composable("edit_account") {
                EditAccountView(
                    navController = navController,
                    onBackClick = { navController.popBackStack() },
                )
            }
            composable("create_attraction") {
                CreateAttractionView(
                    onBackClick = { navController.popBackStack() },
                )
            }
            composable("create_article") {
                CreateArticleView(
                    onBackClick = { navController.popBackStack() },
                )
            }
            composable("rate_app") {
                RateScreen(
                    onBackClick = { navController.popBackStack() },
                )
            }
            composable("moderation") {
                ModerationView(
                    onBackClick = { navController.popBackStack() },
                )
            }
        }
    )
}
