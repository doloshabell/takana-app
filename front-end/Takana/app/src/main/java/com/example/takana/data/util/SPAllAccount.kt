package com.example.takana.data.util

import android.content.Context
import android.content.SharedPreferences
import com.example.takana.R
import com.example.takana.data.model.response.DataAccount
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser

object SPAllAccount {

    private const val ALL_ACCOUNT = "ALL_ACCOUNT"

    fun saveAllAccount(context: Context, allAccount: ArrayList<DataAccount>) {
        saveAllAccountToSharedPreferences(context, allAccount)
    }

    private fun saveAllAccountToSharedPreferences(
        context: Context,
        arrayList: ArrayList<DataAccount>
    ) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val gson = Gson()
        val jsonDataAccount = JsonArray()

        for (dataAccount in arrayList) {
            val dataObject = gson.toJsonTree(dataAccount).asJsonObject
            jsonDataAccount.add(dataObject)
        }

        editor.putString(ALL_ACCOUNT, jsonDataAccount.toString())
        editor.apply()
    }

    fun getAccountList(context: Context): ArrayList<DataAccount> {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val jsonArrayString = sharedPreferences.getString(ALL_ACCOUNT, null)

        val gson = Gson()
        val accountList = ArrayList<DataAccount>()

        if (!jsonArrayString.isNullOrEmpty()) {
            val jsonArray = JsonParser.parseString(jsonArrayString).asJsonArray

            for (jsonElement in jsonArray) {
                val allAccount = gson.fromJson(jsonElement, DataAccount::class.java)
                accountList.add(allAccount)
            }
        }

        return accountList
    }

    fun clearDataAccount(context: Context) {
        val editor =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .edit()
        editor.clear()
        editor.apply()
    }

}