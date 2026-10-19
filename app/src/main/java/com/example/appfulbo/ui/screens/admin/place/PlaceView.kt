package com.example.appfulbo.ui.screens.admin.place

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Room
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.appfulbo.model.Location
import com.example.appfulbo.model.OpeningHours
import com.example.appfulbo.model.Place
import com.example.appfulbo.ui.screens.admin.profile.TextEdit
import com.example.appfulbo.ui.states.GeneralState
import com.example.appfulbo.ui.theme.PrimaryColor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Locale

@Composable
fun PlaceAdminView(viewModel: PlaceViewModel = hiltViewModel()) {
    val place by viewModel.place.collectAsState()
    val saveState by viewModel.saveState.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit) {
        viewModel.getPlace()
    }

    LaunchedEffect(place.location) {
        if (place.location.longitude != 0.0 && place.location.latitude != 0.0) {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                LatLng(place.location.latitude, place.location.longitude),
                15f
            )
        }
    }

    LaunchedEffect(saveState) {
        when (saveState) {
            is GeneralState.Success -> {
                Toast.makeText(context, "¡Local guardado correctamente!", Toast.LENGTH_SHORT).show()
                showDialog = false
                viewModel.resetSaveState()
            }
            is GeneralState.Error -> {
                val errorMsg = (saveState as GeneralState.Error).error
                Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                viewModel.resetSaveState()
            }
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Mi Establecimiento",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor
            )

            if (place.name.isEmpty()) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = null,
                            tint = PrimaryColor,
                            modifier = Modifier
                                .height(64.dp)
                                .width(64.dp)
                        )
                        Text(
                            "Aún no has registrado tu local",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                        Text(
                            "Agrega la información de tu establecimiento para que los usuarios puedan encontrarlo y reservar sus canchas.",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Button(
                            onClick = { showDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Registrar Local")
                        }
                    }
                }
            } else {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (place.imageURL.isNotEmpty()) {
                            AsyncImage(
                                model = place.imageURL,
                                contentDescription = "Imagen del local",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        TextEdit(icon = Icons.Default.Room, field = "Nombre del local", value = place.name)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        TextEdit(icon = Icons.Default.Room, field = "Dirección", value = place.location.address)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        TextEdit(
                            icon = Icons.Default.Timer,
                            field = "Horario de Atención",
                            value = if (place.openingHours.open.isNotEmpty() && place.openingHours.close.isNotEmpty())
                                "${place.openingHours.open} - ${place.openingHours.close}"
                            else "No especificado"
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState
                    ) {
                        if (place.location.latitude != 0.0 && place.location.longitude != 0.0) {
                            Marker(
                                title = place.name,
                                state = MarkerState(
                                    position = LatLng(place.location.latitude, place.location.longitude)
                                )
                            )
                        }
                    }
                }
            }
        }

        if (place.name.isNotEmpty()) {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = PrimaryColor,
                contentColor = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Editar Local")
            }
        }
    }

    if (showDialog) {
        AddEditPlaceDialog(
            currentPlace = place,
            isLoading = saveState is GeneralState.Loading,
            onDismiss = { showDialog = false },
            onSave = { updatedPlace ->
                viewModel.savePlace(updatedPlace)
            }
        )
    }
}

@Composable
fun AddEditPlaceDialog(
    currentPlace: Place,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onSave: (Place) -> Unit
) {
    var name by remember { mutableStateOf(currentPlace.name) }
    var address by remember { mutableStateOf(currentPlace.location.address) }

    val initialLat = if (currentPlace.location.latitude != 0.0) currentPlace.location.latitude else -12.046374
    val initialLng = if (currentPlace.location.longitude != 0.0) currentPlace.location.longitude else -77.042793

    var selectedLatLng by remember { mutableStateOf(LatLng(initialLat, initialLng)) }

    var latitude by remember { mutableStateOf(String.format(Locale.US, "%.6f", selectedLatLng.latitude)) }
    var longitude by remember { mutableStateOf(String.format(Locale.US, "%.6f", selectedLatLng.longitude)) }

    var openHour by remember { mutableStateOf(currentPlace.openingHours.open) }
    var closeHour by remember { mutableStateOf(currentPlace.openingHours.close) }
    var imageUrl by remember { mutableStateOf(currentPlace.imageURL) }

    val dialogCameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(selectedLatLng, 15f)
    }

    AlertDialog(
        onDismissRequest = { if (!isLoading) onDismiss() },
        title = {
            Text(
                if (currentPlace.name.isEmpty()) "Registrar Local" else "Editar Local",
                fontWeight = FontWeight.Bold
            )
        },
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
                    label = { Text("Nombre del Local") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    "Ubicación en el Mapa",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = PrimaryColor,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    "Toca el mapa para colocar el marcador de tu establecimiento:",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = dialogCameraPositionState,
                        uiSettings = MapUiSettings(
                            zoomControlsEnabled = true,
                            myLocationButtonEnabled = false
                        ),
                        onMapClick = { latLng ->
                            selectedLatLng = latLng
                            latitude = String.format(Locale.US, "%.6f", latLng.latitude)
                            longitude = String.format(Locale.US, "%.6f", latLng.longitude)
                        }
                    ) {
                        Marker(
                            state = remember(selectedLatLng) { MarkerState(position = selectedLatLng) },
                            title = name.ifEmpty { "Ubicación del Local" },
                            snippet = address
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = latitude,
                        onValueChange = { newLat ->
                            latitude = newLat
                            newLat.toDoubleOrNull()?.let { latVal ->
                                selectedLatLng = LatLng(latVal, selectedLatLng.longitude)
                                dialogCameraPositionState.position = CameraPosition.fromLatLngZoom(selectedLatLng, 15f)
                            }
                        },
                        label = { Text("Latitud") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = longitude,
                        onValueChange = { newLng ->
                            longitude = newLng
                            newLng.toDoubleOrNull()?.let { lngVal ->
                                selectedLatLng = LatLng(selectedLatLng.latitude, lngVal)
                                dialogCameraPositionState.position = CameraPosition.fromLatLngZoom(selectedLatLng, 15f)
                            }
                        },
                        label = { Text("Longitud") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = openHour,
                        onValueChange = { openHour = it },
                        label = { Text("Apertura") },
                        placeholder = { Text("08:00") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = closeHour,
                        onValueChange = { closeHour = it },
                        label = { Text("Cierre") },
                        placeholder = { Text("23:00") },
                        modifier = Modifier.weight(1f)
                    )
                }
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("URL de la Imagen del Local") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val lat = latitude.toDoubleOrNull() ?: selectedLatLng.latitude
                    val lng = longitude.toDoubleOrNull() ?: selectedLatLng.longitude

                    val updatedPlace = currentPlace.copy(
                        name = name,
                        location = Location(address = address, latitude = lat, longitude = lng),
                        openingHours = OpeningHours(open = openHour, close = closeHour),
                        imageURL = imageUrl
                    )
                    onSave(updatedPlace)
                },
                enabled = name.isNotBlank() && !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.height(20.dp).width(20.dp))
                } else {
                    Text("Guardar")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !isLoading) {
                Text("Cancelar")
            }
        }
    )
}
