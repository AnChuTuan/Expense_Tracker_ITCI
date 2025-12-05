package com.example.expensetracker.ui.bills

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentBillsBinding
import com.example.expensetracker.utils.SessionManager

class BillsFragment : Fragment(R.layout.fragment_bills) {
    private lateinit var binding: FragmentBillsBinding
    private val viewModel: BillsViewModel by viewModels()
    private val adapter = BillAdapter()

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
            // Chuyển sang màn hình thêm Bill (Sẽ làm ở phần 3)
            findNavController().navigate(R.id.action_bills_to_addBill)
        }
    }
}