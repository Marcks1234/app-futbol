package com.example.appfulbo.model

data class PreferenceRequest(
    val title: String,
    val quantity: Int,
    val unit_price: Double,
    val metadata: Metadata
) {
    data class Metadata(
        val placeId: String,
        val adminId: String,
        val userId: String,
        val soccerFieldId: String,
        val date: String,
        val hours: Set<String>,
        val total: Double
    )
}

data class PreferenceResponse(
    val init_point: String
)