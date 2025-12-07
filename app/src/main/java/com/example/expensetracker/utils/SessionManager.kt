package com.example.expensetracker.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUser(id: Int, name: String) {
        prefs.edit().putInt("USER_ID", id).putString("USER_NAME", name).apply()
    }

    fun getUserId(): Int = prefs.getInt("USER_ID", -1)

    fun logout() {
        prefs.edit().clear().apply()
    }

    // hàm về currency

    fun saveCurrency(currency: String) {
        prefs.edit().putString("CURRENCY", currency).apply()
    }

    fun getCurrency(): String = prefs.getString("CURRENCY", "USD") ?: "USD"

    fun getCurrencySymbol(): String = if (getCurrency() == "VND") "₫" else "$"

    fun getExchangeRate(): Double {
        // tạm 1$ là 25k
        return if (getCurrency() == "VND") 25000.0 else 1.0
    }
}