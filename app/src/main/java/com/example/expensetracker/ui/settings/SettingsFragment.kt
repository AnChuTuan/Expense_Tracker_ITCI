package com.example.expensetracker.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentSettingsBinding
import com.example.expensetracker.ui.MainActivity
import com.example.expensetracker.utils.SessionManager

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        val session = SessionManager(requireContext())

        // Load cài đặt hiện tại
        if (session.getCurrency() == "VND") {
            binding.rbVND.isChecked = true
        } else {
            binding.rbUSD.isChecked = true
        }

        binding.btnSaveSettings.setOnClickListener {
            val currency = if (binding.rbVND.isChecked) "VND" else "USD"
            session.saveCurrency(currency)
            Toast.makeText(context, "Saved! Go back to see changes.", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener {
            session.logout()
            // Restart App để về Login
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}