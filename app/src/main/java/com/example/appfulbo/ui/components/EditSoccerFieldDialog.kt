package com.example.appfulbo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appfulbo.model.FieldSize
import com.example.appfulbo.model.GoalSize
import com.example.appfulbo.model.SoccerField
import com.example.appfulbo.ui.theme.PrimaryColor

@Composable
fun EditSoccerFieldDialog(
    soccerField: SoccerField,
    onDismiss: () -> Unit,
    onSave: (SoccerField) -> Unit
) {
    var name by remember { mutableStateOf(soccerField.name) }
    var price by remember { mutableStateOf(soccerField.priceHour.toString()) }
    var maxPlayers by remember { mutableStateOf(soccerField.maxPlayers.toString()) }
    var type by remember { mutableStateOf(if (soccerField.type.isNotEmpty()) soccerField.type else "Sintético") }
    var expandedType by remember { mutableStateOf(false) }

    var sizeLength by remember { mutableStateOf(soccerField.size.length.toString()) }
    var sizeWidth by remember { mutableStateOf(soccerField.size.width.toString()) }

    var goalHeight by remember { mutableStateOf(soccerField.goalSize.height.toString()) }
    var goalWidth by remember { mutableStateOf(soccerField.goalSize.width.toString()) }

    val typesList = listOf("Sintético", "Natural", "Mixto", "Losa / Concreto")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Cancha", fontWeight = FontWeight.Bold) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre de la Cancha") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Precio / Hora (S/)") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = maxPlayers,
                        onValueChange = { maxPlayers = it },
                        label = { Text("Max. Jugadores") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Tipo de Terreno:", fontWeight = FontWeight.SemiBold)
                    OutlinedButton(onClick = { expandedType = true }) {
                        Text(type)
                    }
                    DropdownMenu(
                        expanded = expandedType,
                        onDismissRequest = { expandedType = false }
                    ) {
                        typesList.forEach { selectedType ->
                            DropdownMenuItem(
                                text = { Text(selectedType) },
                                onClick = {
                                    type = selectedType
                                    expandedType = false
                                }
                            )
                        }
                    }
                }

                Text("Dimensiones de la Cancha (Largo x Ancho):", fontWeight = FontWeight.SemiBold)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = sizeLength,
                        onValueChange = { sizeLength = it },
                        label = { Text("Largo (m)") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = sizeWidth,
                        onValueChange = { sizeWidth = it },
                        label = { Text("Ancho (m)") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Text("Dimensiones del Arco (Alto x Ancho):", fontWeight = FontWeight.SemiBold)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = goalHeight,
                        onValueChange = { goalHeight = it },
                        label = { Text("Alto (m)") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = goalWidth,
                        onValueChange = { goalWidth = it },
                        label = { Text("Ancho (m)") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        soccerField.copy(
                            name = name,
                            priceHour = price.toDoubleOrNull() ?: soccerField.priceHour,
                            maxPlayers = maxPlayers.toIntOrNull() ?: soccerField.maxPlayers,
                            type = type,
                            size = FieldSize(
                                length = sizeLength.toDoubleOrNull() ?: soccerField.size.length,
                                width = sizeWidth.toDoubleOrNull() ?: soccerField.size.width
                            ),
                            goalSize = GoalSize(
                                height = goalHeight.toDoubleOrNull() ?: soccerField.goalSize.height,
                                width = goalWidth.toDoubleOrNull() ?: soccerField.goalSize.width
                            )
                        )
                    )
                },
                enabled = name.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
            ) {
                Text("Guardar Cambios")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
