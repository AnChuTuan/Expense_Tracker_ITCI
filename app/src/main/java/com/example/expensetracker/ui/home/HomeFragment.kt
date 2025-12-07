package com.example.expensetracker.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.databinding.FragmentHomeBinding
import com.example.expensetracker.utils.SessionManager
import androidx.fragment.app.activityViewModels
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private val adapter = ExpenseAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val userId = SessionManager(requireContext()).getUserId()

        binding.rvExpenses.layoutManager = LinearLayoutManager(context)
        binding.rvExpenses.adapter = adapter

        // 3 nút event
        binding.btnChart.setOnClickListener { findNavController().navigate(R.id.action_home_to_stats) }
        binding.btnBills.setOnClickListener { findNavController().navigate(R.id.action_home_to_bills) }
        binding.btnSettings.setOnClickListener { findNavController().navigate(R.id.action_home_to_settings) }

        binding.fabAdd.setOnClickListener { findNavController().navigate(R.id.action_home_to_add) }

        viewModel.loadExpenses(userId)
        viewModel.expenses.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                adapter.submitList(list)
                calculateStats(list)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val userId = SessionManager(requireContext()).getUserId()
        viewModel.loadExpenses(userId)
    }

    private fun calculateStats(list: List<Expense>) {
        // currency tính toán
        val session = SessionManager(requireContext())
        val rate = session.getExchangeRate()
        val symbol = session.getCurrencySymbol()
        val format = if (session.getCurrency() == "VND") "%,.0f" else "%.2f"

        var income = 0.0
        var expense = 0.0

        for (item in list) {
            if ((item.type ?: "EXPENSE") == "INCOME") {
                income += item.amount
            } else {
                expense += item.amount
            }
        }

        val balance = income - expense

        // nhân tỷ giá tiền
        binding.tvTotalIncome.text = "+ $symbol ${format.format(income * rate)}"
        binding.tvTotalExpense.text = "- $symbol ${format.format(expense * rate)}"
        binding.tvTotalBalance.text = "$symbol ${format.format(balance * rate)}"
    }
}