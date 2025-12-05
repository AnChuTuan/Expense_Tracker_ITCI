package com.example.expensetracker.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // 10.0.2.2 là localhost của máy tính khi chạy trên Emulator
    private const val BASE_URL = "http://10.0.2.2/expense_api/"

    // 1. Cấu hình OkHttpClient để sửa lỗi "unexpected end of stream"
    private val client = OkHttpClient.Builder()
        .retryOnConnectionFailure(true) // Tự động thử lại nếu rớt mạng
        .connectTimeout(15, TimeUnit.SECONDS) // Tăng thời gian chờ
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            // Thêm Header "Connection: close" để tránh lỗi stream bị ngắt
            val request = chain.request().newBuilder()
                .header("Connection", "close")
                .build()
            chain.proceed(request)
        }
        .build()

    // 2. Cấu hình Gson ở chế độ Lenient (dễ tính) để tránh lỗi JSON format
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // <-- QUAN TRỌNG: Gắn client đã cấu hình vào đây
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}