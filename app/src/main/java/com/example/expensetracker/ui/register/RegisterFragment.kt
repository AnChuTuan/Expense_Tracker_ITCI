package com.example.expensetracker.ui.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        // Handle Sign Up Button
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && pass.length >= 6) {
                viewModel.register(name, email, pass)
            } else {
                Toast.makeText(context, "Please fill all fields (Pass > 6 chars)", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle "Already have account" link
        binding.tvLoginLink.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }

        // Observer result
        viewModel.registerStatus.observe(viewLifecycleOwner) { status ->
            if (status == "Success") {
                Toast.makeText(context, "Registration Successful! Please Login.", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_register_to_login)
            } else if (status != null) {
                Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
            }
        }
    }
}