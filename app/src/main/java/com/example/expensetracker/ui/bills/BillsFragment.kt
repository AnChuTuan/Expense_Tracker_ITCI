package com.example.expensetracker.ui.bills

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.data.model.Bill
import com.example.expensetracker.databinding.FragmentBillsBinding
import com.example.expensetracker.utils.SessionManager

class BillsFragment : Fragment(R.layout.fragment_bills) {
    private lateinit var binding: FragmentBillsBinding
    private val viewModel: BillsViewModel by viewModels() // Dùng viewModels() là đủ nếu có onResume

    private val adapter = BillAdapter { bill ->
        showOptionsDialog(bill)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBillsBinding.bind(view)

        binding.rvBills.layoutManager = LinearLayoutManager(context)
        binding.rvBills.adapter = adapter

        // Observer: Khi data thay đổi thì update list ngay
        viewModel.bills.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            // Hiển thị thông báo nếu list rỗng
            if (list.isEmpty()) {
                // Bạn có thể thêm 1 TextView "No Bills" trong XML và hiện nó lên ở đây
            }
        }

        binding.fabAddBill.setOnClickListener {
            findNavController().navigate(R.id.action_bills_to_addBill)
        }
    }

    // --- FIX QUAN TRỌNG: LUÔN TẢI LẠI DATA KHI MÀN HÌNH HIỆN RA ---
    override fun onResume() {
        super.onResume()
        val userId = SessionManager(requireContext()).getUserId()
        viewModel.loadBills(userId)
    }

    private fun showOptionsDialog(bill: Bill) {
        val options = arrayOf("Mark as PAID", "Mark as UNPAID", "DELETE Bill")
        val userId = SessionManager(requireContext()).getUserId()

        AlertDialog.Builder(requireContext())
            .setTitle(bill.title)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        viewModel.updateStatus(bill.id, "PAID", userId)
                        Toast.makeText(context, "Paid!", Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        viewModel.updateStatus(bill.id, "UNPAID", userId)
                        Toast.makeText(context, "Unpaid!", Toast.LENGTH_SHORT).show()
                    }
                    2 -> confirmDelete(bill.id, userId)
                }
            }
            .show()
    }

    private fun confirmDelete(billId: Int, userId: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Bill?")
            .setMessage("Are you sure?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteBill(billId, userId)
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }
}