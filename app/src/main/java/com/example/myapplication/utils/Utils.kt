package com.example.myapplication.utils

import android.content.Context

fun getAccessToken(context: Context): String {
    val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return "Bearer " + sharedPref.getString("access_token", "access_token_not_found").toString()
}