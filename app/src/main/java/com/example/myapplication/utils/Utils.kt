package com.example.myapplication.utils

import android.content.Context
import com.example.myapplication.models.AuthUser

fun getAccessToken(context: Context): String {
    val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return "Bearer " + sharedPref.getString("access_token", "access_token_not_found").toString()
}

fun saveUserToSharedPrefs(context: Context, user: AuthUser) {
    val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("access_token", user.accessToken)
        putString("email", user.email)
        putString("user_name", user.name)
        putBoolean("is_logged_in", true)
        apply()
    }
}