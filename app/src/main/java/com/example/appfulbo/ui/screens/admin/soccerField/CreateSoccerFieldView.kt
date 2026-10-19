package com.example.appfulbo.ui.screens.admin.soccerField

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appfulbo.ui.components.TextFieldEdit
import com.example.appfulbo.ui.components.TypeText
import com.example.appfulbo.ui.states.StateSoccerField
import com.example.appfulbo.ui.theme.PrimaryColor
import com.example.appfulbo.ui.theme.SecondaryColor

val types = listOf("Sintético", "Natural", "Mixto", "Losa / Concreto")

@Composable
fun CreateSoccerFieldAdminView(
    viewModel: SoccerFieldViewModel,
    onBack: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val name by viewModel.name.collectAsState()
    val price by viewModel.price.collectAsState()
    val maxPlayers by viewModel.maxPlayers.collectAsState()
    val type by viewModel.type.collectAsState()
    val sizeLength by viewModel.sizeLength.collectAsState()
    val sizeWidth by viewModel.sizeWidth.collectAsState()
    val goalSizeHeight by viewModel.goalSizeHeight.collectAsState()
    val goalSizeWidth by viewModel.goalSizeWidth.collectAsState()
    val state by viewModel.stateCreateSoccerField.collectAsState()
    val errors by viewModel.errors.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        if (state is StateSoccerField.Success) {
            Toast.makeText(context, "¡Cancha registrada exitosamente!", Toast.LENGTH_SHORT).show()
            viewModel.resetForm()
            onBack()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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
                    "Registrar Cancha",
                    color = PrimaryColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Agrega los detalles de la nueva cancha",
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
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.SportsSoccer,
                        contentDescription = null,
                        tint = PrimaryColor,
                        modifier = Modifier.height(24.dp).width(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Información General",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                }

                TextFieldEdit(
                    value = name,
                    label = "Nombre de la Cancha (ej: Cancha 1)",
                    onValueChange = viewModel::changeName,
                    supportingText = supportingTextEdit("name", errors),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextFieldEdit(
                        value = price,
                        label = "Precio / Hora",
                        onValueChange = viewModel::changePrice,
                        suffix = "S/",
                        supportingText = supportingTextEdit("price", errors),
                        typeText = TypeText.DECIMAL,
                        modifier = Modifier.weight(1f)
                    )

                    TextFieldEdit(
                        value = maxPlayers,
                        label = "Max. Jugadores",
                        onValueChange = viewModel::changeMaxPlayers,
                        typeText = TypeText.NUMBER,
                        supportingText = supportingTextEdit("maxPlayers", errors),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Tipo de Terreno:", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Box {
                        OutlinedButton(
                            onClick = { expanded = !expanded },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(if (type.isEmpty()) "Seleccionar" else type, color = PrimaryColor)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            types.forEach { selectedType ->
                                DropdownMenuItem(
                                    text = { Text(selectedType) },
                                    onClick = {
                                        viewModel.changeType(selectedType)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
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
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    "Dimensiones de la Cancha (Opcional)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )

                Text("Terreno (Largo x Ancho):", fontSize = 13.sp, color = Color.Gray)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextFieldEdit(
                        value = sizeLength,
                        label = "Largo",
                        onValueChange = viewModel::changeSizeLength,
                        suffix = "m",
                        typeText = TypeText.NUMBER,
                        supportingText = supportingTextEdit("sizeLength", errors),
                        modifier = Modifier.weight(1f)
                    )
                    TextFieldEdit(
                        value = sizeWidth,
                        label = "Ancho",
                        onValueChange = viewModel::changeSizeWidth,
                        suffix = "m",
                        supportingText = supportingTextEdit("sizeWidth", errors),
                        typeText = TypeText.NUMBER,
                        modifier = Modifier.weight(1f)
                    )
                }

                Text("Arcos (Alto x Ancho):", fontSize = 13.sp, color = Color.Gray)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextFieldEdit(
                        value = goalSizeHeight,
                        label = "Alto",
                        onValueChange = viewModel::changeGoalSizeHeight,
                        suffix = "m",
                        typeText = TypeText.NUMBER,
                        supportingText = supportingTextEdit("goalSizeHeight", errors),
                        modifier = Modifier.weight(1f)
                    )
                    TextFieldEdit(
                        value = goalSizeWidth,
                        label = "Ancho",
                        typeText = TypeText.NUMBER,
                        onValueChange = viewModel::changeGoalSizeWidth,
                        suffix = "m",
                        supportingText = supportingTextEdit("goalSizeWidth", errors),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                onClick = { viewModel.createSoccerFieldIfValid() },
                enabled = state != StateSoccerField.Loading
            ) {
                if (state == StateSoccerField.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.height(20.dp).width(20.dp)
                    )
                } else {
                    Text("Registrar Cancha", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                onClick = onBack,
                enabled = state != StateSoccerField.Loading
            ) {
                Text("Cancelar", fontSize = 15.sp)
            }

            if (state is StateSoccerField.Error) {
                Text(
                    text = (state as StateSoccerField.Error).message,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }
        }
    }
}

fun supportingTextEdit(field: String, map: Map<String, String?>): (@Composable () -> Unit)? {
    val error = map[field]
    return if (error != null) {
        { Text(error, color = Color.Red, fontSize = 12.sp) }
    } else null
}
