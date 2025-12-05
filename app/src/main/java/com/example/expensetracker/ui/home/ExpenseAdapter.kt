package com.example.expensetracker.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.databinding.ItemExpenseBinding
import com.example.expensetracker.utils.SessionManager

class ExpenseAdapter(private var list: List<Expense> = emptyList()) : RecyclerView.Adapter<ExpenseAdapter.VH>() {

    fun submitList(newList: List<Expense>) {
        list = newList
        notifyDataSetChanged()
    }

    class VH(val binding: ItemExpenseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]

        // --- LOGIC TIỀN TỆ MỚI ---
        val session = SessionManager(holder.itemView.context)
        val rate = session.getExchangeRate()
        val symbol = session.getCurrencySymbol()
        val displayAmount = item.amount * rate
        val format = if (session.getCurrency() == "VND") "%,.0f" else "%.2f"

        holder.binding.tvTitle.text = item.title
        holder.binding.tvDate.text = item.date

        if ((item.type ?: "EXPENSE") == "INCOME") {
            holder.binding.tvAmount.text = "+ $symbol ${format.format(displayAmount)}"
            holder.binding.tvAmount.setTextColor(Color.parseColor("#2E7D32"))
        } else {
            holder.binding.tvAmount.text = "- $symbol ${format.format(displayAmount)}"
            holder.binding.tvAmount.setTextColor(Color.parseColor("#C62828"))
        }
    }

    override fun getItemCount() = list.size
}