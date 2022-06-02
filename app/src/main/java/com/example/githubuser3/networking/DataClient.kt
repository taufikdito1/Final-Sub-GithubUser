package com.example.githubuser3.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataClient {
    private const val BASE_URL = "https://api.github.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstance = retrofit.create(ApiService::class.java)
}