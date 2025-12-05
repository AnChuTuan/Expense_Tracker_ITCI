package com.example.expensetracker.ui.bills

import android.os.Bundle
import android.view.View
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
    private val viewModel: BillsViewModel by viewModels()

    // Khởi tạo Adapter với sự kiện click
    private val adapter = BillAdapter { bill ->
        showOptionsDialog(bill)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBillsBinding.bind(view)
        val userId = SessionManager(requireContext()).getUserId()

        binding.rvBills.layoutManager = LinearLayoutManager(context)
        binding.rvBills.adapter = adapter

        viewModel.loadBills(userId)
        viewModel.bills.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        binding.fabAddBill.setOnClickListener {
            findNavController().navigate(R.id.action_bills_to_addBill)
        }
    }

    private fun showOptionsDialog(bill: Bill) {
        val options = arrayOf("Mark as PAID", "Mark as UNPAID", "DELETE Bill")
        val userId = SessionManager(requireContext()).getUserId()

        AlertDialog.Builder(requireContext())
            .setTitle(bill.title)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> viewModel.updateStatus(bill.id, "PAID", userId)
                    1 -> viewModel.updateStatus(bill.id, "UNPAID", userId)
                    2 -> confirmDelete(bill.id, userId)
                }
            }
            .show()
    }

    private fun confirmDelete(billId: Int, userId: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Bill?")
            .setMessage("Are you sure you want to delete this bill?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteBill(billId, userId)
            }
            .setNegativeButton("No", null)
            .show()
    }
}