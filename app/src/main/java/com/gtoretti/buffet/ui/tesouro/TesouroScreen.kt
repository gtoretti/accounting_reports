/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gtoretti.buffet.ui.tesouro


import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults


import androidx.compose.material3.CenterAlignedTopAppBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Scaffold

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat

import com.gtoretti.buffet.R
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.Boolean

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.gtoretti.buffet.ui.utils.getLightGreenColor
import com.gtoretti.buffet.ui.utils.getLightRedColor
import kotlinx.serialization.json.Json
import java.math.RoundingMode
import kotlin.toBigDecimal

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TesouroScreen(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    TesouroHomeScreen(
        isExpandedScreen = isExpandedScreen,
        openDrawer = openDrawer,
        snackbarHostState,
    )
}




@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TesouroHomeScreen(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.tesouro_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {
                    if (!isExpandedScreen) {
                        IconButton(onClick = openDrawer) {
                            Icon(
                                painter = painterResource(R.drawable.ic_more_vert),
                                contentDescription = stringResource(
                                    R.string.cd_open_navigation_drawer,
                                ),
                            )
                        }
                    }
                },
                actions = {
                },
            )
        },
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        BrinquedosHomeScreenContent(
            screenModifier,
        )
    }
}



@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun BrinquedosHomeScreenContent(
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .verticalScroll(rememberScrollState()),
        ) {

            val context = LocalContext.current
            var latitudeAtual = remember { mutableDoubleStateOf(0.0) }
            var longitudeAtual = remember { mutableDoubleStateOf(0.0) }
            var latitudeTesouro = remember { mutableDoubleStateOf(0.0) }
            var longitudeTesouro = remember { mutableDoubleStateOf(0.0) }
            var distanciaDoTesouro = remember { mutableFloatStateOf(0f) }
            var mensagem = remember { mutableStateOf("") }




            if (latitudeTesouro.value==0.0 && longitudeTesouro.value==0.0){
                BussolaRemoteFileReaderScreen(latitudeTesouro, longitudeTesouro, mensagem)
            }else{
                BussolaLocationScreen(onRequestLocation = {
                    getCurrentLocation(context,latitudeAtual, longitudeAtual, it)
                })
            }


            if (latitudeAtual.value!=0.0 && longitudeAtual.value!=0.0 && latitudeTesouro.value!=0.0 && longitudeTesouro.value!=0.0){
                val distanceMeters = calculateDistance(latitudeAtual.value, longitudeAtual.value, latitudeTesouro.value, longitudeTesouro.value)
                Text(
                    text = distanceMeters.toBigDecimal().setScale(2, RoundingMode.UP).toString().replace(".", ",") + " metros",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,

                )
                distanciaDoTesouro.floatValue = distanceMeters

                if (distanceMeters<3.0){

                    Spacer(Modifier.height(30.dp))
                    Text(
                        text = "Você está muito perto dele!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = getLightGreenColor()
                    )
                    Spacer(Modifier.height(30.dp))
                    Text(
                        text = mensagem.value,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        color = getLightRedColor()
                    )

                }
            }





            Spacer(Modifier.height(50.dp))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )
            Spacer(Modifier.height(30.dp))
            Text(
                text = "Instruções:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = getLightRedColor()
            )

            Spacer(Modifier.height(30.dp))

            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                text = "Para começar a caça ao tesouro, pressione a bússola para abri-la.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(30.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                text = "Depois de aberta, pressione ela novamente para que ela mostre a distância em metros entre você e o tesouro.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(30.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                text = "Aguarde o aviso dos monitores para começar a caça ao tesouro.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(30.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                text = "O tesouro está escondido DENTRO da área informada pelos monitores.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(30.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                text = "A bússola utiliza o GPS deste smartphone para localizar o tesouro. Os dados de GPS estão exibidos abaixo.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )


            Spacer(Modifier.height(30.dp))


            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                text = "latitude:" +latitudeAtual.value + "\nlongitude:" + longitudeAtual.value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

        }
    }
}



















