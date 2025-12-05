package com.example.expensetracker.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.data.model.Expense // <--- THÊM DÒNG QUAN TRỌNG NÀY
import com.example.expensetracker.databinding.FragmentHomeBinding
import com.example.expensetracker.utils.SessionManager

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = ExpenseAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val userId = SessionManager(requireContext()).getUserId()

        binding.rvExpenses.layoutManager = LinearLayoutManager(context)
        binding.rvExpenses.adapter = adapter

        viewModel.loadExpenses(userId)

        // Quan sát dữ liệu
        viewModel.expenses.observe(viewLifecycleOwner) { list ->
            // 'list' ở đây là danh sách expense lấy từ API về
            adapter.submitList(list)
            calculateStats(list) // Gọi hàm tính toán
        }

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_add)
        }
    }

    // --- HÀM TÍNH TOÁN (Phải nằm NGOÀI onViewCreated nhưng TRONG class) ---
    private fun calculateStats(list: List<Expense>) {
        var income = 0.0
        var expense = 0.0

        for (item in list) {
            // Bây giờ nó sẽ hiểu item.type vì đã import Expense ở trên
            if (item.type == "INCOME") {
                income += item.amount
            } else {
                expense += item.amount
            }
        }

        val balance = income - expense

        binding.tvTotalIncome.text = "+ $${income}"
        binding.tvTotalExpense.text = "- $${expense}"
        binding.tvTotalBalance.text = "$${balance}"
    }
}