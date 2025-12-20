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

package com.example.jetnews.ui.equityChangesStatement

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Scaffold

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration


import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontFamily.Companion
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetnews.R
import com.example.jetnews.data.equityChangesStatement.EquityChangesStatement
import com.example.jetnews.data.equityChangesStatement.EquityChangesStatementItem
import com.example.jetnews.ui.utils.DatePickerModal
import com.example.jetnews.ui.utils.screenToDouble
import com.example.jetnews.ui.utils.toScreen
import com.example.jetnews.ui.utils.toScreenParenthesis
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CAPITAL_INCREASE
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CAPITAL_TRANSACTIONS_WITH_SHAREHOLDERS
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CREATION_OF_RESERVES
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_DIVIDENDS
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_EQUITY_IN_COMPREHENSIVE_INCOME_OF_AFFILIATES
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_EXPENSES_ON_STOCK_ISSUANCE
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_FINAL_BALANCES
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_FINANCIAL_INSTRUMENTS_ADJUSTMENTS
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_INITIAL_BALANCES
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_NET_PROFIT_FOR_PERIOD
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_OTHER_COMPREHENSIVE_RESULTS
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_PERIOD_CONVERSION_ADJUSTMENTS
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RECLASSIFICATION_BY_RESULT_FINANCIAL_INSTRUMENT_ADJUSTMENT
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RECOGNIZED_GRANTED_OPTIONS
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RESERVATION_REVIEW
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_FINANCIAL_INSTRUMENT_ADJUSTMENTS
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_PERIOD_CONVERSION_ADJUSTMENTS
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_REALIZATION_OF_REVALUATION_RESERVE
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TOTAL_COMPREHENSIVE_RESULT
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TREASURY_SHARES_ACQUIRED
import com.example.jetnews.utils.EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TREASURY_SHARES_SOLD

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.Boolean
import kotlin.Double
import kotlin.Long
import kotlin.String
import kotlin.math.absoluteValue
import kotlin.text.isEmpty



/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquityChangesStatementScreen(
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isEquityChangesStatementAddScreen = remember { mutableStateOf(false) }
    val isEquityChangesStatementEditScreen = remember { mutableStateOf(false) }
    val isEquityChangesStatementItemsAddItemScreen = remember { mutableStateOf(false) }
    val isEquityChangesStatementItemsEditItemScreen = remember { mutableStateOf(false) }
    val isEquityChangesStatementItemsScreen = remember { mutableStateOf(false) }

    val equityChangesStatementName = remember { mutableStateOf("") }
    val equityChangesStatementId = remember { mutableLongStateOf(0) }
    val equityChangesStatementStartDate = remember { mutableStateOf("") }
    val equityChangesStatementEndDate = remember { mutableStateOf("") }

    val equityChangesStatementDescriptionList = remember { mutableListOf<String>() }

    val equityChangesStatementItemId = remember { mutableLongStateOf(0) }
    val equityChangesStatementItemType = remember { mutableStateOf("") }
    val equityChangesStatementItemPaidInCapitalValue = remember { mutableDoubleStateOf(0.0) }
    val equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue = remember { mutableDoubleStateOf(0.0) }
    val equityChangesStatementItemProfitReservesValue = remember { mutableDoubleStateOf(0.0) }
    val equityChangesStatementItemAccumulatedProfitsOrLossesValue = remember { mutableDoubleStateOf(0.0) }
    val equityChangesStatementItemOtherComprehensiveResultsValue = remember { mutableDoubleStateOf(0.0) }
    val equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue = remember { mutableDoubleStateOf(0.0) }
    val equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue = remember { mutableDoubleStateOf(0.0) }
    val equityChangesStatementItemConsolidatedEquityValue = remember { mutableDoubleStateOf(0.0) }

    when {
        isEquityChangesStatementAddScreen.value -> {
            EquityChangesStatementAddScreen(
                equityChangesStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isEquityChangesStatementAddScreen
            )
        }
        isEquityChangesStatementEditScreen.value -> {
            EquityChangesStatementEditScreen(
                equityChangesStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isEquityChangesStatementEditScreen,
                equityChangesStatementName,
                equityChangesStatementId,
                equityChangesStatementStartDate,
                equityChangesStatementEndDate
            )
        }
        isEquityChangesStatementItemsEditItemScreen.value -> {
            EquityChangesStatementItemsEditItemScreen(
                equityChangesStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                equityChangesStatementName,
                equityChangesStatementId,
                equityChangesStatementStartDate,
                equityChangesStatementEndDate,
                isEquityChangesStatementItemsEditItemScreen,
                equityChangesStatementItemId,
                equityChangesStatementItemType,
                equityChangesStatementItemPaidInCapitalValue,
                equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                equityChangesStatementItemProfitReservesValue,
                equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                equityChangesStatementItemOtherComprehensiveResultsValue,
                equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                equityChangesStatementItemConsolidatedEquityValue,
                equityChangesStatementDescriptionList
            )
        }
        isEquityChangesStatementItemsAddItemScreen.value -> {
            EquityChangesStatementItemsAddItemScreen(
                equityChangesStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                equityChangesStatementName,
                equityChangesStatementId,
                equityChangesStatementStartDate,
                equityChangesStatementEndDate,
                isEquityChangesStatementItemsAddItemScreen,
                equityChangesStatementDescriptionList
            )
        }
        isEquityChangesStatementItemsScreen.value -> {
            EquityChangesStatementItemsScreen(
                equityChangesStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isEquityChangesStatementItemsScreen,
                equityChangesStatementName,
                equityChangesStatementId,
                equityChangesStatementStartDate,
                equityChangesStatementEndDate,
                isEquityChangesStatementItemsAddItemScreen,
                isEquityChangesStatementItemsEditItemScreen,
                equityChangesStatementItemId,
                equityChangesStatementItemType,
                equityChangesStatementItemPaidInCapitalValue,
                equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                equityChangesStatementItemProfitReservesValue,
                equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                equityChangesStatementItemOtherComprehensiveResultsValue,
                equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                equityChangesStatementItemConsolidatedEquityValue,
                equityChangesStatementDescriptionList,
            )
        }
        else -> {
            EquityChangesStatementHomeScreen(
                equityChangesStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isEquityChangesStatementAddScreen,
                isEquityChangesStatementEditScreen,
                equityChangesStatementName,
                equityChangesStatementId,
                equityChangesStatementStartDate,
                equityChangesStatementEndDate,
                isEquityChangesStatementItemsScreen,
            )
        }
    }
}


/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquityChangesStatementHomeScreen(
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isEquityChangesStatementAddScreen: MutableState<Boolean>,
    isEquityChangesStatementEditScreen: MutableState<Boolean>,
    equityChangesStatementName: MutableState<String>,
    equityChangesStatementId: MutableState<Long>,
    equityChangesStatementStartDate: MutableState<String>,
    equityChangesStatementEndDate: MutableState<String>,
    isEquityChangesStatementItemsScreen: MutableState<Boolean>,
) {


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_title),
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
                    IconButton(
                        onClick = {
                            isEquityChangesStatementAddScreen.value=true
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = stringResource(R.string.equity_changes_statement_add_statement),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        EquityChangesStatementHomeScreenContent(
            equityChangesStatementViewModel,
            screenModifier,
            isEquityChangesStatementEditScreen,
            equityChangesStatementName,
            equityChangesStatementId,
            equityChangesStatementStartDate,
            equityChangesStatementEndDate,
            isEquityChangesStatementItemsScreen,
        )
    }
}


/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 * @param snackbarHostState (state) the state for the screen's [Scaffold]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquityChangesStatementAddScreen(
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isEquityChangesStatementAddScreen: MutableState<Boolean>
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_add_statement),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isEquityChangesStatementAddScreen.value=false
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(
                                R.string.cd_open_navigation_drawer,
                            ),
                        )
                    }

                },
                actions = {
                },
            )
        },
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        EquityChangesStatementAddScreenContent(
            equityChangesStatementViewModel,
            screenModifier,
            isEquityChangesStatementAddScreen
        )
    }
}


/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 * @param snackbarHostState (state) the state for the screen's [Scaffold]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquityChangesStatementEditScreen(
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isEquityChangesStatementEditScreen: MutableState<Boolean>,
    equityChangesStatementName: MutableState<String>,
    equityChangesStatementId: MutableState<Long>,
    equityChangesStatementStartDate: MutableState<String>,
    equityChangesStatementEndDate: MutableState<String>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_change_statement),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isEquityChangesStatementEditScreen.value=false
                        equityChangesStatementName.value=""
                        equityChangesStatementId.value=0
                        equityChangesStatementStartDate.value=""
                        equityChangesStatementEndDate.value=""
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(
                                R.string.cd_open_navigation_drawer,
                            ),
                        )
                    }

                },
                actions = {
                },
            )
        },
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        EquityChangesStatementEditScreenContent(
            equityChangesStatementViewModel,
            screenModifier,
            isEquityChangesStatementEditScreen,
            equityChangesStatementName,
            equityChangesStatementId,
            equityChangesStatementStartDate,
            equityChangesStatementEndDate,
        )
    }
}


