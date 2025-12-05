package com.example.expensetracker.ui.bills

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.api.RetrofitClient
import kotlinx.coroutines.launch

class AddBillViewModel : ViewModel() {
    val success = MutableLiveData<Boolean>()

    fun addBill(uid: Int, title: String, amount: Double, date: String) {
        viewModelScope.launch {
            try {
                // Gọi API addBill đã khai báo trong ApiService
                val res = RetrofitClient.instance.addBill(uid, title, amount, date)
                success.value = res.isSuccessful && res.body()?.status == "success"
            } catch (e: Exception) {
                success.value = false
            }
        }
    }
}