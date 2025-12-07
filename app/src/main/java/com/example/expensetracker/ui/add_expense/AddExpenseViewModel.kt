package com.example.expensetracker.ui.add_expense

import androidx.lifecycle.*
import com.example.expensetracker.data.api.RetrofitClient
import kotlinx.coroutines.launch

class AddExpenseViewModel : ViewModel() {
    val success = MutableLiveData<Boolean>()

    fun add(uid: Int, title: String, amount: Double, date: String, type: String) {
        viewModelScope.launch {
            try {
                // call api
                val res = RetrofitClient.instance.addExpense(uid, title, amount, date, type)
                success.value = res.isSuccessful && res.body()?.status == "success"
            } catch (e: Exception) {
                success.value = false
            }
        }
    }
}