/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 * @param snackbarHostState (state) the state for the screen's [Scaffold]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquityChangesStatementItemsScreen(
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isEquityChangesStatementItemsScreen: MutableState<Boolean>,
    equityChangesStatementName: MutableState<String>,
    equityChangesStatementId: MutableState<Long>,
    equityChangesStatementStartDate: MutableState<String>,
    equityChangesStatementEndDate: MutableState<String>,
    isEquityChangesStatementItemsAddItemScreen: MutableState<Boolean>,
    isEquityChangesStatementItemsEditItemScreen: MutableState<Boolean>,
    equityChangesStatementItemId: MutableState<Long>,
    equityChangesStatementItemType: MutableState<String>,
    equityChangesStatementItemPaidInCapitalValue: MutableState<Double>,
    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue: MutableState<Double>,
    equityChangesStatementItemProfitReservesValue: MutableState<Double>,
    equityChangesStatementItemAccumulatedProfitsOrLossesValue: MutableState<Double>,
    equityChangesStatementItemOtherComprehensiveResultsValue: MutableState<Double>,
    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue: MutableState<Double>,
    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue: MutableState<Double>,
    equityChangesStatementItemConsolidatedEquityValue: MutableState<Double>,
    equityChangesStatementDescriptionList: MutableList<String>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_items),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isEquityChangesStatementItemsScreen.value=false
                        equityChangesStatementName.value=""
                        equityChangesStatementId.value=0
                        equityChangesStatementStartDate.value=""
                        equityChangesStatementEndDate.value=""
                        equityChangesStatementDescriptionList.removeAll(equityChangesStatementDescriptionList)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(
                                R.string.cd_open_navigation_drawer,
                            ),
                        )
                    }

                },
                actions = {
                },
            )
        },
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        EquityChangesStatementItemsScreenContent(
            equityChangesStatementViewModel,
            screenModifier,
            equityChangesStatementName,
            equityChangesStatementId,
            equityChangesStatementStartDate,
            equityChangesStatementEndDate,
            isEquityChangesStatementItemsAddItemScreen,
            isEquityChangesStatementItemsEditItemScreen,
            equityChangesStatementItemId,
            equityChangesStatementItemType,
            equityChangesStatementItemPaidInCapitalValue,
            equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
            equityChangesStatementItemProfitReservesValue,
            equityChangesStatementItemAccumulatedProfitsOrLossesValue,
            equityChangesStatementItemOtherComprehensiveResultsValue,
            equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
            equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
            equityChangesStatementItemConsolidatedEquityValue,
            equityChangesStatementDescriptionList
        )
    }
}



/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 * @param snackbarHostState (state) the state for the screen's [Scaffold]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquityChangesStatementItemsAddItemScreen(
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    equityChangesStatementName: MutableState<String>,
    equityChangesStatementId: MutableState<Long>,
    equityChangesStatementStartDate: MutableState<String>,
    equityChangesStatementEndDate: MutableState<String>,
    isEquityChangesStatementItemsAddItemScreen: MutableState<Boolean>,
    equityChangesStatementDescriptionList: MutableList<String>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_items_add_item),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isEquityChangesStatementItemsAddItemScreen.value=false
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(
                                R.string.cd_open_navigation_drawer,
                            ),
                        )
                    }

                },
                actions = {
                },
            )
        },
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        EquityChangesStatementItemsAddItemScreenContent(
            equityChangesStatementViewModel,
            screenModifier,
            equityChangesStatementName,
            equityChangesStatementId,
            equityChangesStatementStartDate,
            equityChangesStatementEndDate,
            isEquityChangesStatementItemsAddItemScreen,
            equityChangesStatementDescriptionList
        )
    }
}


/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 * @param snackbarHostState (state) the state for the screen's [Scaffold]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquityChangesStatementItemsEditItemScreen(
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    equityChangesStatementName: MutableState<String>,
    equityChangesStatementId: MutableState<Long>,
    equityChangesStatementStartDate: MutableState<String>,
    equityChangesStatementEndDate: MutableState<String>,
    isEquityChangesStatementItemsEditItemScreen: MutableState<Boolean>,
    equityChangesStatementItemId: MutableState<Long>,
    equityChangesStatementItemType: MutableState<String>,
    equityChangesStatementItemPaidInCapitalValue: MutableState<Double>,
    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue: MutableState<Double>,
    equityChangesStatementItemProfitReservesValue: MutableState<Double>,
    equityChangesStatementItemAccumulatedProfitsOrLossesValue: MutableState<Double>,
    equityChangesStatementItemOtherComprehensiveResultsValue: MutableState<Double>,
    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue: MutableState<Double>,
    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue: MutableState<Double>,
    equityChangesStatementItemConsolidatedEquityValue: MutableState<Double>,
    equityChangesStatementDescriptionList: MutableList<String>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_items_edit),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isEquityChangesStatementItemsEditItemScreen.value=false
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(
                                R.string.cd_open_navigation_drawer,
                            ),
                        )
                    }

                },
                actions = {
                },
            )
        },
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        EquityChangesStatementItemsEditItemScreenContent(
            equityChangesStatementViewModel,
            screenModifier,
            equityChangesStatementName,
            equityChangesStatementId,
            equityChangesStatementStartDate,
            equityChangesStatementEndDate,
            isEquityChangesStatementItemsEditItemScreen,
            equityChangesStatementItemId,
            equityChangesStatementItemType,
            equityChangesStatementItemPaidInCapitalValue,
            equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
            equityChangesStatementItemProfitReservesValue,
            equityChangesStatementItemAccumulatedProfitsOrLossesValue,
            equityChangesStatementItemOtherComprehensiveResultsValue,
            equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
            equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
            equityChangesStatementItemConsolidatedEquityValue,
            equityChangesStatementDescriptionList,
        )
    }
}



