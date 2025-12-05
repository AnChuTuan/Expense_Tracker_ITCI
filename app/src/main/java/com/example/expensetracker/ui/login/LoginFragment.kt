package com.example.expensetracker.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentLoginBinding
import com.example.expensetracker.utils.SessionManager

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty()) {
                viewModel.login(email, pass)
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { res ->
            if (res != null && res.status == "success") {
                SessionManager(requireContext()).saveUser(res.user!!.id, res.user.full_name)
                // Navigate to Home
                findNavController().navigate(R.id.action_login_to_home)
            } else {
                Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            // This ID must match the arrow in your nav_graph.xml
            findNavController().navigate(R.id.action_login_to_register)
        }
    }
}