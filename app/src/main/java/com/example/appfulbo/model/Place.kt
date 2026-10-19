package com.example.appfulbo.model

data class Place(
    val id: String = "",
    val adminId: String = "",
    val name: String = "",
    val numberOfFields: Int = 0,
    val fieldType: List<String> = emptyList(),
    val location: Location = Location(),
    val openingHours: OpeningHours = OpeningHours(),
    val imageURL: String = ""
)

data class Location(
    val address: String ="",
    val latitude: Double = 0.00,
    val longitude: Double = 0.00
)

data class OpeningHours(
    val open: String = "",
    val close: String = ""
)