package com.example.appfulbo.ui.screens.user.places

import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.appfulbo.ui.state.UiState
import com.example.appfulbo.ui.theme.PrimaryColor
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Locale

@Composable
fun PlacesView(
    viewModel: PlacesViewModel = hiltViewModel(),
    toSoccerFields: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val places = when (val state = uiState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }
    var query by remember { mutableStateOf("") }
    val context = LocalContext.current
    val fusedLocationProvider = remember { LocationServices.getFusedLocationProviderClient(context) }

    val permissionGranted = remember { mutableStateOf(false) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            permissionGranted.value = granted
            if (granted) {
                fusedLocationProvider.lastLocation.addOnSuccessListener { loc ->
                    loc?.let {
                        userLocation = LatLng(it.latitude, it.longitude)
                    }
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.getDataPlaces()
        permissionGranted.value = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (permissionGranted.value) {
            fusedLocationProvider.lastLocation.addOnSuccessListener { loc ->
                loc?.let { userLocation = LatLng(it.latitude, it.longitude) }
            }
        }
    }

    val sortedPlaces = remember(places, userLocation) {
        if (userLocation != null) {
            places.sortedBy {
                val result = FloatArray(1)
                Location.distanceBetween(
                    userLocation!!.latitude, userLocation!!.longitude,
                    it.location.latitude, it.location.longitude,
                    result
                )
                result[0]
            }
        } else {
            places
        }
    }

    val filteredPlaces = sortedPlaces.filter {
        it.name.contains(query, ignoreCase = true) ||
                it.location.address.contains(query, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column {
            Text(
                "Locales Deportivos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor
            )
            Text(
                "Encuentra los mejores complejos deportivos cerca de ti",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Buscar local o dirección...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = PrimaryColor) },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        )

        when (val state = uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryColor)
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message, color = Color.Red, fontSize = 16.sp)
                }
            }
            is UiState.Success -> {
                if (filteredPlaces.isEmpty()) {
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
                                imageVector = Icons.Default.Store,
                                contentDescription = null,
                                tint = PrimaryColor,
                                modifier = Modifier.size(64.dp)
                            )
                            Text(
                                "No se encontraron locales",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray
                            )
                            Text(
                                "Intenta buscar con otro nombre o término.",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(filteredPlaces) { place ->
                            var showMap by remember { mutableStateOf(false) }
                            val destination = LatLng(place.location.latitude, place.location.longitude)

                            val distanceText = remember(userLocation) {
                                userLocation?.let {
                                    val result = FloatArray(1)
                                    Location.distanceBetween(
                                        it.latitude, it.longitude,
                                        destination.latitude, destination.longitude,
                                        result
                                    )
                                    String.format(Locale.US, "%.1f km", result[0] / 1000)
                                } ?: ""
                            }

                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { toSoccerFields(place.id) },
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

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = place.name,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.DarkGray,
                                            modifier = Modifier.weight(1f)
                                        )

                                        if (distanceText.isNotEmpty()) {
                                            Surface(
                                                color = PrimaryColor.copy(alpha = 0.1f),
                                                shape = RoundedCornerShape(10.dp)
                                            ) {
                                                Text(
                                                    text = "A $distanceText",
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = PrimaryColor,
                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                                )
                                            }
                                        }
                                    }

                                    HorizontalDivider(
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        color = Color.LightGray.copy(alpha = 0.5f)
                                    )

                                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.LocationOn,
                                                contentDescription = null,
                                                tint = Color.Gray,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = place.location.address.ifEmpty { "Dirección no especificada" },
                                                fontSize = 14.sp,
                                                color = Color.DarkGray
                                            )
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.AccessTime,
                                                contentDescription = null,
                                                tint = Color.Gray,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = if (place.openingHours.open.isNotEmpty() && place.openingHours.close.isNotEmpty())
                                                    "Horario: ${place.openingHours.open} - ${place.openingHours.close}"
                                                else "Horario no especificado",
                                                fontSize = 14.sp,
                                                color = Color.DarkGray
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        OutlinedButton(
                                            onClick = {
                                                if (!permissionGranted.value) {
                                                    permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                                                } else {
                                                    fusedLocationProvider.lastLocation.addOnSuccessListener { loc ->
                                                        loc?.let {
                                                            userLocation = LatLng(it.latitude, it.longitude)
                                                            showMap = !showMap
                                                        }
                                                    }
                                                }
                                            },
                                            shape = RoundedCornerShape(12.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Map,
                                                contentDescription = null,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(if (showMap) "Ocultar" else "Mapa", fontSize = 13.sp)
                                        }

                                        Button(
                                            onClick = { toSoccerFields(place.id) },
                                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                                            shape = RoundedCornerShape(12.dp),
                                            modifier = Modifier.weight(1.5f)
                                        ) {
                                            Text("Ver Canchas", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }

                                    if (showMap && permissionGranted.value && destination.latitude != 0.0 && destination.longitude != 0.0) {
                                        val cameraPositionState = rememberCameraPositionState {
                                            position = CameraPosition.fromLatLngZoom(destination, 14f)
                                        }

                                        Spacer(modifier = Modifier.height(12.dp))

                                        ElevatedCard(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(220.dp),
                                            shape = RoundedCornerShape(16.dp)
                                        ) {
                                            GoogleMap(
                                                modifier = Modifier.fillMaxSize(),
                                                cameraPositionState = cameraPositionState
                                            ) {
                                                Marker(
                                                    state = remember(destination) { MarkerState(position = destination) },
                                                    title = place.name
                                                )
                                                userLocation?.let { origin ->
                                                    Marker(
                                                        state = remember(origin) { MarkerState(position = origin) },
                                                        title = "Tú estás aquí"
                                                    )
                                                    Polyline(
                                                        points = listOf(origin, destination),
                                                        color = Color.Blue,
                                                        width = 5f
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
