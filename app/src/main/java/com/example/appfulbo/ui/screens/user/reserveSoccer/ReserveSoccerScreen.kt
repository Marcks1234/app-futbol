package com.example.appfulbo.ui.screens.user.reserveSoccer

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appfulbo.data.repository.mercadoPago.MercadoPagoRepository
import com.example.appfulbo.model.DaysOfWeek
import com.example.appfulbo.model.PreferenceRequest
import com.example.appfulbo.ui.components.ButtonHour
import com.example.appfulbo.ui.components.CardReserveDetail
import com.example.appfulbo.ui.components.DayCarousel
import com.example.appfulbo.ui.screens.admin.createReserve.LegendItem
import com.example.appfulbo.ui.theme.PrimaryColor
import com.example.appfulbo.ui.theme.PrimaryColorDisable

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReserveSoccerScreen(
    soccerFieldId: String,
    placeId: String,
    onBack: () -> Unit = {},
    viewModel: ReserveSoccerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val listReserve by viewModel.listReserve.collectAsState()
    val listHours by viewModel.listHours.collectAsState()
    val soccerField by viewModel.soccerField.collectAsState()
    val place by viewModel.place.collectAsState()
    val userId = viewModel.UserId

    val repo = MercadoPagoRepository()

    val days = remember { DaysOfWeek().getDays() }
    var dayOfWeek by remember { mutableStateOf(days[0]) }

    var total by remember { mutableDoubleStateOf(0.00) }
    var select by remember { mutableStateOf(0) }
    var selectedHours by remember { mutableStateOf(setOf<String>()) }
    val reservedHours = remember(listReserve) { listReserve.flatMap { it.hours }.toSet() }

    LaunchedEffect(placeId, soccerFieldId) {
        viewModel.getPlace(placeId, soccerFieldId)
    }

    LaunchedEffect(dayOfWeek) {
        viewModel.getListReserve(soccerFieldId, dayOfWeek.dataTime)
        selectedHours = emptySet()
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = PrimaryColor
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = soccerField?.name ?: "Reservar Cancha",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )
                Text(
                    text = place?.name ?: "Selecciona el día y horas a reservar",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Selecciona la Fecha", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                DayCarousel(days = days, select = select, onDaySelect = { i, day ->
                    dayOfWeek = day
                    select = i
                })

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Horarios Disponibles", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LegendItem(color = PrimaryColor, label = "Elegido")
                        LegendItem(color = Color.White, borderColor = PrimaryColor, label = "Libre")
                        LegendItem(color = Color(0xFFEEEEEE), label = "Ocupado")
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 280.dp),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listHours) { hour ->
                        val isReserved = hour in reservedHours
                        ButtonHour(
                            value = hour,
                            onToggleHour = { value ->
                                selectedHours = if (value in selectedHours) {
                                    selectedHours - value
                                } else {
                                    selectedHours + value
                                }
                            },
                            selectHour = selectedHours,
                            isReserved = isReserved
                        )
                    }
                }

                soccerField?.let {
                    Text(
                        text = "Precio por hora: S/ ${"%.2f".format(it.priceHour)}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor
                    )
                }
            }
        }

        soccerField?.let {
            total = it.priceHour * selectedHours.size
            CardReserveDetail(total = total, selectedHours = selectedHours, dayOfWeek = dayOfWeek)
        }

        val request = PreferenceRequest(
            title = "Reserva de cancha - ${soccerField?.name ?: ""}",
            quantity = 1,
            unit_price = total,
            metadata = PreferenceRequest.Metadata(
                adminId = place?.adminId ?: "",
                userId = userId,
                soccerFieldId = soccerFieldId,
                date = dayOfWeek.dataTime,
                hours = selectedHours,
                total = total,
                placeId = soccerField?.placeId ?: ""
            )
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                disabledContainerColor = PrimaryColorDisable
            ),
            enabled = selectedHours.isNotEmpty(),
            onClick = {
                repo.generarPreferencia(
                    request,
                    onSuccess = { url ->
                        openMercadoPagoCustomTab(context, url)
                    },
                    onError = { error ->
                        Log.e("MP", error)
                    }
                )
            }
        ) {
            Text("Pagar y Confirmar Reserva", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

fun openMercadoPagoCustomTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, url.toUri())
}
