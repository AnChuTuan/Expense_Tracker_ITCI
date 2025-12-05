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

    fun updateStatus(id: Int, status: String, userId: Int) {
        viewModelScope.launch {
            try {
                RetrofitClient.instance.updateBillStatus(id, status)
                loadBills(userId) // Load lại sau khi sửa
            } catch (e: Exception) { }
        }
    }

    fun deleteBill(id: Int, userId: Int) {
        viewModelScope.launch {
            try {
                RetrofitClient.instance.deleteBill(id)
                loadBills(userId) // Load lại sau khi xóa
            } catch (e: Exception) { }
        }
    }
}