package com.example.appfulbo.model

data class Reserve(
    val adminId: String = "",
    val createAt: Long = System.currentTimeMillis(),
    val date: String = "",
    val hours: List<String> = emptyList(),
    val placeId: String = "",
    val soccerFieldId: String = "",
    val status: String = "pendiente",
    val total: Double = 0.0,
    val userId: String = "",
    val dni: String? = null
)