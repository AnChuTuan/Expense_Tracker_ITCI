package com.example.expensetracker.ui.bills

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentAddBillBinding
import com.example.expensetracker.utils.SessionManager

class AddBillFragment : Fragment(R.layout.fragment_add_bill) {
    private lateinit var binding: FragmentAddBillBinding
    private val viewModel: AddBillViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBillBinding.bind(view)

        val userId = SessionManager(requireContext()).getUserId()

        binding.btnSaveBill.setOnClickListener {
            val title = binding.etBillTitle.text.toString()
            val amount = binding.etBillAmount.text.toString().toDoubleOrNull() ?: 0.0
            val date = binding.etBillDate.text.toString()

            if (title.isNotEmpty()) {
                viewModel.addBill(userId, title, amount, date)
            }
        }

        viewModel.success.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(context, "Bill Added!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack() // Quay lại danh sách bill
            } else {
                Toast.makeText(context, "Error saving bill", Toast.LENGTH_SHORT).show()
            }
        }
    }
}