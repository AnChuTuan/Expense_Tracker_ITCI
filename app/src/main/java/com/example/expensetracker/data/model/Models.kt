package com.example.expensetracker.data.model

// Data Models
data class User(
    val id: Int,
    val email: String,
    val full_name: String
)

data class Expense(
    val id: Int,
    val user_id: Int,
    val title: String,
    val amount: Double,
    val date: String,
    val type: String
)

data class Bill(
    val id: Int,
    val user_id: Int,
    val title: String,
    val amount: Double,
    val due_date: String,
    val status: String
)

// API Responses
data class LoginResponse(
    val status: String,
    val message: String,
    val user: User?
)

data class ExpenseResponse(
    val status: String,
    val data: List<Expense>?
)

data class BaseResponse(
    val status: String,
    val message: String
)

data class BillResponse(
    val status: String,
    val data: List<Bill>?
)