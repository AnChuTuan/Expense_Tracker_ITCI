package com.example.expensetracker.ui.bills

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.api.RetrofitClient
import com.example.expensetracker.data.model.Bill
import kotlinx.coroutines.launch

class BillsViewModel : ViewModel() {
    val bills = MutableLiveData<List<Bill>>()

    fun loadBills(userId: Int) {
        viewModelScope.launch {
            try {
                val res = RetrofitClient.instance.getBills(userId)
                if (res.isSuccessful) {
                    bills.value = res.body()?.data ?: emptyList()
                }
            } catch (e: Exception) {
                bills.value = emptyList()
            }
        }
    }
}