/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun EquityChangesStatementEditScreenContent(
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    modifier: Modifier = Modifier,
    isEquityChangesStatementEditScreen: MutableState<Boolean>,
    equityChangesStatementName: MutableState<String>,
    equityChangesStatementId: MutableState<Long>,
    equityChangesStatementStartDate: MutableState<String>,
    equityChangesStatementEndDate: MutableState<String>,
) {

    val name = remember { mutableStateOf(equityChangesStatementName.value) }
    val openStartDateEditDialog= remember { mutableStateOf(false) }
    val startDate = remember { mutableStateOf(equityChangesStatementStartDate.value) }
    val openEndDateEditDialog= remember { mutableStateOf(false) }
    val endDate = remember { mutableStateOf(equityChangesStatementEndDate.value) }
    val fmt = SimpleDateFormat(stringResource(R.string.fmtDatePattern))
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Column(modifier) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .verticalScroll(rememberScrollState()),
        ) {

            OutlinedTextField(
                value = name.value,
                onValueChange = {
                    name.value = it
                },
                placeholder = { Text("") },
                label = {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_name),
                    )
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier=Modifier.fillMaxWidth().selectable(
                    selected = false,
                    onClick = {
                        openStartDateEditDialog.value=true
                    },
                    role = Role.Button
                )
            ) {
                OutlinedTextField(
                    value = startDate.value,
                    onValueChange = {
                        startDate.value = it
                    },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = stringResource(R.string.equity_changes_statement_startDate),
                        )
                    },
                    modifier = Modifier.onFocusChanged { focusState ->
                        if (focusState.isFocused){
                            openStartDateEditDialog.value=true
                            focusManager.clearFocus()
                        }
                    },
                )
                TextButton(
                    modifier = Modifier.padding(3.dp),
                    onClick =
                        {
                            openStartDateEditDialog.value=true
                        }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.calendar_month_24px),
                        contentDescription = stringResource(R.string.equity_changes_statement_startDate),
                        modifier = Modifier
                    )
                }

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier=Modifier.fillMaxWidth().selectable(
                    selected = false,
                    onClick = {
                        openEndDateEditDialog.value=true
                    },
                    role = Role.Button
                )
            )
            {
                OutlinedTextField(
                    value = endDate.value,
                    onValueChange = {
                        endDate.value = it
                    },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = stringResource(R.string.equity_changes_statement_endDate),
                        )
                    },
                    modifier = Modifier.onFocusChanged { focusState ->
                        if (focusState.isFocused){
                            openEndDateEditDialog.value=true
                            focusManager.clearFocus()
                        }
                    },
                )
                TextButton(
                    modifier = Modifier.padding(3.dp),
                    onClick =
                        {
                            openEndDateEditDialog.value=true
                        }
                )
                {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.calendar_month_24px),
                        contentDescription = stringResource(R.string.equity_changes_statement_endDate),
                        modifier = Modifier
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().border(width = 5.dp, Color(0x00C0D1DF))
            ) {
                Button(
                    onClick = {
                        isEquityChangesStatementEditScreen.value=false
                        equityChangesStatementName.value=""
                        name.value=""
                        equityChangesStatementId.value=0
                        startDate.value = ""
                        endDate.value = ""
                        openStartDateEditDialog.value=false
                        openEndDateEditDialog.value=false
                    },

                    ) {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

                val changedSuccess = stringResource(R.string.equity_changes_statement_changed_success)
                val missingNameMsg = stringResource(R.string.equity_changes_statement_missing_name)
                val missingStartDateMsg = stringResource(R.string.equity_changes_statement_missing_startdate)
                val missingEndDateMsg = stringResource(R.string.equity_changes_statement_missing_enddate)

                Button(
                    onClick = {
                        if (name.value.isEmpty()){
                            Toast.makeText(
                                context,
                                missingNameMsg,
                                Toast.LENGTH_SHORT,
                            ).show()
                        }else
                            if (startDate.value.isEmpty()){
                                Toast.makeText(
                                    context,
                                    missingStartDateMsg,
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }else
                                if (endDate.value.isEmpty()){
                                    Toast.makeText(
                                        context,
                                        missingEndDateMsg,
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }else {
                                    try {
                                        equityChangesStatementViewModel.saveEquityChangesStatement(
                                            EquityChangesStatement(
                                                equityChangesStatementId.value,
                                                name.value,
                                                fmt.parse(startDate.value)!!,
                                                fmt.parse(endDate.value)!!,
                                                0
                                            )
                                        )
                                        Toast.makeText(
                                            context,
                                            changedSuccess,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                        isEquityChangesStatementEditScreen.value = false
                                        equityChangesStatementName.value = ""
                                        name.value = ""
                                        equityChangesStatementId.value = 0
                                        startDate.value = ""
                                        endDate.value = ""
                                        openStartDateEditDialog.value = false
                                        openEndDateEditDialog.value = false
                                    }catch (e: Exception){
                                        Toast.makeText(
                                            context,
                                            e.message,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }
                                }
                    },

                    ) {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_save),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
            }
        }
    }

    when {
        openStartDateEditDialog.value -> {
            val fmt = stringResource(R.string.fmtDatePattern)
            DatePickerModal(
                onDateSelected = {
                    if (it != null) {
                        startDate.value = SimpleDateFormat(fmt).format(Date(it))
                    }
                },
                openDialog = openStartDateEditDialog, title = stringResource(R.string.equity_changes_statement_startDate)
            )
        }

        openEndDateEditDialog.value -> {
            val fmt = stringResource(R.string.fmtDatePattern)
            DatePickerModal(
                onDateSelected = {
                    if (it != null) {
                        endDate.value = SimpleDateFormat(fmt).format(Date(it))
                    }
                },
                openDialog = openEndDateEditDialog, title = stringResource(R.string.equity_changes_statement_endDate)
            )
        }
    }
}


/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun EquityChangesStatementItemsScreenContent(
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    modifier: Modifier = Modifier,
    equityChangesStatementName: MutableState<String>,
    equityChangesStatementId: MutableState<Long>,
    equityChangesStatementStartDate: MutableState<String>,
    equityChangesStatementEndDate: MutableState<String>,
    isEquityChangesStatementItemsAddItemScreen: MutableState<Boolean>,
    isEquityChangesStatementItemsEditItemScreen: MutableState<Boolean>,
    equityChangesStatementItemId: MutableState<Long>,
    equityChangesStatementItemType: MutableState<String>,
    equityChangesStatementItemPaidInCapitalValue: MutableState<Double>,
    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue: MutableState<Double>,
    equityChangesStatementItemProfitReservesValue: MutableState<Double>,
    equityChangesStatementItemAccumulatedProfitsOrLossesValue: MutableState<Double>,
    equityChangesStatementItemOtherComprehensiveResultsValue: MutableState<Double>,
    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue: MutableState<Double>,
    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue: MutableState<Double>,
    equityChangesStatementItemConsolidatedEquityValue: MutableState<Double>,
    equityChangesStatementDescriptionList: MutableList<String>,
) {

    val context = LocalContext.current
    val isItemsPageDeleteItem = remember { mutableStateOf(false) }
    val equityChangesItemsFlow = equityChangesStatementViewModel.getEquityChangesStatementItems(equityChangesStatementId.value)
    val equityChangesItems by equityChangesItemsFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    equityChangesStatementDescriptionList.removeAll(equityChangesStatementDescriptionList)
    equityChangesItems.forEach {
        equityChangesStatementDescriptionList.add(it.type)
    }

    Column(modifier) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        )
        Text(
            text = equityChangesStatementName.value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = equityChangesStatementStartDate.value + " - " + equityChangesStatementEndDate.value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        )
        {
            val allTypesAlreadyAdded = stringResource(R.string.equity_changes_statement_all_items_already_added)
            TextButton(
                modifier = Modifier.padding(1.dp),
                onClick =
                    {
                        val equityChangeTypes = getMissingTypes(equityChangesStatementDescriptionList)
                        if (equityChangeTypes.isNotEmpty()){
                            isEquityChangesStatementItemsAddItemScreen.value = true
                        }else{
                            Toast.makeText(
                                context,
                                allTypesAlreadyAdded,
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.forms_add_on_24px),
                    contentDescription = stringResource(R.string.cash_flows_statement_add),
                    modifier = Modifier
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp).verticalScroll(rememberScrollState()),

        ) {
            //datatable
            var cellHeigth =150
            cellHeigth += cellHeigth * equityChangesItems.size
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth().height(cellHeigth.dp)
            )
            {
                GridRowTitles(equityChangesItems,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
                ScrollingGrid(equityChangesItems)
            }
        }
    }

    when {
        isItemsPageDeleteItem.value -> {
            EquityChangesStatementItemDeleteDialog(isItemsPageDeleteItem,
                equityChangesStatementItemId,
                equityChangesStatementItemType,
                equityChangesStatementViewModel,
                context)
        }
    }
}

fun hasType(equityChangesItems: List<EquityChangesStatementItem>, type:String): EquityChangesStatementItem {
    equityChangesItems.forEach {
        if (it.type == type)
            return it
    }
    return EquityChangesStatementItem(0,0,"",0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0)
}

@Composable
fun isHeader(label:String): Boolean {
    val headers = listOf(
        stringResource(R.string.equity_changes_statement_paidInCapitalValue),
        stringResource(R.string.equity_changes_statement_capitalReservesGrantedOptionsAndTreasurySharesValue),
        stringResource(R.string.equity_changes_statement_accumulatedProfitsOrLossesValue),
        stringResource(R.string.equity_changes_statement_profitReservesValue),
        stringResource(R.string.equity_changes_statement_otherComprehensiveResultsValue),
        stringResource(R.string.equity_changes_statement_equityOfTheParentCompanyShareholdersValue),
        stringResource(R.string.equity_changes_statement_nonControllingInterestsInEquityOfSubsidiaryValue),
        stringResource(R.string.equity_changes_statement_consolidatedEquityValue),
        stringResource(R.string.equity_changes_statement_item_added_success),
        stringResource(R.string.equity_changes_statement_item_type_grid),
    )
    return headers.contains(label)
}

@Composable
fun CellCard(label: String, modifier: Modifier){
    OutlinedCard(
        border = BorderStroke(2.dp, Color.Black),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = modifier
    ){
        Text(label, modifier.padding(3.dp),textAlign = TextAlign.Center,
            style = TextStyle(fontFamily = FontFamily.SansSerif),

            )
    }
}

@Composable
fun CellTitleRowCard(label: String, modifier: Modifier){
    OutlinedCard(
        border = BorderStroke(2.dp, Color.Black),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
    ),
        modifier = modifier
    ){
        Text(label, modifier.padding(3.dp),textAlign = TextAlign.Center,
            style = TextStyle(fontFamily = FontFamily.SansSerif),

            )
    }
}

@Composable
fun CellTitleColumnCard(label: String, modifier: Modifier,
                        item: EquityChangesStatementItem,
                        equityChangesStatementItemId: MutableState<Long>,
                        equityChangesStatementItemType: MutableState<String>,
                        equityChangesStatementItemPaidInCapitalValue: MutableState<Double>,
                        equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue: MutableState<Double>,
                        equityChangesStatementItemProfitReservesValue: MutableState<Double>,
                        equityChangesStatementItemAccumulatedProfitsOrLossesValue: MutableState<Double>,
                        equityChangesStatementItemOtherComprehensiveResultsValue: MutableState<Double>,
                        equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue: MutableState<Double>,
                        equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue: MutableState<Double>,
                        equityChangesStatementItemConsolidatedEquityValue: MutableState<Double>,
                        isEquityChangesStatementItemsEditItemScreen: MutableState<Boolean>,
                        isItemsPageDeleteItem: MutableState<Boolean>){

    OutlinedCard(
        border = BorderStroke(2.dp, Color.Black),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        ),
        modifier = modifier
    ){
        Column(
            modifier = modifier.padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(50.dp)
            )
            {
                TextButton(
                    modifier = Modifier.padding(3.dp),
                    onClick =
                        {
                            isItemsPageDeleteItem.value=true
                            equityChangesStatementItemId.value=item.equityChangesStatementItemId
                            equityChangesStatementItemType.value=item.type
                        }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.delete_24px),
                        contentDescription = stringResource(R.string.equity_changes_statement_item_delete),
                        modifier = Modifier,
                        tint = MaterialTheme.colorScheme.tertiaryContainer
                    )
                }

                TextButton(
                    modifier = Modifier.padding(3.dp),
                    onClick =
                        {
                            isEquityChangesStatementItemsEditItemScreen.value=true
                            equityChangesStatementItemId.value=item.equityChangesStatementItemId
                            equityChangesStatementItemType.value=item.type
                            equityChangesStatementItemPaidInCapitalValue.value=item.paidInCapitalValue
                            equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue.value=item.capitalReservesGrantedOptionsAndTreasurySharesValue
                            equityChangesStatementItemProfitReservesValue.value=item.profitReservesValue
                            equityChangesStatementItemAccumulatedProfitsOrLossesValue.value=item.accumulatedProfitsOrLossesValue
                            equityChangesStatementItemOtherComprehensiveResultsValue.value=item.otherComprehensiveResultsValue
                            equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue.value=item.equityOfTheParentCompanyShareholdersValue
                            equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue.value=item.nonControllingInterestsInEquityOfSubsidiaryValue
                            equityChangesStatementItemConsolidatedEquityValue.value=item.consolidatedEquityValue
                        }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.edit_24px),
                        contentDescription = stringResource(R.string.equity_changes_statement_item_edit),
                        modifier = Modifier,
                        tint = MaterialTheme.colorScheme.tertiaryContainer
                    )
                }
            }
            Text(label, Modifier.padding(3.dp), textAlign = TextAlign.Center,
                style = TextStyle(fontFamily = FontFamily.SansSerif),

                )
        }
    }
}


