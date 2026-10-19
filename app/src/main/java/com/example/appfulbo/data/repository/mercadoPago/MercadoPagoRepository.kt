package com.example.appfulbo.data.repository.mercadoPago

import com.example.appfulbo.data.remote.mercadoPagoService.RetrofitInstance
import com.example.appfulbo.model.PreferenceRequest
import com.example.appfulbo.model.PreferenceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MercadoPagoRepository {
    fun generarPreferencia(
        request: PreferenceRequest,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        RetrofitInstance.api.createPreference(request).enqueue(object :
            Callback<PreferenceResponse> {
            override fun onResponse(
                call: Call<PreferenceResponse>,
                response: Response<PreferenceResponse>
            ) {
                if (response.isSuccessful) {
                    val initPoint = response.body()?.init_point
                    if (initPoint != null) {
                        onSuccess(initPoint)
                    } else {
                        onError("Respuesta sin init_point")
                    }
                } else {
                    onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PreferenceResponse>, t: Throwable) {
                onError("Fallo: ${t.message}")
            }
        })
    }
}