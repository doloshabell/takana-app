package com.example.takana.data.util

import android.content.Context
import com.example.takana.R
import com.google.gson.Gson

object UserToken {

    private const val USER_OBJECT = "USER_OBJECT"

    fun saveObjectToSharedPreferences(
        context: Context,
        name: String,
        username: String,
        userId: Long?
    ) {
        val sharedPref =
            context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val jsonObject = Gson().toJson(User(name, username, userId))

        editor.putString(USER_OBJECT, jsonObject)
        editor.apply()
    }

    data class User(
        val name: String,
        val username: String,
        val userId: Long?
    )

    fun getObjectFromSharedPreferences(context: Context): User? {
        val sharedPref = context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)

        val jsonObject = sharedPref.getString(USER_OBJECT, null)

        return if (jsonObject != null) {
            Gson().fromJson(jsonObject, User::class.java)
        } else {
            null
        }
    }

    fun clearDataUserToken(context: Context) {
        val editor =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .edit()
        editor.clear()
        editor.apply()
    }
}