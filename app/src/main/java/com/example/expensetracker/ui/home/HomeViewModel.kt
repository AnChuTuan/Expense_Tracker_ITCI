package com.example.expensetracker.ui.home

import androidx.lifecycle.*
import com.example.expensetracker.data.api.RetrofitClient
import com.example.expensetracker.data.model.Expense
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> = _expenses

    fun loadExpenses(userId: Int) {
        viewModelScope.launch {
            try {
                val res = RetrofitClient.instance.getExpenses(userId)
                if (res.isSuccessful) _expenses.value = res.body()?.data ?: emptyList()
            } catch (e: Exception) { }
        }
    }
}