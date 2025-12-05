package com.example.expensetracker.data.api

import com.example.expensetracker.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register.php")
    suspend fun register(
        @Field("email") e: String,
        @Field("password") p: String,
        @Field("name") n: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("email") e: String,
        @Field("password") p: String
    ): Response<LoginResponse>

    @GET("get_expenses.php")
    suspend fun getExpenses(@Query("user_id") uid: Int): Response<ExpenseResponse>

    @FormUrlEncoded
    @POST("add_expense.php")
    suspend fun addExpense(
        @Field("user_id") uid: Int,
        @Field("title") t: String,
        @Field("amount") a: Double,
        @Field("date") d: String,
        @Field("type") type: String
    ): Response<BaseResponse>

    @GET("get_bills.php")
    suspend fun getBills(@Query("user_id") uid: Int): Response<BillResponse>

    @FormUrlEncoded
    @POST("add_bill.php")
    suspend fun addBill(
        @Field("user_id") uid: Int,
        @Field("title") t: String,
        @Field("amount") a: Double,
        @Field("due_date") d: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("update_bill_status.php")
    suspend fun updateBillStatus(@Field("id") id: Int, @Field("status") status: String): Response<BaseResponse>

    @FormUrlEncoded
    @POST("delete_bill.php")
    suspend fun deleteBill(@Field("id") id: Int): Response<BaseResponse>
}