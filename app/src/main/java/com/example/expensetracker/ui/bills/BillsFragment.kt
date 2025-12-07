package com.example.expensetracker.ui.bills

import android.os.Bundle
import android.util.Log
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
    private val viewModel: BillsViewModel by viewModels()

    private val adapter = BillAdapter { bill ->
        showOptionsDialog(bill)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBillsBinding.bind(view)

        binding.rvBills.layoutManager = LinearLayoutManager(context)
        binding.rvBills.adapter = adapter

        // check data của bill
        viewModel.bills.observe(viewLifecycleOwner) { list ->
            Log.d("DEBUG_BILLS", "UI nhận được list size: ${list.size}")
            adapter.submitList(list)

            // noti báo nếu list rỗng
            if (list.isEmpty()) {
                Toast.makeText(context, "No bills found", Toast.LENGTH_SHORT).show()
            }
        }

        // check loading
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {

            } else {

            }
        }

        binding.fabAddBill.setOnClickListener {
            findNavController().navigate(R.id.action_bills_to_addBill)
        }
    }

    override fun onResume() {
        super.onResume()
        val userId = SessionManager(requireContext()).getUserId()
        Log.d("DEBUG_BILLS", "OnResume - UserID: $userId")

        if (userId != -1) {
            viewModel.loadBills(userId)
        } else {
            Toast.makeText(context, "User ID lost. Please Login again.", Toast.LENGTH_LONG).show()
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
                    2 -> {
                        viewModel.deleteBill(bill.id, userId)
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .show()
    }
}