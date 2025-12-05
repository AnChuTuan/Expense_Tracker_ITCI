package com.example.expensetracker.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUser(id: Int, name: String) {
        prefs.edit().putInt("USER_ID", id).putString("USER_NAME", name).apply()
    }

    fun getUserId(): Int = prefs.getInt("USER_ID", -1)
    fun getUserName(): String? = prefs.getString("USER_NAME", "User")
    fun logout() = prefs.edit().clear().apply()
}