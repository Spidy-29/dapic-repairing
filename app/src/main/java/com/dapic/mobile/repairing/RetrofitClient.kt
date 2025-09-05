package com.dapic.mobile.repairing

import com.dapic.mobile.repairing.data.GoogleSheetApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://script.google.com/macros/s/AKfycbydCtj0ab25O-fbq56y49HXtniti18jAYtxYm1Jlqez0tHgvGHjNbJAo-MEBGc4oKOj/" // Replace with your script URL

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