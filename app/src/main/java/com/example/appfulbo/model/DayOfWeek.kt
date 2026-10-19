package com.example.appfulbo.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

data class DaysOfWeek(
    val label: String = "",
    val number: String = "",
    val month: String = "",
    val dataTime: String = ""
){
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDays(): List<DaysOfWeek> {
        val es = Locale("es")
        val hoy = LocalDate.now(ZoneId.of("America/Lima"))
        val dias = mutableListOf<DaysOfWeek>()
        val formatterDia = DateTimeFormatter.ofPattern("dd",es)
        val formatterMes = DateTimeFormatter.ofPattern("MMM",es)
        val formatterSemana = DateTimeFormatter.ofPattern("EEE",es)
        val formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy",es)
        repeat(7) { i ->
            val fecha = hoy.plusDays(i.toLong())
            val label: String = if (i == 0) "HOY" else formatterSemana.format(fecha).uppercase()
            val number: String = formatterDia.format(fecha)
            val month: String = formatterMes.format(fecha).uppercase()
            val dataTime: String = formatterData.format(fecha)
            dias.add(
                DaysOfWeek(label = label, number = number, month = month, dataTime = dataTime)
            )
        }
        return dias
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMatrizHours(place: Place?) : List<String> {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        var listHoursAux = mutableListOf<String>()

        place?.let {
            var open = LocalTime.parse(it.openingHours.open, formatter)
            val close = LocalTime.parse(it.openingHours.close, formatter)
            while (open < close) {
                listHoursAux.add(open.format(formatter))
                open = open.plusHours(1)
            }
        }
        return listHoursAux.toList()
    }
}