package com.example.expensetracker.ui.login

import androidx.lifecycle.*
import com.example.expensetracker.data.api.RetrofitClient
import com.example.expensetracker.data.model.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val loginResult = MutableLiveData<LoginResponse?>()

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.login(email, pass)
                if (response.isSuccessful) loginResult.value = response.body()
            } catch (e: Exception) { loginResult.value = null }
        }
    }
}