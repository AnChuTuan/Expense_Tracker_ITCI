package com.example.expensetracker.ui.bills

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.data.model.Bill
import com.example.expensetracker.databinding.ItemBillBinding
import com.example.expensetracker.utils.SessionManager

class BillAdapter(
    private val onItemClick: (Bill) -> Unit
) : ListAdapter<Bill, BillAdapter.VH>(BillDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemBillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClick)
    }

    class VH(private val binding: ItemBillBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Bill, onItemClick: (Bill) -> Unit) {
            val context = itemView.context
            val session = SessionManager(context)
            val rate = session.getExchangeRate()
            val symbol = session.getCurrencySymbol()
            val displayAmount = item.amount * rate
            val format = if (session.getCurrency() == "VND") "%,.0f" else "%.2f"

            binding.tvBillTitle.text = item.title
            binding.tvBillAmount.text = "$symbol ${format.format(displayAmount)}"
            binding.tvBillDate.text = context.getString(R.string.due_date, item.due_date)

            binding.tvBillStatus.text = item.status
            if (item.status == "PAID") {
                binding.tvBillStatus.setTextColor(ContextCompat.getColor(context, R.color.green_paid))
            } else {
                binding.tvBillStatus.setTextColor(ContextCompat.getColor(context, R.color.orange_unpaid))
            }

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    class BillDiffCallback : DiffUtil.ItemCallback<Bill>() {
        override fun areItemsTheSame(oldItem: Bill, newItem: Bill): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Bill, newItem: Bill): Boolean {
            return oldItem == newItem
        }
    }
}