@Composable
fun CellTitleFinalColumnCard(label: String, modifier: Modifier){

    OutlinedCard(
        border = BorderStroke(2.dp, Color.Black),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = modifier
    ){
        Column(
            modifier = modifier.padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, Modifier.padding(3.dp), textAlign = TextAlign.Center,
                style = TextStyle(fontFamily = FontFamily.SansSerif),

                )
        }
    }
}

@Composable
fun GridRowTitles(equityChangesItems: List<EquityChangesStatementItem>,
                  equityChangesStatementItemId: MutableState<Long>,
                  equityChangesStatementItemType: MutableState<String>,
                  equityChangesStatementItemPaidInCapitalValue: MutableState<Double>,
                  equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue: MutableState<Double>,
                  equityChangesStatementItemProfitReservesValue: MutableState<Double>,
                  equityChangesStatementItemAccumulatedProfitsOrLossesValue: MutableState<Double>,
                  equityChangesStatementItemOtherComprehensiveResultsValue: MutableState<Double>,
                  equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue: MutableState<Double>,
                  equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue: MutableState<Double>,
                  equityChangesStatementItemConsolidatedEquityValue: MutableState<Double>,
                  isEquityChangesStatementItemsEditItemScreen: MutableState<Boolean>,
                  isItemsPageDeleteItem: MutableState<Boolean>) {


    val itemModifier = Modifier
        .size(width = 150.dp, height = 200.dp)

    LazyHorizontalGrid(
        rows = GridCells.Fixed(equityChangesItems.size+2),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        item {
            CellTitleRowCard(stringResource(R.string.equity_changes_statement_item_type_grid),itemModifier)
        }
        val item1 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_INITIAL_BALANCES)
        if (item1.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_INITIAL_BALANCES,itemModifier,
                    item1,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item2 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CAPITAL_INCREASE)
        if (item2.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CAPITAL_INCREASE,itemModifier,
                    item2,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item3 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_EXPENSES_ON_STOCK_ISSUANCE)
        if (item3.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_EXPENSES_ON_STOCK_ISSUANCE,itemModifier,
                    item3,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item4 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RECOGNIZED_GRANTED_OPTIONS)
        if (item4.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RECOGNIZED_GRANTED_OPTIONS,itemModifier,
                    item4,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item5 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TREASURY_SHARES_ACQUIRED)
        if (item5.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TREASURY_SHARES_ACQUIRED,itemModifier,
                    item5,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item6 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TREASURY_SHARES_SOLD)
        if (item6.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TREASURY_SHARES_SOLD,itemModifier,
                    item6,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item7 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_DIVIDENDS)
        if (item7.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_DIVIDENDS,itemModifier,
                    item7,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item8 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CAPITAL_TRANSACTIONS_WITH_SHAREHOLDERS)
        if (item8.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CAPITAL_TRANSACTIONS_WITH_SHAREHOLDERS,itemModifier,
                    item8,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item9 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_NET_PROFIT_FOR_PERIOD)
        if (item9.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_NET_PROFIT_FOR_PERIOD,itemModifier,
                    item9,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item10 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_FINANCIAL_INSTRUMENTS_ADJUSTMENTS)
        if (item10.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_FINANCIAL_INSTRUMENTS_ADJUSTMENTS,itemModifier,
                    item10,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item11 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_FINANCIAL_INSTRUMENT_ADJUSTMENTS)
        if (item11.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_FINANCIAL_INSTRUMENT_ADJUSTMENTS,itemModifier,
                    item11,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item12 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_EQUITY_IN_COMPREHENSIVE_INCOME_OF_AFFILIATES)
        if (item12.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_EQUITY_IN_COMPREHENSIVE_INCOME_OF_AFFILIATES,itemModifier,
                    item12,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item13 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_PERIOD_CONVERSION_ADJUSTMENTS)
        if (item13.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_PERIOD_CONVERSION_ADJUSTMENTS,itemModifier,
                    item13,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item14 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_PERIOD_CONVERSION_ADJUSTMENTS)
        if (item14.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_PERIOD_CONVERSION_ADJUSTMENTS,itemModifier,
                    item14,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item15 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_OTHER_COMPREHENSIVE_RESULTS)
        if (item15.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_OTHER_COMPREHENSIVE_RESULTS,itemModifier,
                    item15,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item16 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RECLASSIFICATION_BY_RESULT_FINANCIAL_INSTRUMENT_ADJUSTMENT)
        if (item16.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RECLASSIFICATION_BY_RESULT_FINANCIAL_INSTRUMENT_ADJUSTMENT,itemModifier,
                    item16,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item17 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TOTAL_COMPREHENSIVE_RESULT)
        if (item17.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TOTAL_COMPREHENSIVE_RESULT,itemModifier,
                    item17,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item18 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CREATION_OF_RESERVES)
        if (item18.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CREATION_OF_RESERVES,itemModifier,
                    item18,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item19 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RESERVATION_REVIEW)
        if (item19.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RESERVATION_REVIEW,itemModifier,
                    item19,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        val item20 = hasType(equityChangesItems,EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_REALIZATION_OF_REVALUATION_RESERVE)
        if (item20.equityChangesStatementItemId>0){
            item {
                CellTitleColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_REALIZATION_OF_REVALUATION_RESERVE,itemModifier,
                    item20,
                    equityChangesStatementItemId,
                    equityChangesStatementItemType,
                    equityChangesStatementItemPaidInCapitalValue,
                    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue,
                    equityChangesStatementItemProfitReservesValue,
                    equityChangesStatementItemAccumulatedProfitsOrLossesValue,
                    equityChangesStatementItemOtherComprehensiveResultsValue,
                    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue,
                    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue,
                    equityChangesStatementItemConsolidatedEquityValue,
                    isEquityChangesStatementItemsEditItemScreen,
                    isItemsPageDeleteItem)
            }
        }

        item {
            CellTitleFinalColumnCard(EQUITY_CHANGES_STATEMENT_ITEM_TYPE_FINAL_BALANCES,itemModifier)
        }
    }
}

data class CellValue(val label: String,val type: String)

