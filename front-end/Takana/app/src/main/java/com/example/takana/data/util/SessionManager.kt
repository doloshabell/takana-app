package com.example.takana.data.util

import android.content.Context
import android.content.SharedPreferences
import com.example.takana.R

object SessionManager {

    private const val USER_TOKEN = "USER_TOKEN"

    fun saveAuthToken(context: Context, token: String) {
        saveString(context, token)
    }

    private fun saveString(context: Context, value: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(
            context.getString(
                R.string.app_name
            ), Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, value)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        return getString(context, USER_TOKEN)
    }

    fun getString(context: Context, key: String): String? {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }

    fun clearDataSession(context: Context) {
        val editor =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .edit()
        editor.clear()
        editor.apply()
    }
}