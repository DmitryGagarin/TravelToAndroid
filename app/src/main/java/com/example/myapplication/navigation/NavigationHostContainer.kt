package com.example.myapplication.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.views.attraction.AttractionView
import com.example.myapplication.views.attraction.AttractionsView
import com.example.myapplication.views.auth.LoginView
import com.example.myapplication.views.auth.RegistrationFirstView
import com.example.myapplication.views.auth.ResetPasswordView
import com.example.myapplication.views.user.LikeView
import com.example.myapplication.views.user.ProfileView
import com.example.myapplication.views.settings.SettingsView
import com.example.myapplication.views.article.CreateArticleView
import com.example.myapplication.views.attraction.CreateAttractionView
import com.example.myapplication.views.settings.EditAccountView
import com.example.myapplication.views.admin.ModerationView
import com.example.myapplication.views.RateScreen
import com.example.myapplication.views.auth.AccountVerificationView
import com.example.myapplication.views.auth.RegistrationSecondView

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
