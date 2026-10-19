package com.example.appfulbo.model

data class SoccerField(
    var placeId: String = "",
    val id: String = "",
    val name: String = "",
    val priceHour: Double = 0.0,
    val size: FieldSize = FieldSize(),
    val goalSize: GoalSize = GoalSize(),
    val type: String = "",
    val maxPlayers: Int = 0,
    val available: Boolean = true,
    val imageURL: String = "https://elregionalpiura.com.pe/images/Fotografias/2015/Mayo_2015/canchas_depor.png",
)

data class FieldSize(
    val length: Double = 0.0,
    val width: Double = 0.0
)

data class GoalSize(
    val height: Double = 0.0,
    val width: Double = 0.0
)