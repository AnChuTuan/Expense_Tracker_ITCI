package com.example.expensetracker.ui.stats

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.expensetracker.R
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.databinding.FragmentStatsBinding
import com.example.expensetracker.ui.home.HomeViewModel // Dùng chung ViewModel với Home cho tiện
import com.example.expensetracker.utils.SessionManager
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import androidx.fragment.app.activityViewModels
class StatsFragment : Fragment(R.layout.fragment_stats) {
    private lateinit var binding: FragmentStatsBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatsBinding.bind(view)

        viewModel.expenses.observe(viewLifecycleOwner) { list ->
            setupPieChart(list)
        }
    }

    override fun onResume() {
        super.onResume()
        val userId = SessionManager(requireContext()).getUserId()
        viewModel.loadExpenses(userId)
    }
    private fun setupPieChart(list: List<Expense>) {
        var income = 0.0f
        var expense = 0.0f

        for (item in list) {
            // Ép kiểu Double về Float vì thư viện biểu đồ dùng Float
            if ((item.type ?: "EXPENSE") == "INCOME") {
                income += item.amount.toFloat()
            } else {
                expense += item.amount.toFloat()
            }
        }

        // Tạo dữ liệu cho biểu đồ
        val entries = ArrayList<PieEntry>()
        if (income > 0) entries.add(PieEntry(income, "Income"))
        if (expense > 0) entries.add(PieEntry(expense, "Expense"))

        val dataSet = PieDataSet(entries, "Financial Status")

        // Màu sắc: Xanh lá cho Income, Đỏ cho Expense
        dataSet.colors = listOf(Color.parseColor("#2E7D32"), Color.parseColor("#C62828"))
        dataSet.valueTextSize = 16f
        dataSet.valueTextColor = Color.WHITE

        val data = PieData(dataSet)
        binding.pieChart.data = data
        binding.pieChart.description.isEnabled = false
        binding.pieChart.centerText = "Overview"
        binding.pieChart.setCenterTextSize(18f)
        binding.pieChart.animateY(1000) // Hiệu ứng xoay
        binding.pieChart.invalidate() // Vẽ lại
    }
}