package com.example.neostorecompose.ui.components

import android.location.Geocoder
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.neostorecompose.domain.model.StoreLocation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch


@Composable
fun StoreLocationMainScreen() {
    val dummyLocations = listOf(
        "NeoStore Mumbai" to "Mumbai, Maharashtra",
        "NeoStore Pune" to "Pune, Maharashtra",
        "NeoStore Delhi" to "New Delhi, Delhi"
    )

    StoreLocationScreen(
        onBack = { /* Navigate back */ },
        storeList = dummyLocations
    )
}


@OptIn(MapsComposeExperimentalApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StoreLocationScreen(
    onBack: () -> Unit,
    storeList: List<Pair<String, String>> // List of Pair<StoreName, Address>
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Geocoding addresses
    var markers by remember { mutableStateOf<Map<String, LatLng>>(emptyMap()) }
    var selectedName by remember { mutableStateOf<String?>(null) }
    var cameraPositionState = rememberCameraPositionState()

    // Load LatLngs from addresses once
    LaunchedEffect(storeList) {
        val geocoder = Geocoder(context)
        val tempMap = mutableMapOf<String, LatLng>()
        storeList.forEach { (name, address) ->
            try {
                val result = geocoder.getFromLocationName(address, 1)
                if (!result.isNullOrEmpty()) {
                    tempMap[name] = LatLng(result[0].latitude, result[0].longitude)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        markers = tempMap
        // Default camera to first marker
        markers.values.firstOrNull()?.let {
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(it, 12f))
        }
    }

    Column(modifier = Modifier
        .BackgroundForScreens()
        .fillMaxSize()) {

        // Top App Bar
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Store Locations",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { /*navController.popBackStack()*/ }) {
                    Icon(
                        Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
        // Google Map
        GoogleMap(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            cameraPositionState = cameraPositionState
        ) {
            markers.forEach { (name, latLng) ->
                Marker(
                    state = MarkerState(position = latLng),
                    title = name,
                    icon = BitmapDescriptorFactory.defaultMarker(
                        if (selectedName == name) BitmapDescriptorFactory.HUE_RED
                        else BitmapDescriptorFactory.HUE_AZURE
                    )
                )
            }
        }

        // List of store names
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.95f))
                .padding(8.dp)
                .heightIn(max = 200.dp)
        ) {
            items(storeList) { (name, address) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            selectedName = name
                            markers[name]?.let { latLng ->
                                coroutineScope.launch {
                                    cameraPositionState.animate(
                                        CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                                    )
                                }
                            }
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedName == name) Color(0xFFEEEEEE) else Color.White
                    )
                ) {
                    Text(
                        text = "$name\n$address",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}



