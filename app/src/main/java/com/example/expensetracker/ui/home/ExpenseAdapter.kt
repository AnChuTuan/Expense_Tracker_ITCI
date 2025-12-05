package com.example.expensetracker.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.databinding.ItemExpenseBinding

class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.VH>() {
    private var list = listOf<Expense>()

    fun submitList(newList: List<Expense>) {
        list = newList
        notifyDataSetChanged()
    }

    class VH(val binding: ItemExpenseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]
        holder.binding.tvTitle.text = item.title
        holder.binding.tvDate.text = item.date

        if (item.type == "INCOME") {
            holder.binding.tvAmount.text = "+ $${item.amount}"
            holder.binding.tvAmount.setTextColor(Color.parseColor("#2E7D32")) // Màu xanh lá
        } else {
            holder.binding.tvAmount.text = "- $${item.amount}"
            holder.binding.tvAmount.setTextColor(Color.parseColor("#C62828")) // Màu đỏ
        }
    }

    override fun getItemCount() = list.size
}