package com.example.expensetracker.ui.add_expense

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentAddExpenseBinding
import com.example.expensetracker.utils.SessionManager

class AddExpenseFragment : Fragment(R.layout.fragment_add_expense) {
    private lateinit var binding: FragmentAddExpenseBinding
    private val viewModel: AddExpenseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddExpenseBinding.bind(view)
        val uid = SessionManager(requireContext()).getUserId()

        val userId = SessionManager(requireContext()).getUserId()

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val amount = binding.etAmount.text.toString().toDoubleOrNull() ?: 0.0
            val date = binding.etDate.text.toString()
            val type = if (binding.rbIncome.isChecked) "INCOME" else "EXPENSE"

            if(title.isNotEmpty()) {
                viewModel.add(userId, title, amount, date, type)
            }
        }

        viewModel.success.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }
}