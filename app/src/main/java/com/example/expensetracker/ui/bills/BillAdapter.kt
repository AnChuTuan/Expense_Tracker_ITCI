package com.example.expensetracker.ui.bills

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.data.model.Bill
import com.example.expensetracker.databinding.ItemBillBinding

class BillAdapter : RecyclerView.Adapter<BillAdapter.VH>() {
    private var list = listOf<Bill>()

    fun submitList(newList: List<Bill>) {
        list = newList
        notifyDataSetChanged()
    }

    class VH(val binding: ItemBillBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemBillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]
        holder.binding.tvBillTitle.text = item.title
        holder.binding.tvBillAmount.text = "$${item.amount}"
        holder.binding.tvBillDate.text = "Hạn: ${item.due_date}"
        holder.binding.tvBillStatus.text = item.status
    }

    override fun getItemCount() = list.size
}