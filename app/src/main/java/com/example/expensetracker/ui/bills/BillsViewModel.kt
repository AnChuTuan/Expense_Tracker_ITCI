package com.example.expensetracker.ui.bills

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.api.RetrofitClient
import com.example.expensetracker.data.model.Bill
import kotlinx.coroutines.launch

class BillsViewModel : ViewModel() {
    val bills = MutableLiveData<List<Bill>>()

    val isLoading = MutableLiveData<Boolean>()

    fun loadBills(userId: Int) {
        isLoading.value = true
        //LOGCAT để check bill
        Log.d("DEBUG_BILLS", "tải bill cho UserID: $userId")

        viewModelScope.launch {
            try {
                val res = RetrofitClient.instance.getBills(userId)

                if (res.isSuccessful && res.body()?.status == "success") {
                    val list = res.body()?.data ?: emptyList()
                    Log.d("DEBUG_BILLS", "tải thành công, số lượng bill: ${list.size}")

                    // Mẹo: Tạo list mới để Adapter nhận biết sự thay đổi
                    bills.value = ArrayList(list)
                } else {
                    Log.e("DEBUG_BILLS", "lỗi Server trả về: ${res.message()}")
                    bills.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("DEBUG_BILLS", "lỗi Exception: ${e.message}")
                e.printStackTrace()
                bills.value = emptyList()
            } finally {
                isLoading.value = false
            }
        }
    }

    fun updateStatus(id: Int, status: String, userId: Int) {
        viewModelScope.launch { try { RetrofitClient.instance.updateBillStatus(id, status); loadBills(userId) } catch (e: Exception){} }
    }
    fun deleteBill(id: Int, userId: Int) {
        viewModelScope.launch { try { RetrofitClient.instance.deleteBill(id); loadBills(userId) } catch (e: Exception){} }
    }
}