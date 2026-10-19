package com.example.appfulbo.data.remote.mercadoPagoService

import com.example.appfulbo.model.PreferenceRequest
import com.example.appfulbo.model.PreferenceResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MercadoPagoApi {
    @POST("createPreference")
    fun createPreference(@Body request: PreferenceRequest): Call<PreferenceResponse>
}
