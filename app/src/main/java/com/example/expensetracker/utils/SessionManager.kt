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

    // --- CÁC HÀM XỬ LÝ TIỀN TỆ (ĐANG BỊ THIẾU) ---

    fun saveCurrency(currency: String) {
        prefs.edit().putString("CURRENCY", currency).apply()
    }

    fun getCurrency(): String = prefs.getString("CURRENCY", "USD") ?: "USD"

    // Đây là hàm mà máy bạn đang báo lỗi "Unresolved reference"
    fun getCurrencySymbol(): String = if (getCurrency() == "VND") "₫" else "$"

    fun getExchangeRate(): Double {
        // Giả sử 1 USD = 25,000 VND
        return if (getCurrency() == "VND") 25000.0 else 1.0
    }
}