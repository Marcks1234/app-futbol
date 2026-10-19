package com.example.appfulbo.ui.screens.admin.createReserve

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appfulbo.model.DaysOfWeek
import com.example.appfulbo.model.Reserve
import com.example.appfulbo.model.SoccerField
import com.example.appfulbo.model.User
import com.example.appfulbo.ui.components.ButtonHour
import com.example.appfulbo.ui.components.CardReserveDetail
import com.example.appfulbo.ui.components.DayCarousel
import com.example.appfulbo.ui.components.TextFieldEdit
import com.example.appfulbo.ui.theme.PrimaryColor
import com.example.appfulbo.ui.theme.PrimaryColorDisable
import com.example.appfulbo.utils.sortHours

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateReserveScreen(
    viewModel: CreateReserveViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val listReserve by viewModel.listReserve.collectAsState()
    val listHours by viewModel.listHours.collectAsState()
    val success by viewModel.reserveSuccess.collectAsState()
    val error by viewModel.errorMessage.collectAsState()
    val context = LocalContext.current

    val reservedHours = remember(listReserve) { listReserve.flatMap { it.hours }.toSet() }
    var selectedHours by remember { mutableStateOf(setOf<String>()) }

    val users by viewModel.userResults.collectAsState()
    val soccerFields by viewModel.soccerFieldsResult.collectAsState()

    var userName by remember { mutableStateOf("") }
    var soccerName by remember { mutableStateOf("") }
    var userExist by remember { mutableStateOf(true) }
    var dni by remember { mutableStateOf("") }

    val days = remember { DaysOfWeek().getDays() }
    var select by remember { mutableStateOf(0) }
    var selectDay by remember { mutableStateOf(days[0]) }

    var userId by remember { mutableStateOf("") }
    var total by remember { mutableDoubleStateOf(0.00) }
    var selectedSoccerField by remember { mutableStateOf<SoccerField?>(null) }

    var showUsers by remember { mutableStateOf(false) }
    var showSoccerFields by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { viewModel.loadAllUsersOnce() }

    LaunchedEffect(selectedSoccerField?.id, selectDay) {
        selectedSoccerField?.id?.let {
            viewModel.getListReserve(it, selectDay.dataTime)
            selectedHours = emptySet()
        }
    }

    LaunchedEffect(success) {
        if (success) {
            Toast.makeText(context, "¡Reserva registrada con éxito!", Toast.LENGTH_SHORT).show()
            userName = ""
            soccerName = ""
            dni = ""
            userId = ""
            selectedSoccerField = null
            selectedHours = emptySet()
            onBack()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
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
                        "Crear Reserva Manual",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor
                    )
                    Text(
                        "Registra reservas para usuarios o clientes presenciales",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        item {
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
                    UserSection(userExist, onToggle = { userExist = it })

                    if (userExist) {
                        SearchField(
                            value = userName,
                            placeholder = "Buscar usuario registrado",
                            onValueChange = {
                                userName = it
                                viewModel.filterUsers(it)
                                showUsers = true
                            },
                            onFocus = {
                                if (it) {
                                    showUsers = true
                                    viewModel.filterUsers(userName)
                                }
                            },
                            onClean = {
                                showUsers = false
                                userName = ""
                                userId = ""
                            }
                        )
                        if (showUsers && users.isNotEmpty()) {
                            ListItems(
                                items = users,
                                saveId = { userId = it },
                                saveName = { userName = it },
                                changeShow = { showUsers = it }
                            )
                        }
                    } else {
                        OutlinedTextField(
                            value = dni,
                            onValueChange = { if (it.length <= 8 && it.all { c -> c.isDigit() }) dni = it },
                            placeholder = { Text("Ingresar DNI del cliente (8 dígitos)") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                    }
                }
            }
        }

        item {
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
                    Text("Selección de Cancha", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    SearchField(
                        value = soccerName,
                        placeholder = "Buscar cancha disponible",
                        onValueChange = {
                            soccerName = it
                            viewModel.filterSoccerFields(it)
                            showSoccerFields = true
                        },
                        onFocus = {
                            if (it) {
                                showSoccerFields = true
                                viewModel.filterSoccerFields(soccerName)
                            }
                        },
                        onClean = {
                            showSoccerFields = false
                            soccerName = ""
                            selectedSoccerField = null
                            selectedHours = emptySet()
                        }
                    )

                    if (showSoccerFields && soccerFields.isNotEmpty()) {
                        ListItems(
                            items = soccerFields,
                            saveId = { fieldId -> selectedSoccerField = soccerFields.find { it.id == fieldId } },
                            saveName = { soccerName = it },
                            changeShow = { showSoccerFields = it }
                        )
                    }
                }
            }
        }

        selectedSoccerField?.let { field ->
            item {
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
                        Text("Fecha y Horario", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                        DayCarousel(
                            days = days,
                            select = select,
                            onDaySelect = { i, day ->
                                select = i
                                selectDay = day
                            }
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Horas Disponibles", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)

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
                                        selectedHours =
                                            if (value in selectedHours) selectedHours - value else selectedHours + value
                                    },
                                    selectHour = selectedHours,
                                    isReserved = isReserved
                                )
                            }
                        }
                    }
                }
            }

            item {
                total = field.priceHour * selectedHours.size
                CardReserveDetail(
                    total = total,
                    dayOfWeek = selectDay,
                    selectedHours = selectedHours
                )
            }
        }

        item {
            val canReserve = (userExist && userId.isNotBlank() || !userExist && dni.length == 8) &&
                    selectedSoccerField != null && selectedHours.isNotEmpty()
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryColor,
                        disabledContainerColor = PrimaryColorDisable
                    ),
                    onClick = {
                        val reserve = Reserve(
                            adminId = viewModel.getCurrentId(),
                            userId = if (userExist) userId else "",
                            soccerFieldId = selectedSoccerField?.id ?: "",
                            placeId = selectedSoccerField?.placeId ?: "",
                            date = selectDay.dataTime,
                            hours = sortHours(selectedHours.toList()),
                            total = total,
                            createAt = System.currentTimeMillis(),
                            status = "pagado",
                            dni = if (!userExist) dni else null
                        )
                        viewModel.addReserve(reserve)
                    },
                    enabled = canReserve
                ) {
                    Text("Confirmar y Reservar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = onBack
                ) {
                    Text("Cancelar", fontSize = 15.sp)
                }
            }

            if (error != null) {
                Text("Error: $error", color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, borderColor: Color? = null, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, CircleShape)
                .then(
                    if (borderColor != null) Modifier.padding(1.dp)
                    else Modifier
                )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, fontSize = 11.sp, color = Color.Gray)
    }
}

