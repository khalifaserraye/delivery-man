package com.example.projettdm

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val endPoint: EndPoint by lazy {
        Retrofit.Builder().baseUrl("http://192.168.43.245:8091/")
                .addConverterFactory(GsonConverterFactory
                        .create()).build().create(EndPoint::class.java)
    }
}
