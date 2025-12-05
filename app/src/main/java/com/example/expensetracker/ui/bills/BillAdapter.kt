package com.example.expensetracker.ui.bills

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.data.model.Bill
import com.example.expensetracker.databinding.ItemBillBinding
import com.example.expensetracker.utils.SessionManager

class BillAdapter(
    private val onItemClick: (Bill) -> Unit
) : RecyclerView.Adapter<BillAdapter.VH>() {

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

        // 1. Xử lý Tiền tệ (VND/USD)
        val session = SessionManager(holder.itemView.context)
        val rate = session.getExchangeRate()
        val symbol = session.getCurrencySymbol()
        val displayAmount = item.amount * rate
        val format = if (session.getCurrency() == "VND") "%,.0f" else "%.2f"

        // 2. Gán dữ liệu
        holder.binding.tvBillTitle.text = item.title
        holder.binding.tvBillAmount.text = "$symbol ${format.format(displayAmount)}"
        holder.binding.tvBillDate.text = "Due: ${item.due_date}"
        holder.binding.tvBillStatus.text = item.status

        // 3. Đổi màu trạng thái
        if (item.status == "PAID") {
            holder.binding.tvBillStatus.setTextColor(Color.parseColor("#388E3C")) // Xanh đậm
            holder.binding.tvBillTitle.alpha = 0.5f // Làm mờ tiêu đề nếu đã trả
        } else {
            holder.binding.tvBillStatus.setTextColor(Color.parseColor("#D32F2F")) // Đỏ
            holder.binding.tvBillTitle.alpha = 1.0f
        }

        // 4. Sự kiện click
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = list.size
}