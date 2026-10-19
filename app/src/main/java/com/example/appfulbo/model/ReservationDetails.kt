package com.example.appfulbo.model

data class ReservationDetails (
    var reserva: Reserve = Reserve(),
    var user: User = User(),
    var soccerField: SoccerField = SoccerField(),
)