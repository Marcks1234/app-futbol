package com.example.appfulbo.data.remote.mercadoPagoService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
private val URL: String = "https://us-central1-app-fulbo-fd292.cloudfunctions.net/"
    val api: MercadoPagoApi by lazy {

        Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MercadoPagoApi::class.java)

    }
}