fun calculateDistance(
    lat1: Double, lon1: Double,
    lat2: Double, lon2: Double
): Float {
    // Validate latitude and longitude ranges
    if (lat1 !in -90.0..90.0 || lat2 !in -90.0..90.0 ||
        lon1 !in -180.0..180.0 || lon2 !in -180.0..180.0
    ) {
        throw IllegalArgumentException("Latitude must be between -90 and 90, longitude between -180 and 180.")
    }

    val startPoint = Location("start").apply {
        latitude = lat1
        longitude = lon1
    }

    val endPoint = Location("end").apply {
        latitude = lat2
        longitude = lon2
    }

    return startPoint.distanceTo(endPoint) // returns meters
}

@Composable
fun BussolaRemoteFileReaderScreen(latitudeTesouro: MutableState<Double>, longitudeTesouro: MutableState<Double>, mensagem: MutableState<String>) {
    val scope = rememberCoroutineScope()
    var text = remember { mutableStateOf("Pressione o botão para receber a missão!") }
    var isLoading = remember { mutableStateOf(false) }

    val client = remember {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json() // JSON serialization
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = if (isLoading.value) "Abrindo bússola. Aguarde..." else "Abra a bússola para começar a busca pelo tesouro!",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = getLightRedColor()
        )

        TextButton(
            modifier = Modifier.padding(5.dp),
            onClick =
                {
                    scope.launch {
                        try {
                            isLoading.value = true

                            //https://jsonsilo.com/
                            var uri = "https://api.jsonsilo.com/public/b0f10ab5-69ca-4dd3-8bcd-ac4128981e8f"

                            val data: String = client.get(uri).body()
                            val json = Json { ignoreUnknownKeys = true }
                            val decoded = json.decodeFromString<LocalGPS>(data)

                            latitudeTesouro.value = decoded.latitude
                            longitudeTesouro.value = decoded.longitude
                            mensagem.value = decoded.mensagem

                        } catch (e: Exception) {
                            text.value = "Error: ${e.message!!}"
                        } finally {
                            isLoading.value = false
                        }
                    }
                }
        ) {
            Image(
                    painter = painterResource(id = R.drawable.compass_closed),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(400.dp)
                        .padding(all = 8.dp)
            )
        }
    }
}



// Example data model for JSON
@Serializable
data class LocalGPS(
    val latitude: Double,
    val longitude: Double,
    val mensagem: String,
)

// Request location with permission check
@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, latAtual: MutableState<Double>, longAtual: MutableState<Double>,  onLocationReceived: (String) -> Unit) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        onLocationReceived("Permission not granted")
        return
    }

    var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)


// Create location request
    var locationRequest: LocationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY, // High accuracy
        1000 // Update interval in ms
    ).setMinUpdateIntervalMillis(500) // Fastest update
        .build()

    var locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val location = result.lastLocation
            if (location != null) {
                latAtual.value = location.latitude
                longAtual.value = location.longitude
            }
        }
    }

    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        locationCallback,
        Looper.getMainLooper()
    )

}


@Composable
fun BussolaLocationScreen(onRequestLocation: ((String) -> Unit) -> Unit) {
    var locationText = remember { mutableStateOf("") }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onRequestLocation { locationText.value = it }
        } else {
            locationText.value = "Permission denied"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Pressione a bússola para medir sua distância do tesouro!",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = getLightRedColor()
        )

        TextButton(
            modifier = Modifier.padding(2.dp),
            onClick =
                {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.compass_open),
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(500.dp)
                    .padding(all = 2.dp)
            )
        }
    }
}

fun copy(context: Context, textToCopy: String){
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    // Create a ClipData object with the text
    val clip = ClipData.newPlainText("Copied Text", textToCopy)
    // Set the clip to the clipboard
    clipboard.setPrimaryClip(clip)
}