fun orderByType(src: List<EquityChangesStatementItem>):ArrayList<EquityChangesStatementItem> {
    val ret = ArrayList<EquityChangesStatementItem>()
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_INITIAL_BALANCES })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CAPITAL_INCREASE })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_EXPENSES_ON_STOCK_ISSUANCE })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RECOGNIZED_GRANTED_OPTIONS })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TREASURY_SHARES_ACQUIRED })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TREASURY_SHARES_SOLD })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_DIVIDENDS })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CAPITAL_TRANSACTIONS_WITH_SHAREHOLDERS })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_NET_PROFIT_FOR_PERIOD })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_FINANCIAL_INSTRUMENTS_ADJUSTMENTS })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_FINANCIAL_INSTRUMENT_ADJUSTMENTS })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_EQUITY_IN_COMPREHENSIVE_INCOME_OF_AFFILIATES })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_PERIOD_CONVERSION_ADJUSTMENTS })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_PERIOD_CONVERSION_ADJUSTMENTS })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_OTHER_COMPREHENSIVE_RESULTS })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RECLASSIFICATION_BY_RESULT_FINANCIAL_INSTRUMENT_ADJUSTMENT })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TOTAL_COMPREHENSIVE_RESULT })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CREATION_OF_RESERVES })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RESERVATION_REVIEW })
    ret.addAll(src.filter { it.type==EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_REALIZATION_OF_REVALUATION_RESERVE })
    return ret
}

@Composable
fun ScrollingGrid(equityChangesItems: List<EquityChangesStatementItem>) {
    val typeFinalTotal = "typeFinalTotal"

    val list: ArrayList<CellValue> = ArrayList()

    //order equityChangesItems
    val ordered = orderByType(equityChangesItems)

    list.add(CellValue(stringResource(R.string.equity_changes_statement_paidInCapitalValue),""))
    var paidInCapitalValueTotal=0.0
    ordered.forEach {
        list.add(CellValue(it.paidInCapitalValue.toScreenParenthesis(),""))
        paidInCapitalValueTotal+=it.paidInCapitalValue
    }

    list.add(CellValue(paidInCapitalValueTotal.toScreenParenthesis(),typeFinalTotal))

    list.add(CellValue(stringResource(R.string.equity_changes_statement_capitalReservesGrantedOptionsAndTreasurySharesValue),""))
    var capitalReservesGrantedOptionsAndTreasurySharesValueTotal=0.0
    ordered.forEach {
        list.add(CellValue(it.capitalReservesGrantedOptionsAndTreasurySharesValue.toScreenParenthesis(),""))
        capitalReservesGrantedOptionsAndTreasurySharesValueTotal+=it.capitalReservesGrantedOptionsAndTreasurySharesValue
    }
    list.add(CellValue(capitalReservesGrantedOptionsAndTreasurySharesValueTotal.toScreenParenthesis(),typeFinalTotal))

    var profitReservesValueTotal=0.0
    list.add(CellValue(stringResource(R.string.equity_changes_statement_profitReservesValue),""))
    ordered.forEach {
        list.add(CellValue(it.profitReservesValue.toScreenParenthesis(),""))
        profitReservesValueTotal+=it.profitReservesValue
    }
    list.add(CellValue(profitReservesValueTotal.toScreenParenthesis(),typeFinalTotal))

    list.add(CellValue(stringResource(R.string.equity_changes_statement_accumulatedProfitsOrLossesValue),""))
    var accumulatedProfitsOrLossesValueTotal=0.0
    ordered.forEach {
        list.add(CellValue(it.accumulatedProfitsOrLossesValue.toScreenParenthesis(),""))
        accumulatedProfitsOrLossesValueTotal+=it.accumulatedProfitsOrLossesValue
    }
    list.add(CellValue(accumulatedProfitsOrLossesValueTotal.toScreenParenthesis(),typeFinalTotal))

    list.add(CellValue(stringResource(R.string.equity_changes_statement_otherComprehensiveResultsValue),""))
    var otherComprehensiveResultsValueTotal=0.0
    ordered.forEach {
        list.add(CellValue(it.otherComprehensiveResultsValue.toScreenParenthesis(),""))
        otherComprehensiveResultsValueTotal+=it.otherComprehensiveResultsValue
    }
    list.add(CellValue(otherComprehensiveResultsValueTotal.toScreenParenthesis(),typeFinalTotal))

    list.add(CellValue(stringResource(R.string.equity_changes_statement_equityOfTheParentCompanyShareholdersValue),""))
    var equityOfTheParentCompanyShareholdersValueTotal=0.0
    ordered.forEach {
        list.add(CellValue(it.equityOfTheParentCompanyShareholdersValue.toScreenParenthesis(),""))
        equityOfTheParentCompanyShareholdersValueTotal+=it.equityOfTheParentCompanyShareholdersValue
    }
    list.add(CellValue(equityOfTheParentCompanyShareholdersValueTotal.toScreenParenthesis(),typeFinalTotal))

    list.add(CellValue(stringResource(R.string.equity_changes_statement_nonControllingInterestsInEquityOfSubsidiaryValue),""))
    var nonControllingInterestsInEquityOfSubsidiaryValueTotal=0.0
    ordered.forEach {
        list.add(CellValue(it.nonControllingInterestsInEquityOfSubsidiaryValue.toScreenParenthesis(),""))
        nonControllingInterestsInEquityOfSubsidiaryValueTotal+=it.nonControllingInterestsInEquityOfSubsidiaryValue
    }
    list.add(CellValue(nonControllingInterestsInEquityOfSubsidiaryValueTotal.toScreenParenthesis(),typeFinalTotal))

    list.add(CellValue(stringResource(R.string.equity_changes_statement_consolidatedEquityValue),""))
    var consolidatedEquityValueTotal=0.0
    ordered.forEach {
        list.add(CellValue(it.consolidatedEquityValue.toScreenParenthesis(),""))
        consolidatedEquityValueTotal+=it.consolidatedEquityValue
    }
    list.add(CellValue(consolidatedEquityValueTotal.toScreenParenthesis(),typeFinalTotal))

    val itemsList = list.toList()

    val itemModifier = Modifier
        .size(width = 150.dp, height = 200.dp)

    LazyHorizontalGrid(
        rows = GridCells.Fixed(ordered.size+2),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        items(itemsList) {
            if (it.type==typeFinalTotal)
                CellTitleFinalColumnCard(it.label,itemModifier)
            else
            if (isHeader(it.label))
                CellTitleRowCard(it.label,itemModifier)
            else
                CellCard(it.label,itemModifier)
        }
    }
}

fun getMissingTypes(descriptionList: List<String>): List<String>{
    val types = ArrayList(listOf(
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_INITIAL_BALANCES,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CAPITAL_INCREASE,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_EXPENSES_ON_STOCK_ISSUANCE,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RECOGNIZED_GRANTED_OPTIONS,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TREASURY_SHARES_ACQUIRED,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TREASURY_SHARES_SOLD,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_DIVIDENDS,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CAPITAL_TRANSACTIONS_WITH_SHAREHOLDERS,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_NET_PROFIT_FOR_PERIOD,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_FINANCIAL_INSTRUMENTS_ADJUSTMENTS,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_FINANCIAL_INSTRUMENT_ADJUSTMENTS,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_EQUITY_IN_COMPREHENSIVE_INCOME_OF_AFFILIATES,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_PERIOD_CONVERSION_ADJUSTMENTS,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_PERIOD_CONVERSION_ADJUSTMENTS,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_OTHER_COMPREHENSIVE_RESULTS,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RECLASSIFICATION_BY_RESULT_FINANCIAL_INSTRUMENT_ADJUSTMENT,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TOTAL_COMPREHENSIVE_RESULT,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_CREATION_OF_RESERVES,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_RESERVATION_REVIEW,
        EQUITY_CHANGES_STATEMENT_ITEM_TYPE_TAXES_ON_REALIZATION_OF_REVALUATION_RESERVE,
    ))
    types.removeAll(descriptionList.toSet())
    return types
}

@Composable
fun SignedDoubleField(fieldName: MutableState<String>,isCredit: MutableState<Boolean>,label:String,
                      paidInCapitalValue: Double,
                      capitalReservesGrantedOptionsAndTreasurySharesValue: Double,
                      profitReservesValue: Double,
                      accumulatedProfitsOrLossesValue: Double,
                      otherComprehensiveResultsValue: Double,
                      equityOfTheParentCompanyShareholdersValue: Double,
                      nonControllingInterestsInEquityOfSubsidiaryValue: Double,
                      consolidatedEquityValue: MutableState<String>,
                      ){
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start,
    )
    {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
    )
    {
        OutlinedTextField(
            value = fieldName.value,
            onValueChange = {
                fieldName.value = it
                consolidatedEquityValue.value = (paidInCapitalValue +
                        capitalReservesGrantedOptionsAndTreasurySharesValue +
                        profitReservesValue +
                        accumulatedProfitsOrLossesValue +
                        otherComprehensiveResultsValue +
                        equityOfTheParentCompanyShareholdersValue +
                        nonControllingInterestsInEquityOfSubsidiaryValue).toScreenParenthesis()
            },
            placeholder = { Text("") },
            label = {
                Text(
                    text = "Valor:",
                )
            },modifier = Modifier.fillMaxWidth(0.5f)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(8.dp)
        )
        {
            Switch(
                checked = isCredit.value,
                onCheckedChange = { isCredit.value = it },
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = if (isCredit.value) stringResource(R.string.cash_flows_statement_credit) else stringResource(R.string.cash_flows_statement_debit),
                maxLines = 1,
                style = TextStyle(fontFamily = FontFamily.SansSerif),

            )
        }
    }
}

