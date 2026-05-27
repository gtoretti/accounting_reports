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

package com.gtoretti.buffet.ui.home


import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

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
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

import com.gtoretti.buffet.R
import com.gtoretti.buffet.ui.utils.getLightGreenColor
import com.gtoretti.buffet.ui.utils.getLightRedColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

import kotlin.Boolean


/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
            HomeScreen(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                snackbarHostState,
            )
}


/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
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
                        text = stringResource(R.string.home_title),
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
        HomeScreenContent(
            screenModifier,
        )
    }
}

/**
 */
@Composable
private fun HomeScreenContent(
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            val context = LocalContext.current







            //Bem-vindo

            Spacer(Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.home_bemvindo),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )


            Spacer(Modifier.height(30.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )
            Spacer(Modifier.height(30.dp))





            // Carrosel de imagens:
            val images = listOf(
                "atracoes.jpg",
                "festas.jpg",
                "obuffet.jpg",
            )
            ImageCarousel(images)

            Spacer(Modifier.height(30.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )

            Spacer(Modifier.height(30.dp))

            // Endereço:
            Text(
                text = stringResource(R.string.home_endereco),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )

            Spacer(Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.home_endereco_rua),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
            )

            // botao exibir no Maps
            TextButton(
                modifier = Modifier.padding(5.dp),
                onClick =
                    {
                        openMapWithAddress("Rua Joana Antoniolli Scarassatti 349 Recanto do Lago, Paulínia 13140-868",
                            context
                        )
                    }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.location_on_24px),
                    contentDescription = "Mapa",
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .size(30.dp)
                )
            }

            Spacer(Modifier.height(30.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )

            Spacer(Modifier.height(30.dp))

            // Contato para reservas:
            Text(
                text = stringResource(R.string.home_contato),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )

            Spacer(Modifier.height(30.dp))

            // botao enviar email
            Button(
                onClick = {

                    sendEmail(context,"eventos_realize@yahoo.com.br","Orçamento e reserva","Olá,\nGostaria de solicitar orçamento de festa.")

                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = getLightRedColor(), // botao vermelho
                    contentColor = Color.White          // texto do botao branco
                ),
            ) {
                Text("Enviar e-mail")
            }



            Spacer(Modifier.height(30.dp))



            //botao whatsapp
            Button(
                onClick = {

                    sendWhatsAppMessage(context,"+55 19 98717-7588", "Olá, gostaria de solicitar orçamento de festa.")

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = getLightGreenColor(), // botao verde
                    contentColor = Color.White          // texto do botao branco
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Contato por WhatsApp")
            }






        }
    }
}






fun sendWhatsAppMessage(context: Context, phone: String, message: String) {
    try {
        val formattedPhone = phone.replace("+", "").replace(" ", "")
        val uri = Uri.parse("https://wa.me/$formattedPhone?text=${Uri.encode(message)}")

        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.whatsapp") // Force open in WhatsApp

        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "O WhatsApp não está instalado.", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

fun sendEmail(context: Context, email: String, subject: String, message: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:") // Ensures only email apps handle this
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
    }

    try {
        context.startActivity(intent)
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun ImageCarousel(imageUrls: List<String>, autoScrollDelay: Long = 3000) {
    val pagerState = rememberPagerState(initialPage = 0)

    // Auto-scroll effect
    LaunchedEffect(pagerState.currentPage) {
        yield() // Avoid immediate jump
        delay(autoScrollDelay)
        val nextPage = (pagerState.currentPage + 1) % imageUrls.size
        //pagerState.animateScrollToPage(nextPage) //comment to disable autoscroll
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = imageUrls.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            AsyncImage(
                model = "file:///android_asset/"+imageUrls[page], // Coil can take a File directly
                contentDescription = imageUrls[page],
                modifier = Modifier
                    .size(200.dp)
                    //.fillMaxHeight()
                    .aspectRatio(1f) // Keep square ratio (adjust as needed)
                    .padding(1.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Pager indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = Color.Gray,
            indicatorShape = CircleShape,
            modifier = Modifier.padding(8.dp)
        )
    }
}

fun openMapWithAddress(address: String, context: Context) {
    val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}