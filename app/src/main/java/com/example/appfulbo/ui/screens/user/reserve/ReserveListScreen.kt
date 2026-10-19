package com.example.appfulbo.ui.screens.user.reserve

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appfulbo.ui.components.UserCardReserve
import com.example.appfulbo.utils.isDatePast
import com.example.appfulbo.ui.screens.admin.reserve.ReserveListViewModel
import com.example.appfulbo.ui.theme.PrimaryColor
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReserveListScreen(viewModel: ReserveListViewModel = hiltViewModel()) {
    val reserves by viewModel.detailReserves.collectAsState()
    val formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("es", "PE"))
    val currentDate = Instant.now().atZone(ZoneId.of("America/Lima")).format(formatterDate)

    var searchQuery by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var showDate by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val filtered = remember(reserves, searchQuery, selectedDate) {
        viewModel.filterReserves(searchQuery, selectedDate)
    }

    LaunchedEffect(Unit) {
        viewModel.getReserveDetail()
        selectedDate = ""
    }

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            selectedDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
                .format(formatterDate)
        }
    }

    if (showDate) {
        DatePickerDialog(
            onDismissRequest = { showDate = false },
            confirmButton = {
                Button(
                    onClick = { showDate = false },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
                ) { Text("Confirmar") }
            },
            dismissButton = { OutlinedButton(onClick = { showDate = false }) { Text("Cancelar") } }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "Mis Reservas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )
                Text(
                    "Hoy es $currentDate",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Surface(
                color = PrimaryColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "${filtered.size} Reservas",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        SearchAndDateFilter(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onDateClick = { showDate = true },
            onClearDate = {
                selectedDate = ""
                datePickerState.selectedDateMillis = null
            },
            showClear = selectedDate.isNotEmpty()
        )

        if (selectedDate.isNotEmpty()) {
            Surface(
                color = Color(0xFFE3F2FD),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Filtrando por fecha: $selectedDate",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1565C0),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }

        if (filtered.isEmpty()) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.EventNote,
                        contentDescription = null,
                        tint = PrimaryColor,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = "No tienes reservas registradas",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "Explora los locales disponibles y reserva tu cancha favorita.",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(filtered) { item ->
                    val isPast = isDatePast(item.reserva.date)
                    UserCardReserve(it = item, isPast = isPast)
                }
            }
        }
    }
}

@Composable
fun SearchAndDateFilter(
    query: String,
    onQueryChange: (String) -> Unit,
    onDateClick: () -> Unit,
    onClearDate: () -> Unit,
    showClear: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text("Buscar por cancha...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = PrimaryColor) },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onDateClick) {
            Icon(Icons.Default.CalendarMonth, contentDescription = "Filtrar por Fecha", tint = PrimaryColor)
        }

        if (showClear) {
            IconButton(onClick = onClearDate) {
                Icon(Icons.Default.Clear, contentDescription = "Limpiar Fecha", tint = Color.Red)
            }
        }
    }
}
