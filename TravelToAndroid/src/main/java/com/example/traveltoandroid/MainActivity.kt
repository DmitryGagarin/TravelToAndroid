package com.example.traveltoandroid

import BottomNavigationBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.traveltoandroid.navigation.NavHostContainer
import com.example.traveltoandroid.ui.theme.TravelToTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelToTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val hideBottomBarRoute = listOf(
                    "login", "registration", "registration_second", "account_verification", "reset_password"
                )
                val shouldShowBottomBar = !hideBottomBarRoute.any { route ->
                    currentRoute?.matches(route.toRegex()) == true
                }

                Surface(color = Color.White) {
                    Scaffold(
                        bottomBar = {
                            if (shouldShowBottomBar) {
                                BottomNavigationBar(navController = navController)
                            }
                        }, content = { padding ->
                            NavHostContainer(navController = navController, padding = padding)
                        }
                    )
                }
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Attractions",
            icon = Icons.Filled.Place,
            route = "attractions"
        ),
        BottomNavItem(
            label = "Liked",
            icon = Icons.Filled.Favorite,
            route = "like"
        ),
        BottomNavItem(
            label = "Articles",
            icon = Icons.Default.Menu,
            route = "articles"
        ),
        BottomNavItem(
            label = "Account",
            icon = Icons.Filled.Person,
            route = "profile"
        )
    )
}