fun getSignedDouble(text: String, isCredit: Boolean): Double {
    if (isCredit)
        return text.screenToDouble().absoluteValue
    return text.screenToDouble().absoluteValue * -1
}

/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun EquityChangesStatementItemsAddItemScreenContent(
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    modifier: Modifier = Modifier,
    equityChangesStatementName: MutableState<String>,
    equityChangesStatementId: MutableState<Long>,
    equityChangesStatementStartDate: MutableState<String>,
    equityChangesStatementEndDate: MutableState<String>,
    isEquityChangesStatementItemsAddItemScreen: MutableState<Boolean>,
    equityChangesStatementDescriptionList: MutableList<String>,
) {

    val context = LocalContext.current
    val equityChangeItemType = remember { mutableStateOf("") }
    val paidInCapitalValue = remember { mutableStateOf("") }
    val capitalReservesGrantedOptionsAndTreasurySharesValue = remember { mutableStateOf("") }
    val profitReservesValue = remember { mutableStateOf("") }
    val accumulatedProfitsOrLossesValue = remember { mutableStateOf("") }
    val otherComprehensiveResultsValue = remember { mutableStateOf("") }
    val equityOfTheParentCompanyShareholdersValue = remember { mutableStateOf("") }
    val nonControllingInterestsInEquityOfSubsidiaryValue = remember { mutableStateOf("") }
    val consolidatedEquityValue = remember { mutableStateOf("") }

    val paidInCapitalValueIsCredit = remember { mutableStateOf(true) }
    val capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit = remember { mutableStateOf(true) }
    val profitReservesValueIsCredit = remember { mutableStateOf(true) }
    val accumulatedProfitsOrLossesValueIsCredit = remember { mutableStateOf(true) }
    val otherComprehensiveResultsValueIsCredit = remember { mutableStateOf(true) }
    val equityOfTheParentCompanyShareholdersValueIsCredit = remember { mutableStateOf(true) }
    val nonControllingInterestsInEquityOfSubsidiaryValueIsCredit = remember { mutableStateOf(true) }

    val equityChangeItemTypes = getMissingTypes(equityChangesStatementDescriptionList)

    consolidatedEquityValue.value = (getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value) +
            getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value) +
            getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value) +
            getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value) +
            getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value) +
            getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value) +
            getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value)).toScreenParenthesis()

    val focusManager = LocalFocusManager.current

    Column(modifier) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .verticalScroll(rememberScrollState()),
        ) {

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth())
            {
                Text(
                    text = equityChangesStatementName.value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = equityChangesStatementStartDate.value + " - " + equityChangesStatementEndDate.value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )
            Spacer(Modifier.height(10.dp))

            TypeDropdown(equityChangeItemType,equityChangeItemTypes)
            Spacer(Modifier.height(10.dp))

            SignedDoubleField(paidInCapitalValue,paidInCapitalValueIsCredit,stringResource(R.string.equity_changes_statement_paidInCapitalValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)
            SignedDoubleField(capitalReservesGrantedOptionsAndTreasurySharesValue,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit,stringResource(R.string.equity_changes_statement_capitalReservesGrantedOptionsAndTreasurySharesValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)
            SignedDoubleField(profitReservesValue,profitReservesValueIsCredit,stringResource(R.string.equity_changes_statement_accumulatedProfitsOrLossesValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)
            SignedDoubleField(accumulatedProfitsOrLossesValue,accumulatedProfitsOrLossesValueIsCredit,stringResource(R.string.equity_changes_statement_profitReservesValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)
            SignedDoubleField(otherComprehensiveResultsValue,otherComprehensiveResultsValueIsCredit,stringResource(R.string.equity_changes_statement_otherComprehensiveResultsValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)
            SignedDoubleField(equityOfTheParentCompanyShareholdersValue,equityOfTheParentCompanyShareholdersValueIsCredit,stringResource(R.string.equity_changes_statement_equityOfTheParentCompanyShareholdersValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)
            SignedDoubleField(nonControllingInterestsInEquityOfSubsidiaryValue,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit,stringResource(R.string.equity_changes_statement_nonControllingInterestsInEquityOfSubsidiaryValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start,
            )
            {
                Text(
                    text = stringResource(R.string.equity_changes_statement_consolidatedEquityValue),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start,
            )
            {
                OutlinedTextField(
                    value = consolidatedEquityValue.value,
                    onValueChange = {

                    },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = "Valor:",
                        )
                    },modifier = Modifier.onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            focusManager.clearFocus()
                        }
                    }
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        isEquityChangesStatementItemsAddItemScreen.value = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

                val itemAddedSuccess = stringResource(R.string.equity_changes_statement_item_added_success)
                val emptyTypeMsg = stringResource(R.string.equity_changes_statement_item_missing_type)

                Button(
                    onClick = {

                        if (equityChangeItemType.value.isEmpty()){
                            Toast.makeText(
                                context,
                                emptyTypeMsg,
                                Toast.LENGTH_SHORT,
                            ).show()
                        }else

                            try {

                                val consolidatedEquityValueSaved = getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value) +
                                        getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value) +
                                        getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value) +
                                        getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value) +
                                        getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value) +
                                        getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value) +
                                        getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value)

                                equityChangesStatementViewModel.saveEquityChangesStatementItem(
                                    EquityChangesStatementItem(0,
                                        equityChangesStatementId.value,
                                        equityChangeItemType.value,
                                        getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                                        getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                                        getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                                        getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                                        getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                                        getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                                        getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                                        consolidatedEquityValueSaved,
                                        0))

                                isEquityChangesStatementItemsAddItemScreen.value = false
                                Toast.makeText(
                                    context,
                                    itemAddedSuccess,
                                    Toast.LENGTH_SHORT,
                                    ).show()

                            }catch (e: Exception){
                                Toast.makeText(
                                    context,
                                    e.message,
                                    Toast.LENGTH_SHORT,
                                    ).show()
                            }

                    },
                ) {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_save),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
            }
        }
    }

}