@Composable
fun UserSection(userExist: Boolean, onToggle: (Boolean) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Tipo de Cliente", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("¿Usuario registrado?", fontSize = 15.sp)
            Spacer(Modifier.weight(1f))
            Text("Sí")
            RadioButton(selected = userExist, onClick = { onToggle(true) })
            Spacer(Modifier.width(8.dp))
            Text("No")
            RadioButton(selected = !userExist, onClick = { onToggle(false) })
        }
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
    }
}

@Composable
fun SearchField(
    value: String, placeholder: String, onValueChange: (String) -> Unit,
    onFocus: (Boolean) -> Unit, onClean: () -> Unit,
) {
    TextFieldEdit(
        trailingIcon = { IconButton(onClick = { onClean() }) { Icon(Icons.Default.Clear, null) } },
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { onFocus(it.isFocused) }
    )
}

@Composable
fun <T> ListItems(
    items: List<T>, modifier: Modifier = Modifier,
    saveId: (String) -> Unit, saveName: (String) -> Unit, changeShow: (Boolean) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 180.dp)
            .padding(vertical = 4.dp)
    ) {
        items(items) { item ->
            val (id, name) = when (item) {
                is SoccerField -> item.id to item.name
                is User -> item.id to item.userName
                else -> "" to ""
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        saveName(name)
                        saveId(id)
                        changeShow(false)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (item is SoccerField) Icons.Default.SportsSoccer else Icons.Default.Person,
                    contentDescription = null,
                    tint = PrimaryColor,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = name,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(12.dp)
                )
            }
        }
    }
}
