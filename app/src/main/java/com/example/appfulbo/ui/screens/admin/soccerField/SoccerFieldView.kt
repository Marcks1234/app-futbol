package com.example.appfulbo.ui.screens.admin.soccerField

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appfulbo.ui.components.AdminCardSoccerField
import com.example.appfulbo.ui.components.EditSoccerFieldDialog
import com.example.appfulbo.ui.states.StateSoccerField
import com.example.appfulbo.ui.theme.PrimaryColor

@Composable
fun SoccerFieldAdminView(viewModel: SoccerFieldViewModel) {
    val soccerFields by viewModel.soccerFields.collectAsState()
    val state by viewModel.stateSoccerField.collectAsState()
    var showEditDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getSoccerFields()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            StateSoccerField.Empty -> {}
            StateSoccerField.Loading -> {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(color = PrimaryColor)
                }
            }

            is StateSoccerField.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Mis Canchas",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryColor
                            )
                            Text(
                                "Administra las canchas registradas",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }

                        Surface(
                            color = PrimaryColor.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "${soccerFields.size} Canchas",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryColor,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (soccerFields.isEmpty()) {
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
                                    imageVector = Icons.Default.SportsSoccer,
                                    contentDescription = null,
                                    tint = PrimaryColor,
                                    modifier = Modifier
                                        .height(64.dp)
                                        .width(64.dp)
                                )
                                Text(
                                    "Aún no tienes canchas registradas",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray
                                )
                                Text(
                                    "Ve a la pestaña de 'Registrar Cancha' en el menú de navegación para publicar tu primera cancha.",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(soccerFields) { soccerField ->
                                AdminCardSoccerField(
                                    soccerField = soccerField,
                                    textColor = Color.White,
                                    onClick = {
                                        Log.i("id Cancha", soccerField.id)
                                        viewModel.selectSoccerField(soccerField)
                                        showEditDialog = true
                                    }
                                )
                            }
                        }
                    }
                }

                if (showEditDialog) {
                    val field = viewModel.selectedSoccerField.collectAsState().value
                    if (field != null) {
                        EditSoccerFieldDialog(
                            soccerField = field,
                            onDismiss = { showEditDialog = false },
                            onSave = { updatedField ->
                                viewModel.updateSoccerField(updatedField)
                                showEditDialog = false
                            }
                        )
                    }
                }
            }

            is StateSoccerField.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error al cargar las canchas",
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
