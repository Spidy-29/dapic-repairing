package com.dapic.mobile.repairing

import com.dapic.mobile.repairing.data.GoogleSheetApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://script.google.com/macros/s/AKfycbyf-5tpiqBbNgyjDesD-sEoMOlXWBFIfx02eK7f2ZyMkyV_gjTB-rM6fGDSr90Mt4O0JA/" // Replace with your script URL

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val apiService: GoogleSheetApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleSheetApiService::class.java)
    }
}