/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun EquityChangesStatementItemsEditItemScreenContent(
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    modifier: Modifier = Modifier,
    equityChangesStatementName: MutableState<String>,
    equityChangesStatementId: MutableState<Long>,
    equityChangesStatementStartDate: MutableState<String>,
    equityChangesStatementEndDate: MutableState<String>,
    isEquityChangesStatementItemsEditItemScreen: MutableState<Boolean>,
    equityChangesStatementItemId: MutableState<Long>,
    equityChangesStatementItemType: MutableState<String>,
    equityChangesStatementItemPaidInCapitalValue: MutableState<Double>,
    equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue: MutableState<Double>,
    equityChangesStatementItemProfitReservesValue: MutableState<Double>,
    equityChangesStatementItemAccumulatedProfitsOrLossesValue: MutableState<Double>,
    equityChangesStatementItemOtherComprehensiveResultsValue: MutableState<Double>,
    equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue: MutableState<Double>,
    equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue: MutableState<Double>,
    equityChangesStatementItemConsolidatedEquityValue: MutableState<Double>,
    equityChangesStatementDescriptionList: MutableList<String>,
) {

    val context = LocalContext.current
    val equityChangeItemType = remember { mutableStateOf(equityChangesStatementItemType.value) }
    val paidInCapitalValue = remember { mutableStateOf(equityChangesStatementItemPaidInCapitalValue.value.absoluteValue.toScreen()) }
    val capitalReservesGrantedOptionsAndTreasurySharesValue = remember { mutableStateOf(equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue.value.absoluteValue.toScreen()) }
    val profitReservesValue = remember { mutableStateOf(equityChangesStatementItemProfitReservesValue.value.absoluteValue.toScreen()) }
    val accumulatedProfitsOrLossesValue = remember { mutableStateOf(equityChangesStatementItemAccumulatedProfitsOrLossesValue.value.absoluteValue.toScreen()) }
    val otherComprehensiveResultsValue = remember { mutableStateOf(equityChangesStatementItemOtherComprehensiveResultsValue.value.absoluteValue.toScreen()) }
    val equityOfTheParentCompanyShareholdersValue = remember { mutableStateOf(equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue.value.absoluteValue.toScreen()) }
    val nonControllingInterestsInEquityOfSubsidiaryValue = remember { mutableStateOf(equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue.value.absoluteValue.toScreen()) }
    val consolidatedEquityValue = remember { mutableStateOf(equityChangesStatementItemConsolidatedEquityValue.value.toScreenParenthesis()) }

    val paidInCapitalValueIsCredit = remember { mutableStateOf(equityChangesStatementItemPaidInCapitalValue.value>=0.0) }
    val capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit = remember { mutableStateOf(equityChangesStatementItemCapitalReservesGrantedOptionsAndTreasurySharesValue.value>=0.0) }
    val profitReservesValueIsCredit = remember { mutableStateOf(equityChangesStatementItemProfitReservesValue.value>=0.0) }
    val accumulatedProfitsOrLossesValueIsCredit = remember { mutableStateOf(equityChangesStatementItemAccumulatedProfitsOrLossesValue.value>=0.0) }
    val otherComprehensiveResultsValueIsCredit = remember { mutableStateOf(equityChangesStatementItemOtherComprehensiveResultsValue.value>=0.0) }
    val equityOfTheParentCompanyShareholdersValueIsCredit = remember { mutableStateOf(equityChangesStatementItemEquityOfTheParentCompanyShareholdersValue.value>=0.0) }
    val nonControllingInterestsInEquityOfSubsidiaryValueIsCredit = remember { mutableStateOf(equityChangesStatementItemNonControllingInterestsInEquityOfSubsidiaryValue.value>=0.0) }

    consolidatedEquityValue.value = (getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value) +
            getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value) +
            getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value) +
            getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value) +
            getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value) +
            getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value) +
            getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value)).toScreenParenthesis()


    val equityChangeItemTypes = getMissingTypes(equityChangesStatementDescriptionList)
    val focusManager = LocalFocusManager.current

    Column(modifier) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .verticalScroll(rememberScrollState()),
        ) {

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth())
            {
                Text(
                    text = equityChangesStatementName.value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = equityChangesStatementStartDate.value + " - " + equityChangesStatementEndDate.value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )
            Spacer(Modifier.height(10.dp))

            TypeDropdown(equityChangeItemType,equityChangeItemTypes)

            Spacer(Modifier.height(10.dp))

            SignedDoubleField(paidInCapitalValue,paidInCapitalValueIsCredit,stringResource(R.string.equity_changes_statement_paidInCapitalValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)
            SignedDoubleField(capitalReservesGrantedOptionsAndTreasurySharesValue,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit,stringResource(R.string.equity_changes_statement_capitalReservesGrantedOptionsAndTreasurySharesValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)
            SignedDoubleField(profitReservesValue,profitReservesValueIsCredit,stringResource(R.string.equity_changes_statement_accumulatedProfitsOrLossesValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)
            SignedDoubleField(accumulatedProfitsOrLossesValue,accumulatedProfitsOrLossesValueIsCredit,stringResource(R.string.equity_changes_statement_profitReservesValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)
            SignedDoubleField(otherComprehensiveResultsValue,otherComprehensiveResultsValueIsCredit,stringResource(R.string.equity_changes_statement_otherComprehensiveResultsValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)
            SignedDoubleField(equityOfTheParentCompanyShareholdersValue,equityOfTheParentCompanyShareholdersValueIsCredit,stringResource(R.string.equity_changes_statement_equityOfTheParentCompanyShareholdersValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)
            SignedDoubleField(nonControllingInterestsInEquityOfSubsidiaryValue,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit,stringResource(R.string.equity_changes_statement_nonControllingInterestsInEquityOfSubsidiaryValue),
                getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                consolidatedEquityValue)

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start,
            )
            {
                Text(
                    text = stringResource(R.string.equity_changes_statement_consolidatedEquityValue),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start,
            )
            {
                OutlinedTextField(
                    value = consolidatedEquityValue.value,
                    onValueChange = {

                    },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = "Valor:",
                        )
                    },modifier = Modifier.onFocusChanged { focusState ->
                        if (focusState.isFocused){
                            focusManager.clearFocus()
                        }
                    },
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        isEquityChangesStatementItemsEditItemScreen.value = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

                val changedSuccess = stringResource(R.string.equity_changes_statement_item_changed_success)

                Button(
                    onClick = {

                            try {
                                val consolidatedEquityValueSaved = getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value) +
                                        getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value) +
                                        getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value) +
                                        getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value) +
                                        getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value) +
                                        getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value) +
                                        getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value)

                                equityChangesStatementViewModel.saveEquityChangesStatementItem(
                                    EquityChangesStatementItem(equityChangesStatementItemId.value,
                                        equityChangesStatementId.value,
                                        equityChangeItemType.value,
                                        getSignedDouble(paidInCapitalValue.value,paidInCapitalValueIsCredit.value),
                                        getSignedDouble(capitalReservesGrantedOptionsAndTreasurySharesValue.value,capitalReservesGrantedOptionsAndTreasurySharesValueIsCredit.value),
                                        getSignedDouble(profitReservesValue.value,profitReservesValueIsCredit.value),
                                        getSignedDouble(accumulatedProfitsOrLossesValue.value,accumulatedProfitsOrLossesValueIsCredit.value),
                                        getSignedDouble(otherComprehensiveResultsValue.value,otherComprehensiveResultsValueIsCredit.value),
                                        getSignedDouble(equityOfTheParentCompanyShareholdersValue.value,equityOfTheParentCompanyShareholdersValueIsCredit.value),
                                        getSignedDouble(nonControllingInterestsInEquityOfSubsidiaryValue.value,nonControllingInterestsInEquityOfSubsidiaryValueIsCredit.value),
                                        consolidatedEquityValueSaved,
                                        0))

                                isEquityChangesStatementItemsEditItemScreen.value = false
                                Toast.makeText(
                                    context,
                                    changedSuccess,
                                    Toast.LENGTH_SHORT,
                                ).show()

                            }catch (e: Exception){
                                Toast.makeText(
                                    context,
                                    e.message,
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                    },
                ) {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_save),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun TypeDropdown(equityChangeType: MutableState<String>, typeList: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Row {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                },
            value = equityChangeType.value,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary,fontFamily = FontFamily.SansSerif),
            onValueChange = {

            },
            placeholder = { Text("") },
            label = {
                Row (verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()){
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        IconButton(onClick = { expanded = !expanded },
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(24.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "")
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            typeList.forEach { item ->

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = item,
                                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                    },
                                    onClick = {
                                        equityChangeType.value = item
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = stringResource(R.string.equity_changes_statement_item_type),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

            }, enabled = false
        )
    }
}


/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun EquityChangesStatementHomeScreenContent(
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    modifier: Modifier = Modifier,
    isEquityChangesStatementEditScreen: MutableState<Boolean>,
    equityChangesStatementName: MutableState<String>,
    equityChangesStatementId: MutableState<Long>,
    equityChangesStatementStartDate: MutableState<String>,
    equityChangesStatementEndDate: MutableState<String>,
    isEquityChangesStatementItemsScreen: MutableState<Boolean>,
) {

    val context = LocalContext.current
    val isDeleting = remember { mutableStateOf(false) }
    val fmt = SimpleDateFormat(stringResource(R.string.fmtDatePattern))

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
            val equityChangesStatementsFlow = equityChangesStatementViewModel.getEquityChangesStatements()
            val equityChangesStatements by equityChangesStatementsFlow.collectAsStateWithLifecycle(initialValue = emptyList())

            equityChangesStatements.forEach { equityChangesStatement ->

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = false,
                            onClick = {
                                isEquityChangesStatementEditScreen.value=true
                                equityChangesStatementName.value=equityChangesStatement.name
                                equityChangesStatementId.value=equityChangesStatement.equityChangesStatementId
                                equityChangesStatementStartDate.value = fmt.format(equityChangesStatement.startDate)
                                equityChangesStatementEndDate.value = fmt.format(equityChangesStatement.endDate)
                            },
                            role = Role.Button
                        )
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth(0.7f)
                            .padding(horizontal = 10.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .fillMaxWidth()) {
                            Text(
                                text = equityChangesStatement.name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            Text(
                                text = fmt.format(equityChangesStatement.startDate) + " - " + fmt.format(equityChangesStatement.endDate),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }

                    TextButton(
                        modifier = Modifier.padding(3.dp),
                        onClick =
                            {
                                isEquityChangesStatementItemsScreen.value = true
                                equityChangesStatementId.value = equityChangesStatement.equityChangesStatementId
                                equityChangesStatementName.value = equityChangesStatement.name
                                equityChangesStatementStartDate.value = fmt.format(equityChangesStatement.startDate)
                                equityChangesStatementEndDate.value = fmt.format(equityChangesStatement.endDate)
                            }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.format_list_bulleted_24px),
                            contentDescription = stringResource(R.string.cash_flows_statement_items),
                            modifier = Modifier
                        )
                    }

                    TextButton(
                        modifier = Modifier.padding(3.dp),
                        onClick =
                            {
                                isDeleting.value = true
                                equityChangesStatementId.value = equityChangesStatement.equityChangesStatementId
                                equityChangesStatementName.value = equityChangesStatement.name
                            }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.delete_24px),
                            contentDescription = stringResource(R.string.cash_flows_statement_delete),
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }

    when {
        isDeleting.value -> {
            EquityChangesStatementDeleteDialog(isDeleting, equityChangesStatementId, equityChangesStatementName, equityChangesStatementViewModel,context)
        }
    }
}



/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun EquityChangesStatementAddScreenContent(
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    modifier: Modifier = Modifier,
    isEquityChangesStatementAddScreen: MutableState<Boolean>
) {

    val equityChangesStatementName = remember { mutableStateOf("") }
    val context = LocalContext.current

    val openStartDateCreateDialog= remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf("") }
    val openEndDateCreateDialog= remember { mutableStateOf(false) }
    var endDate by remember { mutableStateOf("") }
    val fmt = SimpleDateFormat(stringResource(R.string.fmtDatePattern))
    val focusManager = LocalFocusManager.current

    Column(modifier) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .verticalScroll(rememberScrollState()),
        ) {

            OutlinedTextField(
                value = equityChangesStatementName.value,
                onValueChange = {
                    equityChangesStatementName.value = it
                },
                placeholder = { Text("") },
                label = {
                    Text(
                        text = stringResource(R.string.cash_flows_statement_name),
                    )
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier=Modifier.fillMaxWidth().selectable(
                    selected = false,
                    onClick = {
                        openStartDateCreateDialog.value=true
                    },
                    role = Role.Button
                )
            ) {

                OutlinedTextField(
                    value = startDate,
                    onValueChange =
                        {
                            startDate = it
                        },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = stringResource(R.string.cash_flows_statement_startDate),
                        )
                    },
                    modifier = Modifier.onFocusChanged { focusState ->
                        if (focusState.isFocused){
                            openStartDateCreateDialog.value=true
                            focusManager.clearFocus()
                        }
                    },
                )
                TextButton(
                    modifier = Modifier.padding(3.dp),
                    onClick =
                        {
                            openStartDateCreateDialog.value=true
                        }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.calendar_month_24px),
                        contentDescription = stringResource(R.string.cash_flows_statement_startDate),
                        modifier = Modifier
                    )
                }

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier=Modifier.fillMaxWidth().selectable(
                    selected = false,
                    onClick = {
                        openEndDateCreateDialog.value=true
                    },
                    role = Role.Button
                )
            )
            {
                OutlinedTextField(
                    value = endDate,
                    onValueChange =
                        {
                            endDate = it
                        },
                    placeholder = { Text("") },
                    label =
                        {
                            Text(
                                text = stringResource(R.string.cash_flows_statement_endDate),
                            )
                        },
                    modifier = Modifier.onFocusChanged { focusState ->
                        if (focusState.isFocused){
                            openEndDateCreateDialog.value=true
                            focusManager.clearFocus()
                        }
                    },
                )
                TextButton(
                    modifier = Modifier.padding(3.dp),
                    onClick =
                        {
                            openEndDateCreateDialog.value=true
                        }
                )
                {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.calendar_month_24px),
                        contentDescription = stringResource(R.string.cash_flows_statement_endDate),
                        modifier = Modifier
                    )
                }
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().border(width = 5.dp, Color(0x00C0D1DF))
            ) {
                Button(
                    onClick =
                        {
                            isEquityChangesStatementAddScreen.value = false
                            startDate = ""
                            endDate = ""
                        },
                )
                {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
                val sucessMsg = stringResource(R.string.cash_flows_statement_added_sucess)
                val missingNameMsg = stringResource(R.string.cash_flows_statement_missing_name)
                val missingStartDateMsg = stringResource(R.string.cash_flows_statement_missing_startdate)
                val missingEndDateMsg = stringResource(R.string.cash_flows_statement_missing_enddate)
                Button(
                    onClick =
                        {
                            if (equityChangesStatementName.value.isEmpty()){
                                Toast.makeText(
                                    context,
                                    missingNameMsg,
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }else
                                if (startDate.isEmpty()){
                                    Toast.makeText(
                                        context,
                                        missingStartDateMsg,
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }else
                                    if (endDate.isEmpty()){
                                        Toast.makeText(
                                            context,
                                            missingEndDateMsg,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }else
                                    {
                                        try {
                                            equityChangesStatementViewModel.saveEquityChangesStatement(
                                                EquityChangesStatement(
                                                    0,
                                                    equityChangesStatementName.value,
                                                    fmt.parse(startDate)!!,
                                                    fmt.parse(endDate)!!,
                                                    0
                                                )
                                            )
                                            Toast.makeText(
                                                context,
                                                sucessMsg,
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                            isEquityChangesStatementAddScreen.value = false
                                            startDate = ""
                                            endDate = ""
                                        }catch (e: Exception){
                                            Toast.makeText(
                                                context,
                                                e.message,
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                        }
                                    }
                        }
                )
                {
                    Text(
                        text = stringResource(R.string.equity_changes_statement_save),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
            }
        }
    }

    when {
        openStartDateCreateDialog.value -> {
            val fmt = stringResource(R.string.fmtDatePattern)
            DatePickerModal(
                onDateSelected = {
                    if (it != null) {
                        startDate = SimpleDateFormat(fmt).format(Date(it))
                    }
                },
                openDialog = openStartDateCreateDialog, title = stringResource(R.string.cash_flows_statement_startDate)
            )
        }

        openEndDateCreateDialog.value -> {
            val fmt = stringResource(R.string.fmtDatePattern)
            DatePickerModal(
                onDateSelected = {
                    if (it != null) {
                        endDate = SimpleDateFormat(fmt).format(Date(it))
                    }
                },
                openDialog = openEndDateCreateDialog, title = stringResource(R.string.cash_flows_statement_endDate)
            )
        }
    }

}


@Composable
fun EquityChangesStatementDeleteDialog(
    isDeleting: MutableState<Boolean>,
    isDeletingId: MutableState<Long>,
    isDeletingName: MutableState<String>,
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    context: Context
) {
    if (isDeleting.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                isDeleting.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(500.dp),

            title = {
                Text(
                    text = stringResource(R.string.cash_flows_statement_delete_cash_flows_statement),
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(2.dp)

                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Text(
                            text = isDeletingName.value
                        )
                    }

                }
            },
            confirmButton = {


            }, dismissButton = {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            isDeleting.value = false
                            isDeletingId.value = 0
                            isDeletingName.value = ""
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.equity_changes_statement_cancel),
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    val deleteSucessMsg = stringResource(R.string.cash_flows_statement_deleted_sucess)
                    Button(
                        onClick = {
                            equityChangesStatementViewModel.deleteEquityChangesStatement(EquityChangesStatement(isDeletingId.value,isDeletingName.value, Date(0), Date(0), 0))
                            isDeleting.value = false
                            isDeletingId.value = 0
                            isDeletingName.value = ""
                            Toast.makeText(
                                context,
                                deleteSucessMsg,
                                Toast.LENGTH_SHORT,
                            ).show()
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.cash_flows_statement_delete),
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }
                }
            }
        )
    }
}


@Composable
fun EquityChangesStatementItemDeleteDialog(
    isDeletingEquityChangesStatementItem: MutableState<Boolean>,
    isDeletingEquityChangesStatementItemId: MutableState<Long>,
    isDeletingEquityChangesStatementItemType: MutableState<String>,
    equityChangesStatementViewModel: EquityChangesStatementViewModel,
    context: Context
) {
    if (isDeletingEquityChangesStatementItem.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                isDeletingEquityChangesStatementItem.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(500.dp),

            title = {
                Text(
                    text = stringResource(R.string.equity_changes_statement_item_delete_title),
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(2.dp)

                ) {
                    Text(
                        text = isDeletingEquityChangesStatementItemType.value
                    )
                }
            },
            confirmButton = {


            }, dismissButton = {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            isDeletingEquityChangesStatementItem.value = false
                            isDeletingEquityChangesStatementItemId.value=0
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.equity_changes_statement_cancel),
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    val deletedSuccessMsg = stringResource(R.string.equity_changes_statement_item_deleted_success)
                    Button(
                        onClick = {
                            equityChangesStatementViewModel.deleteEquityChangesStatementItem(EquityChangesStatementItem(isDeletingEquityChangesStatementItemId.value,0, "", 0.0, 0.0,0.0,0.0,0.0,0.0,0.0,0.0,0))
                            isDeletingEquityChangesStatementItem.value = false
                            isDeletingEquityChangesStatementItemId.value=0
                            isDeletingEquityChangesStatementItemType.value=""

                            Toast.makeText(
                                context,
                                deletedSuccessMsg,
                                Toast.LENGTH_SHORT,
                            ).show()
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.equity_changes_statement_item_delete),
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }
                }
            }
        )
    }
}