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

package com.gtoretti.drego.ui.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height


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
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp

import com.gtoretti.drego.R
import com.gtoretti.drego.ui.utils.getLightGreenColor
import com.gtoretti.drego.ui.utils.getLightRedColor

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

            Spacer(Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.home_screen_1),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )


            Spacer(Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.home_screen_1a),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )



            Spacer(Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.home_screen_6),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Justify
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )
            Spacer(Modifier.height(20.dp))



            Text(
                text = stringResource(R.string.home_screen_7),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )


            Spacer(Modifier.height(10.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {


                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = stringResource(R.string.period_results_statement_add_statement),
                            modifier = Modifier.padding(20.dp),
                        )


                    Text(
                        text = stringResource(R.string.home_screen_8),
                        modifier = Modifier,
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start
                    )



                }



                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                )
                Spacer(Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.edit_24px),
                        contentDescription = stringResource(R.string.cash_flows_statement_add),
                        modifier = Modifier.padding(20.dp),
                    )

                    Text(
                        text = stringResource(R.string.home_screen_9),
                        modifier = Modifier,
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start
                    )
                }


                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                )
                Spacer(Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.format_list_bulleted_24px),
                        contentDescription = stringResource(R.string.cash_flows_statement_items),
                        modifier = Modifier.padding(20.dp)
                    )

                    Text(
                        text = stringResource(R.string.home_screen_9a),
                        modifier = Modifier,
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start
                    )
                }


                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                )
                Spacer(Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.delete_24px),
                        contentDescription = stringResource(R.string.cash_flows_statement_delete),
                        modifier = Modifier.padding(20.dp),
                        tint = getLightRedColor()

                    )

                    Text(
                        text = stringResource(R.string.home_screen_10),
                        modifier = Modifier,
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start
                    )
                }


                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                )
                Spacer(Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.delete_24px),
                        contentDescription = stringResource(R.string.cash_flows_statement_delete),
                        modifier = Modifier.padding(20.dp),
                        tint = getLightRedColor()

                    )

                    Text(
                        text = stringResource(R.string.home_screen_10a),
                        modifier = Modifier,
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start
                    )
                }


                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                )
                Spacer(Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.table_view_24px),
                        contentDescription = stringResource(R.string.cash_flows_statement_add),
                        modifier = Modifier.padding(20.dp),
                        tint = getLightGreenColor()
                    )

                    Text(
                        text = stringResource(R.string.home_screen_11),
                        modifier = Modifier,
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start
                    )
                }


                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                )
                Spacer(Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.forms_add_on_24px),
                        contentDescription = stringResource(R.string.cash_flows_statement_add),
                        modifier = Modifier.padding(20.dp),
                        tint = getLightGreenColor()
                    )

                    Text(
                        text = stringResource(R.string.home_screen_12),
                        modifier = Modifier,
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start
                    )
                }


                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                )
                Spacer(Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.keyboard_arrow_right_24px),
                        contentDescription = "Fechar",
                        modifier = Modifier.padding(start = 20.dp),
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.folder_24px),
                        contentDescription = "Abrir",
                        modifier = Modifier.padding(end = 20.dp),
                    )

                    Text(
                        text = stringResource(R.string.home_screen_12a),
                        modifier = Modifier,
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start
                    )
                }
            }


            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )
            Spacer(Modifier.height(30.dp))



            /*
            Text(
                text = stringResource(R.string.home_screen_2),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )
            Spacer(Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.home_screen_3),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )
            Spacer(Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.home_screen_4),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )
            Spacer(Modifier.height(10.dp))
*/
            Text(
                text = stringResource(R.string.home_screen_5),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Justify
            )



            Spacer(Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.home_screen_5a),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Justify
            )



            Spacer(Modifier.height(30.dp))





            Text(
                text = stringResource(R.string.home_screen_13),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(R.string.home_screen_13a),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )

            Spacer(Modifier.height(30.dp))

        }
    }
}

