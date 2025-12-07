package com.example.expensetracker.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.api.RetrofitClient
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val registerStatus = MutableLiveData<String?>() // null = idle, "Success" = ok, khác = error message

    fun register(name: String, email: String, pass: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.register(email, pass, name)
                if (response.isSuccessful && response.body()?.status == "success") {
                    registerStatus.value = "Success"
                } else {
                    registerStatus.value = response.body()?.message ?: "Registration Failed"
                }
            } catch (e: Exception) {
                registerStatus.value = "Connection Error: ${e.message}"
            }
        }
    }
}