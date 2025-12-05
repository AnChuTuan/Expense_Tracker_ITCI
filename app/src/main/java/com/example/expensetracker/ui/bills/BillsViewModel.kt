package com.example.expensetracker.ui.bills

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.api.RetrofitClient
import com.example.expensetracker.data.model.Bill
import kotlinx.coroutines.launch

class BillsViewModel : ViewModel() {
    val bills = MutableLiveData<List<Bill>>()
    val error = MutableLiveData<String>()

    fun loadBills(userId: Int) {
        viewModelScope.launch {
            try {
                val res = RetrofitClient.instance.getBills(userId)
                if (res.isSuccessful && res.body()?.status == "success") {
                    bills.value = res.body()?.data ?: emptyList()
                } else {
                    bills.value = emptyList()
                }
            } catch (e: Exception) {
                error.value = "Connection Error"
                bills.value = emptyList()
            }
        }
    }

    fun updateStatus(id: Int, status: String, userId: Int) {
        viewModelScope.launch {
            try {
                RetrofitClient.instance.updateBillStatus(id, status)
                loadBills(userId) // Tải lại ngay sau khi update
            } catch (e: Exception) { }
        }
    }

    fun deleteBill(id: Int, userId: Int) {
        viewModelScope.launch {
            try {
                RetrofitClient.instance.deleteBill(id)
                loadBills(userId) // Tải lại ngay sau khi xóa
            } catch (e: Exception) { }
        }
    }
}