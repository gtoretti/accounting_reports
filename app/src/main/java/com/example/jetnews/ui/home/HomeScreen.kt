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

package com.example.jetnews.ui.home


import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth


import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll


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

import androidx.compose.runtime.Composable

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp

import com.example.jetnews.R

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
                                painter = painterResource(R.drawable.ic_jetnews_logo),
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
                .padding(horizontal = 5.dp)
                .verticalScroll(rememberScrollState()),
        ) {


            Text(
                text = stringResource(R.string.home_screen_1),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )

            Text(
                text = stringResource(R.string.home_screen_2),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )


            Text(
                text = stringResource(R.string.home_screen_3),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )

            Text(
                text = stringResource(R.string.home_screen_4),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )

            Text(
                text = stringResource(R.string.home_screen_5),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )

            Text(
                text = stringResource(R.string.home_screen_6),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )

            Text(
                text = stringResource(R.string.home_screen_7),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )

            Text(
                text = stringResource(R.string.home_screen_8),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )

            Text(
                text = stringResource(R.string.home_screen_9),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )

            Text(
                text = stringResource(R.string.home_screen_10),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )

            Text(
                text = stringResource(R.string.home_screen_11),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )

            Text(
                text = stringResource(R.string.home_screen_12),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )


        }
    }
}

