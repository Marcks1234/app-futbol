package com.example.appfulbo.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun sortHours(horas: List<String>): List<String> {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return horas.sortedBy {
        try {
            sdf.parse(it)
        } catch (e: Exception) {
            Date()
        }
    }
}

fun isDatePast(dateStr: String): Boolean {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val today = sdf.parse(sdf.format(Date()))
        val date = sdf.parse(dateStr)
        date?.before(today) ?: false
    } catch (e: Exception) {
        false
    }
}
