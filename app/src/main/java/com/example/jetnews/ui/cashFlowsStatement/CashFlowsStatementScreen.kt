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

package com.example.jetnews.ui.cashFlowsStatement

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetnews.R
import com.example.jetnews.data.cashFlowsStatement.CashFlowStatement
import com.example.jetnews.data.cashFlowsStatement.CashFlowStatementItem
import com.example.jetnews.ui.accountingAccounts.AccountingAccountsViewModel
import com.example.jetnews.ui.accountingAccounts.displayEachExpandableTitleRow
import com.example.jetnews.ui.accountingAccounts.getResultTypes
import com.example.jetnews.ui.utils.DatePickerModal
import com.example.jetnews.ui.utils.screenToDouble
import com.example.jetnews.ui.utils.toScreen
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_FINANCING
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_INVESTING
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_OPERATING
import com.example.jetnews.utils.CASH_FLOWS_STATEMENT_TYPE_FINANCING
import com.example.jetnews.utils.CASH_FLOWS_STATEMENT_TYPE_INVESTING
import com.example.jetnews.utils.CASH_FLOWS_STATEMENT_TYPE_OPERATING
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.Boolean
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
fun CashFlowsStatementScreen(
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    accountingAccountsViewModel: AccountingAccountsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isCashFlowsStatementAddScreen = remember { mutableStateOf(false) }
    val isCashFlowsStatementEditScreen = remember { mutableStateOf(false) }
    val isCashFlowsStatementItemsAddItemScreen = remember { mutableStateOf(false) }
    val isCashFlowsStatementItemsEditItemScreen = remember { mutableStateOf(false) }
    val isCashFlowsStatementItemsScreen = remember { mutableStateOf(false) }

    val cashFlowsStatementName = remember { mutableStateOf("") }
    val cashFlowsStatementId = remember { mutableLongStateOf(0) }
    val cashFlowsStatementStartDate = remember { mutableStateOf("") }
    val cashFlowsStatementEndDate = remember { mutableStateOf("") }
    val cashFlowsStatementInitialCashBalance = remember { mutableDoubleStateOf(0.0) }
    val cashFlowsStatementInitialCashBalanceIsCredit = remember { mutableStateOf(true) }

    val cashFlowsStatementItemId = remember { mutableLongStateOf(0) }
    val cashFlowsStatementItemType = remember { mutableStateOf("") }
    val cashFlowsStatementItemDescription = remember { mutableStateOf("") }
    val cashFlowsStatementItemDate = remember { mutableStateOf("") }
    val cashFlowsStatementItemValue = remember { mutableDoubleStateOf(0.0) }
    val cashFlowsStatementItemIsCredit = remember { mutableStateOf(false) }

    when {
        isCashFlowsStatementAddScreen.value -> {
            CashFlowsStatementAddScreen(
                cashFlowsStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isCashFlowsStatementAddScreen
            )
        }
        isCashFlowsStatementEditScreen.value -> {
            CashFlowsStatementEditScreen(
                cashFlowsStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isCashFlowsStatementEditScreen,
                cashFlowsStatementName,
                cashFlowsStatementId,
                cashFlowsStatementStartDate,
                cashFlowsStatementEndDate
            )
        }
        isCashFlowsStatementItemsEditItemScreen.value -> {
            CashFlowsStatementItemsEditItemScreen(
                cashFlowsStatementViewModel,
               accountingAccountsViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                cashFlowsStatementName,
                cashFlowsStatementId,
                cashFlowsStatementStartDate,
                cashFlowsStatementEndDate,
                isCashFlowsStatementItemsEditItemScreen,
                cashFlowsStatementItemId,
                cashFlowsStatementItemType,
                cashFlowsStatementItemDescription,
                cashFlowsStatementItemDate,
                cashFlowsStatementItemValue,
                cashFlowsStatementItemIsCredit
            )
        }
        isCashFlowsStatementItemsAddItemScreen.value -> {
            CashFlowsStatementItemsAddItemScreen(
                cashFlowsStatementViewModel,
                accountingAccountsViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                cashFlowsStatementName,
                cashFlowsStatementId,
                cashFlowsStatementStartDate,
                cashFlowsStatementEndDate,
                isCashFlowsStatementItemsAddItemScreen
            )
        }
        isCashFlowsStatementItemsScreen.value -> {
            CashFlowsStatementItemsScreen(
                cashFlowsStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isCashFlowsStatementItemsScreen,
                cashFlowsStatementName,
                cashFlowsStatementId,
                cashFlowsStatementStartDate,
                cashFlowsStatementEndDate,
                cashFlowsStatementInitialCashBalance,
                cashFlowsStatementInitialCashBalanceIsCredit,
                isCashFlowsStatementItemsAddItemScreen,
                isCashFlowsStatementItemsEditItemScreen,
                cashFlowsStatementItemId,
                cashFlowsStatementItemType,
                cashFlowsStatementItemDescription,
                cashFlowsStatementItemDate,
                cashFlowsStatementItemValue,
                cashFlowsStatementItemIsCredit,
            )
        }
        else -> {
            CashFlowsStatementHomeScreen(
                cashFlowsStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isCashFlowsStatementAddScreen,
                isCashFlowsStatementEditScreen,
                cashFlowsStatementName,
                cashFlowsStatementId,
                cashFlowsStatementStartDate,
                cashFlowsStatementEndDate,
                isCashFlowsStatementItemsScreen,
                cashFlowsStatementInitialCashBalance,
                cashFlowsStatementInitialCashBalanceIsCredit
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
fun CashFlowsStatementHomeScreen(
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isCashFlowsStatementAddScreen: MutableState<Boolean>,
    isCashFlowsStatementEditScreen: MutableState<Boolean>,
    cashFlowsStatementName: MutableState<String>,
    cashFlowsStatementId: MutableState<Long>,
    cashFlowsStatementStartDate: MutableState<String>,
    cashFlowsStatementEndDate: MutableState<String>,
    isCashFlowsStatementItemsScreen: MutableState<Boolean>,
    cashFlowsStatementInitialCashBalance: MutableState<Double>,
    cashFlowsStatementInitialCashBalanceIsCredit: MutableState<Boolean>,
) {


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.cash_flows_statement_title),
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
                            isCashFlowsStatementAddScreen.value=true
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = stringResource(R.string.addCashFlowsStatement),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        CashFlowsStatementHomeScreenContent(
            cashFlowsStatementViewModel,
            screenModifier,
            isCashFlowsStatementEditScreen,
            cashFlowsStatementName,
            cashFlowsStatementId,
            cashFlowsStatementStartDate,
            cashFlowsStatementEndDate,
            isCashFlowsStatementItemsScreen,
            cashFlowsStatementInitialCashBalance,
            cashFlowsStatementInitialCashBalanceIsCredit,
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
fun CashFlowsStatementAddScreen(
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isCashFlowsStatementAddScreen: MutableState<Boolean>
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.addCashFlowsStatement),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isCashFlowsStatementAddScreen.value=false
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
        CashFlowsStatementAddScreenContent(
            cashFlowsStatementViewModel,
            screenModifier,
            isCashFlowsStatementAddScreen
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
fun CashFlowsStatementEditScreen(
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isCashFlowsStatementEditScreen: MutableState<Boolean>,
    cashFlowsStatementName: MutableState<String>,
    cashFlowsStatementId: MutableState<Long>,
    cashFlowsStatementStartDate: MutableState<String>,
    cashFlowsStatementEndDate: MutableState<String>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.editCashFlowsStatement),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isCashFlowsStatementEditScreen.value=false
                        cashFlowsStatementName.value=""
                        cashFlowsStatementId.value=0
                        cashFlowsStatementStartDate.value=""
                        cashFlowsStatementEndDate.value=""
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
        CashFlowsStatementEditScreenContent(
            cashFlowsStatementViewModel,
            screenModifier,
            isCashFlowsStatementEditScreen,
            cashFlowsStatementName,
            cashFlowsStatementId,
            cashFlowsStatementStartDate,
            cashFlowsStatementEndDate,
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
fun CashFlowsStatementItemsScreen(
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isCashFlowsStatementItemsScreen: MutableState<Boolean>,
    cashFlowsStatementName: MutableState<String>,
    cashFlowsStatementId: MutableState<Long>,
    cashFlowsStatementStartDate: MutableState<String>,
    cashFlowsStatementEndDate: MutableState<String>,
    cashFlowsStatementInitialCashBalance: MutableState<Double>,
    cashFlowsStatementInitialCashBalanceIsCredit: MutableState<Boolean>,
    isCashFlowsStatementItemsAddItemScreen: MutableState<Boolean>,
    isCashFlowsStatementItemsEditItemScreen: MutableState<Boolean>,
    cashFlowsStatementItemId: MutableState<Long>,
    cashFlowsStatementItemType: MutableState<String>,
    cashFlowsStatementItemDescription: MutableState<String>,
    cashFlowsStatementItemDate: MutableState<String>,
    cashFlowsStatementItemValue: MutableState<Double>,
    cashFlowsStatementItemIsCredit: MutableState<Boolean>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.itemsCashFlowsStatement),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isCashFlowsStatementItemsScreen.value=false
                        cashFlowsStatementName.value=""
                        cashFlowsStatementId.value=0
                        cashFlowsStatementStartDate.value=""
                        cashFlowsStatementEndDate.value=""
                        cashFlowsStatementInitialCashBalance.value=0.0
                        cashFlowsStatementInitialCashBalanceIsCredit.value=true
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
        CashFlowsStatementItemsScreenContent(
            cashFlowsStatementViewModel,
            screenModifier,
            cashFlowsStatementName,
            cashFlowsStatementId,
            cashFlowsStatementStartDate,
            cashFlowsStatementEndDate,
            cashFlowsStatementInitialCashBalance,
            cashFlowsStatementInitialCashBalanceIsCredit,
            isCashFlowsStatementItemsAddItemScreen,
            isCashFlowsStatementItemsEditItemScreen,
            cashFlowsStatementItemId,
            cashFlowsStatementItemType,
            cashFlowsStatementItemDescription,
            cashFlowsStatementItemDate,
            cashFlowsStatementItemValue,
            cashFlowsStatementItemIsCredit,
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
fun CashFlowsStatementItemsAddItemScreen(
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    accountingAccountsViewModel: AccountingAccountsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    cashFlowsStatementName: MutableState<String>,
    cashFlowsStatementId: MutableState<Long>,
    cashFlowsStatementStartDate: MutableState<String>,
    cashFlowsStatementEndDate: MutableState<String>,
    isCashFlowsStatementItemsAddItemScreen: MutableState<Boolean>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.itemsAddCashFlowsStatement),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isCashFlowsStatementItemsAddItemScreen.value=false
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
        CashFlowsStatementItemsAddItemScreenContent(
            cashFlowsStatementViewModel,
            accountingAccountsViewModel,
            screenModifier,
            cashFlowsStatementName,
            cashFlowsStatementId,
            cashFlowsStatementStartDate,
            cashFlowsStatementEndDate,
            isCashFlowsStatementItemsAddItemScreen,
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
fun CashFlowsStatementItemsEditItemScreen(
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    accountingAccountsViewModel: AccountingAccountsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    cashFlowsStatementName: MutableState<String>,
    cashFlowsStatementId: MutableState<Long>,
    cashFlowsStatementStartDate: MutableState<String>,
    cashFlowsStatementEndDate: MutableState<String>,
    isCashFlowsStatementItemsEditItemScreen: MutableState<Boolean>,
    cashFlowsStatementItemId: MutableState<Long>,
    cashFlowsStatementItemType: MutableState<String>,
    cashFlowsStatementItemDescription: MutableState<String>,
    cashFlowsStatementItemDate: MutableState<String>,
    cashFlowsStatementItemValue: MutableState<Double>,
    cashFlowsStatementItemIsCredit: MutableState<Boolean>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.itemsEditCashFlowsStatement),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isCashFlowsStatementItemsEditItemScreen.value=false
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
        CashFlowsStatementItemsEditItemScreenContent(
            cashFlowsStatementViewModel,
            accountingAccountsViewModel,
            screenModifier,
            cashFlowsStatementName,
            cashFlowsStatementId,
            cashFlowsStatementStartDate,
            cashFlowsStatementEndDate,
            isCashFlowsStatementItemsEditItemScreen,
            cashFlowsStatementItemId,
            cashFlowsStatementItemType,
            cashFlowsStatementItemDescription,
            cashFlowsStatementItemDate,
            cashFlowsStatementItemValue,
            cashFlowsStatementItemIsCredit,
        )
    }
}



/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun CashFlowsStatementEditScreenContent(
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    modifier: Modifier = Modifier,
    isCashFlowsStatementEditScreen: MutableState<Boolean>,
    cashFlowsStatementName: MutableState<String>,
    cashFlowsStatementId: MutableState<Long>,
    cashFlowsStatementStartDate: MutableState<String>,
    cashFlowsStatementEndDate: MutableState<String>,
) {

    val name = remember { mutableStateOf(cashFlowsStatementName.value) }
    val openStartDateEditDialog= remember { mutableStateOf(false) }
    val startDate = remember { mutableStateOf(cashFlowsStatementStartDate.value) }
    val openEndDateEditDialog= remember { mutableStateOf(false) }
    val endDate = remember { mutableStateOf(cashFlowsStatementEndDate.value) }
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
                            text = stringResource(R.string.cash_flows_statement_startDate),
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
                            text = stringResource(R.string.cash_flows_statement_endDate),
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
                    onClick = {
                        isCashFlowsStatementEditScreen.value=false
                        cashFlowsStatementName.value=""
                        name.value=""
                        cashFlowsStatementId.value=0
                        startDate.value = ""
                        endDate.value = ""
                        openStartDateEditDialog.value=false
                        openEndDateEditDialog.value=false
                    },

                    ) {
                    Text(
                        text = stringResource(R.string.cash_flows_statement_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

                val changedSucess = stringResource(R.string.cash_flows_statement_changed_sucess)
                val missingNameMsg = stringResource(R.string.cash_flows_statement_missing_name)
                val missingStartDateMsg = stringResource(R.string.cash_flows_statement_missing_startdate)
                val missingEndDateMsg = stringResource(R.string.cash_flows_statement_missing_enddate)

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

                                        cashFlowsStatementViewModel.saveCashFlowStatement(
                                            context,
                                            CashFlowStatement(
                                                cashFlowsStatementId.value,
                                                name.value,
                                                fmt.parse(startDate.value)!!,
                                                fmt.parse(endDate.value)!!,
                                                0.0,
                                                true,
                                                0
                                            )
                                        )
                                        Toast.makeText(
                                            context,
                                            changedSucess,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                        isCashFlowsStatementEditScreen.value = false
                                        cashFlowsStatementName.value = ""
                                        name.value = ""
                                        cashFlowsStatementId.value = 0
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
                        text = stringResource(R.string.cash_flows_statement_save),
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
                openDialog = openStartDateEditDialog, title = stringResource(R.string.cash_flows_statement_startDate)
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
                openDialog = openEndDateEditDialog, title = stringResource(R.string.cash_flows_statement_endDate)
            )
        }
    }
}


/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun CashFlowsStatementItemsScreenContent(
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    modifier: Modifier = Modifier,
    cashFlowsStatementName: MutableState<String>,
    cashFlowsStatementId: MutableState<Long>,
    cashFlowsStatementStartDate: MutableState<String>,
    cashFlowsStatementEndDate: MutableState<String>,
    cashFlowsStatementInitialCashBalance: MutableState<Double>,
    cashFlowsStatementInitialCashBalanceIsCredit: MutableState<Boolean>,
    isCashFlowsStatementItemsAddItemScreen: MutableState<Boolean>,
    isCashFlowsStatementItemsEditItemScreen: MutableState<Boolean>,
    cashFlowsStatementItemId: MutableState<Long>,
    cashFlowsStatementItemType: MutableState<String>,
    cashFlowsStatementItemDescription: MutableState<String>,
    cashFlowsStatementItemDate: MutableState<String>,
    cashFlowsStatementItemValue: MutableState<Double>,
    cashFlowsStatementItemIsCredit: MutableState<Boolean>,
) {

    val context = LocalContext.current
    val isItemsPageEditInitialCashBalance = remember { mutableStateOf(false) }
    val isItemsPageDeleteItem = remember { mutableStateOf(false) }

    val cashFlowItemsFlow = cashFlowsStatementViewModel.getCashFlowStatementItems(cashFlowsStatementId.value)
    val cashFlowItems by cashFlowItemsFlow.collectAsStateWithLifecycle(initialValue = emptyList())


    Column(modifier) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                //.verticalScroll(rememberScrollState()),
        ) {

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth())
            {
                Text(
                    text = cashFlowsStatementName.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = cashFlowsStatementStartDate.value + " - " + cashFlowsStatementEndDate.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                )
                Spacer(Modifier.height(10.dp))

                var cashFlowsStatementInitialCashBalanceText = cashFlowsStatementInitialCashBalance.value.toScreen()
                if (!cashFlowsStatementInitialCashBalanceIsCredit.value)
                    cashFlowsStatementInitialCashBalanceText = "("+cashFlowsStatementInitialCashBalance.value.absoluteValue+")"

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier=Modifier.fillMaxWidth().selectable(
                        selected = false,
                        onClick = {
                            isItemsPageEditInitialCashBalance.value=true

                        },
                        role = Role.Button
                    )
                )
                {
                    Text(
                        text = stringResource(R.string.cash_flows_statement_initial_balance_cash_value)+" $cashFlowsStatementInitialCashBalanceText",
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                    TextButton(
                        modifier = Modifier.padding(1.dp),
                        onClick =
                            {
                                isItemsPageEditInitialCashBalance.value=true
                            }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.edit_24px),
                            contentDescription = stringResource(R.string.cash_flows_statement_add),
                            modifier = Modifier
                        )
                    }

                    TextButton(
                        modifier = Modifier.padding(1.dp),
                        onClick =
                            {

                            }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.convert_to_text_24px),
                            contentDescription = stringResource(R.string.cash_flows_statement_add),
                            modifier = Modifier
                        )
                    }
                    TextButton(
                        modifier = Modifier.padding(1.dp),
                        onClick =
                            {
                                isCashFlowsStatementItemsAddItemScreen.value = true
                            }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.forms_add_on_24px),
                            contentDescription = stringResource(R.string.cash_flows_statement_add),
                            modifier = Modifier
                        )
                    }
                }
            }
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )
            Spacer(Modifier.height(10.dp))


            var totalOperating = 0.0
            var totalInvesting = 0.0
            var totalFinancing = 0.0

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp).verticalScroll(rememberScrollState()),

                ) {

                val operatingExpanded = remember { mutableStateOf(false) }
                displayEachExpandableTitleRow(operatingExpanded, CASH_FLOWS_STATEMENT_TYPE_OPERATING,0)
                val operatingItems = cashFlowItems.filter { it.type == ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_OPERATING }
                operatingItems.forEach { operating->

                    if (operating.isCredit)
                        totalOperating+=operating.value
                    else
                        totalOperating-=operating.value
                    if (operatingExpanded.value){
                        CashFlowsStatementItemRow(operating,
                            isItemsPageDeleteItem,
                            isCashFlowsStatementItemsEditItemScreen,
                            cashFlowsStatementItemId,
                            cashFlowsStatementItemType,
                            cashFlowsStatementItemDescription,
                            cashFlowsStatementItemDate,
                            cashFlowsStatementItemValue,
                            cashFlowsStatementItemIsCredit)
                    }
                }
                if (operatingExpanded.value){
                    var totalOperatingText = totalOperating.toScreen()
                    if (totalOperating<0.0)
                        totalOperatingText = "(" + totalOperating.absoluteValue.toScreen() + ")"
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(0.82f)
                    ){
                        Text(
                            text = stringResource(R.string.cash_flows_statement_total),
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = totalOperatingText,
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    )
                    Spacer(Modifier.height(10.dp))
                }

                val investingExpanded = remember { mutableStateOf(false) }
                val investingItems = cashFlowItems.filter { it.type == ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_INVESTING }
                displayEachExpandableTitleRow(investingExpanded, CASH_FLOWS_STATEMENT_TYPE_INVESTING,0)
                investingItems.forEach { investing->
                    if (investing.isCredit)
                        totalInvesting+=investing.value
                    else
                        totalInvesting-=investing.value
                    if (investingExpanded.value){
                        CashFlowsStatementItemRow(investing,
                            isItemsPageDeleteItem,
                            isCashFlowsStatementItemsEditItemScreen,
                            cashFlowsStatementItemId,
                            cashFlowsStatementItemType,
                            cashFlowsStatementItemDescription,
                            cashFlowsStatementItemDate,
                            cashFlowsStatementItemValue,
                            cashFlowsStatementItemIsCredit)
                    }
                }
                if (investingExpanded.value){
                    var totalInvestingText = totalInvesting.toScreen()
                    if (totalInvesting<0.0)
                        totalInvestingText = "(" + totalInvesting.absoluteValue.toScreen() + ")"
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(0.82f)
                    ){
                        Text(
                            text = stringResource(R.string.cash_flows_statement_total),
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = totalInvestingText,
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    )
                    Spacer(Modifier.height(10.dp))
                }

                val financingExpanded = remember { mutableStateOf(false) }
                val financingItems = cashFlowItems.filter { it.type == ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_FINANCING }
                displayEachExpandableTitleRow(financingExpanded, CASH_FLOWS_STATEMENT_TYPE_FINANCING,0)
                financingItems.forEach { financing->
                    if (financing.isCredit)
                        totalFinancing+=financing.value
                    else
                        totalFinancing-=financing.value
                    if (financingExpanded.value){
                        CashFlowsStatementItemRow(financing,
                            isItemsPageDeleteItem,
                            isCashFlowsStatementItemsEditItemScreen,
                            cashFlowsStatementItemId,
                            cashFlowsStatementItemType,
                            cashFlowsStatementItemDescription,
                            cashFlowsStatementItemDate,
                            cashFlowsStatementItemValue,
                            cashFlowsStatementItemIsCredit)
                    }
                }
                if (financingExpanded.value){
                    var totalFinancingText = totalFinancing.toScreen()
                    if (totalFinancing<0.0)
                        totalFinancingText = "(" + totalFinancing.absoluteValue.toScreen() + ")"

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(0.82f)
                    ){
                        Text(
                            text = stringResource(R.string.cash_flows_statement_total),
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = totalFinancingText,
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    )
                    Spacer(Modifier.height(10.dp))
                }

                val cashIncreaseVal = (totalOperating + totalInvesting + totalFinancing)
                var cashIncreaseText = cashIncreaseVal.toScreen()
                if (cashIncreaseVal<0.0)
                    cashIncreaseText = "(" + cashIncreaseVal.absoluteValue.toScreen() + ")"

                var signedCashFlowsStatementInitialCashBalance = cashFlowsStatementInitialCashBalance.value
                if (!cashFlowsStatementInitialCashBalanceIsCredit.value)
                    signedCashFlowsStatementInitialCashBalance = -(signedCashFlowsStatementInitialCashBalance.absoluteValue)

                val finalBalanceValue = (totalOperating + totalInvesting + totalFinancing + signedCashFlowsStatementInitialCashBalance)
                var finalBalance = finalBalanceValue.toScreen()
                if (finalBalanceValue<0.0)
                    finalBalance = "(" + finalBalanceValue.absoluteValue.toScreen() + ")"

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(0.82f)
                ) {
                    Text(
                        text = stringResource(R.string.cash_flows_statement_increase),
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "$ $cashIncreaseText",
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(0.82f)
                ) {
                    Text(
                        text = stringResource(R.string.cash_flows_statement_final_balance),
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "$ $finalBalance",
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }

    when {
        isItemsPageEditInitialCashBalance.value -> {
            CashFlowsStatementItemsEditInitialCashBalanceDialog(
                isItemsPageEditInitialCashBalance,
                cashFlowsStatementName,
                cashFlowsStatementId,
                cashFlowsStatementStartDate,
                cashFlowsStatementEndDate,
                cashFlowsStatementInitialCashBalance,
                cashFlowsStatementInitialCashBalanceIsCredit,
                cashFlowsStatementViewModel,
                context
            )
        }
        isItemsPageDeleteItem.value -> {
            CashFlowsStatementItemDeleteDialog(isItemsPageDeleteItem,
                cashFlowsStatementItemId,
                cashFlowsStatementItemType,
                cashFlowsStatementItemDescription,
                cashFlowsStatementItemValue,
                cashFlowsStatementItemIsCredit,
                cashFlowsStatementViewModel ,
                context)
        }
    }
}


@Composable
private fun CashFlowsStatementItemRow(
    item: CashFlowStatementItem,
    isCashFlowsStatementItemsDeleteItemDialog: MutableState<Boolean>,
    isCashFlowsStatementItemsEditItemScreen: MutableState<Boolean>,
    cashFlowStatementItemId: MutableState<Long>,
    cashFlowStatementItemType: MutableState<String>,
    cashFlowStatementItemDescription: MutableState<String>,
    cashFlowsStatementItemDate: MutableState<String>,
    cashFlowStatementItemValue: MutableState<Double>,
    cashFlowStatementItemValueIsCredit: MutableState<Boolean>,
){

    val fmt = SimpleDateFormat(stringResource(R.string.fmtDatePattern))
    val fmtMM = SimpleDateFormat(stringResource(R.string.fmtMMDatePattern))


    var value = "("+item.value.toScreen()+")"
    if (item.isCredit)
        value = item.value.toScreen()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
        )
    {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth(0.82f)
            .selectable(
                selected = false,
                onClick = {
                    isCashFlowsStatementItemsEditItemScreen.value=true
                    cashFlowStatementItemId.value=item.cashFlowStatementItemId
                    cashFlowStatementItemType.value=item.type
                    cashFlowStatementItemDescription.value=item.description
                    cashFlowsStatementItemDate.value = fmt.format(item.date)
                    cashFlowStatementItemValue.value=item.value
                    cashFlowStatementItemValueIsCredit.value=item.isCredit
                },
                role = Role.Button
            )
    ) {
        Text(
            text = item.description,
            modifier = Modifier
                .fillMaxWidth(0.4f),
            style = TextStyle(fontFamily = FontFamily.SansSerif),
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = fmtMM.format(item.date),
            modifier = Modifier
                .padding(5.dp),
            style = TextStyle(fontFamily = FontFamily.SansSerif),
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = value,
            modifier = Modifier
                .fillMaxWidth(0.75f),
            textAlign = TextAlign.End,
            style = TextStyle(fontFamily = FontFamily.SansSerif),
            color = MaterialTheme.colorScheme.primary,
        )
    }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            TextButton(
                modifier = Modifier.padding(3.dp),
                onClick =
                    {
                        isCashFlowsStatementItemsDeleteItemDialog.value = true
                        cashFlowStatementItemId.value = item.cashFlowStatementItemId
                        cashFlowStatementItemType.value = item.type
                        cashFlowStatementItemDescription.value = item.description
                        cashFlowStatementItemValue.value = item.value
                        cashFlowStatementItemValueIsCredit.value=item.isCredit
                    }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.delete_24px),
                    contentDescription = stringResource(R.string.cash_flows_statement_delete),
                    modifier = Modifier,
                    tint = Color.Red
                )
            }
        }
    }

}

/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun CashFlowsStatementItemsAddItemScreenContent(
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    accountingAccountsViewModel: AccountingAccountsViewModel,
    modifier: Modifier = Modifier,
    cashFlowsStatementName: MutableState<String>,
    cashFlowsStatementId: MutableState<Long>,
    cashFlowsStatementStartDate: MutableState<String>,
    cashFlowsStatementEndDate: MutableState<String>,
    isCashFlowsStatementItemsAddItemScreen: MutableState<Boolean>,
) {

    val context = LocalContext.current
    val accountingAccountlevel1 = remember { mutableStateOf("") }
    val accountingAccountlevel4Description = remember { mutableStateOf("") }
    val openFinancialMovementDateDialog= remember { mutableStateOf(false) }
    val financialMovementDate = remember { mutableStateOf("") }
    val financialMovementValue = remember { mutableStateOf("") }
    val financialMovementSignal= remember { mutableStateOf(true) }

    val resultTypes = getResultTypes("")
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
                    text = cashFlowsStatementName.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = cashFlowsStatementStartDate.value + " - " + cashFlowsStatementEndDate.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Level1TypeResultDropdownAddItem(accountingAccountlevel1,accountingAccountlevel4Description,resultTypes)
            val accountingAccountsFlow=accountingAccountsViewModel.getAccountingAccounts(accountingAccountlevel1.value)
            val accountingAccounts by accountingAccountsFlow.collectAsStateWithLifecycle(initialValue = emptyList())
            val level4Descrition = ArrayList<String>()
            accountingAccounts.forEach { accountingAccount ->
                level4Descrition.add(accountingAccount.level4 + ": " + accountingAccount.description)
            }
            Level4DropdownMenuAddItem(accountingAccountlevel4Description, level4Descrition)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier=Modifier.fillMaxWidth().selectable(
                    selected = false,
                    onClick = {
                        openFinancialMovementDateDialog.value=true
                    },
                    role = Role.Button
                )
                )
            {
                OutlinedTextField(
                    value = financialMovementDate.value,
                    onValueChange = {
                        financialMovementDate.value = it
                    },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = stringResource(R.string.cash_flows_statement_date),
                        )
                    },
                    modifier = Modifier.onFocusChanged { focusState ->
                        if (focusState.isFocused){
                            openFinancialMovementDateDialog.value=true
                            focusManager.clearFocus()
                        }
                    },
                )
                TextButton(
                    modifier = Modifier.padding(3.dp),
                    onClick =
                        {
                            openFinancialMovementDateDialog.value=true
                        }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.calendar_month_24px),
                        contentDescription = stringResource(R.string.cash_flows_statement_move_date),
                        modifier = Modifier
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            )
            {
                OutlinedTextField(
                    value = financialMovementValue.value,
                    onValueChange = {
                        financialMovementValue.value = it
                    },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = stringResource(R.string.cash_flows_statement_value),
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
                        checked = financialMovementSignal.value,
                        onCheckedChange = { financialMovementSignal.value = it },
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = if (financialMovementSignal.value) stringResource(R.string.cash_flows_statement_credit) else stringResource(R.string.cash_flows_statement_debit),
                        maxLines = 1
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        isCashFlowsStatementItemsAddItemScreen.value = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.cash_flows_statement_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

                val financialMoveAddedSuccess = stringResource(R.string.cash_flows_statement_financial_move_added_success)
                val fmtPattern = stringResource(R.string.fmtDatePattern)
                val emptyTypeMsg = stringResource(R.string.cash_flows_statement_Item_empty_type)
                val emptyDescrMsg = stringResource(R.string.cash_flows_statement_Item_empty_result_account)
                val emptyDateMsg = stringResource(R.string.cash_flows_statement_Item_empty_date)
                val emptyValueMsg = stringResource(R.string.cash_flows_statement_Item_empty_value)

                Button(
                    onClick = {
                        val fmt = SimpleDateFormat(fmtPattern)

                        if (accountingAccountlevel1.value.isEmpty()){
                            Toast.makeText(
                                context,
                                emptyTypeMsg,
                                Toast.LENGTH_SHORT,
                            ).show()
                        }else
                            if (accountingAccountlevel4Description.value.isEmpty()){
                                Toast.makeText(
                                    context,
                                    emptyDescrMsg,
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }else
                                if (financialMovementDate.value.isEmpty()){
                                    Toast.makeText(
                                        context,
                                        emptyDateMsg,
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }else
                                    if (financialMovementValue.value.screenToDouble()==0.0){
                                        Toast.makeText(
                                            context,
                                            emptyValueMsg,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }else {
                                        try {
                                            cashFlowsStatementViewModel.saveCashFlowStatementItem(
                                                context,
                                                CashFlowStatementItem(
                                                    0,
                                                    cashFlowsStatementId.value,
                                                    accountingAccountlevel4Description.value,
                                                    "",
                                                    0,
                                                    accountingAccountlevel1.value,
                                                    financialMovementValue.value.screenToDouble(),
                                                    financialMovementSignal.value,
                                                    fmt.parse(financialMovementDate.value)!!,
                                                    0
                                                ),
                                                CashFlowStatement(0,
                                                    "",
                                                    fmt.parse(cashFlowsStatementStartDate.value)!!,
                                                    fmt.parse(cashFlowsStatementEndDate.value)!!,
                                                    0.0,
                                                    false,
                                                    0)
                                            )
                                            isCashFlowsStatementItemsAddItemScreen.value = false
                                            Toast.makeText(
                                                context,
                                                financialMoveAddedSuccess,
                                                Toast.LENGTH_SHORT,
                                            ).show()

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
                        text = stringResource(R.string.cash_flows_statement_save),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
            }
        }
    }

    when{
        openFinancialMovementDateDialog.value -> {
            val fmt = stringResource(R.string.fmtDatePattern)
            DatePickerModal(
                onDateSelected = {
                    if (it != null) {
                        financialMovementDate.value = SimpleDateFormat(fmt).format(Date(it))
                    }
                },
                openDialog = openFinancialMovementDateDialog, title = stringResource(R.string.cash_flows_statement_financial_move_date)
            )
        }
    }
}


/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun CashFlowsStatementItemsEditItemScreenContent(
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    accountingAccountsViewModel: AccountingAccountsViewModel,
    modifier: Modifier = Modifier,
    cashFlowsStatementName: MutableState<String>,
    cashFlowsStatementId: MutableState<Long>,
    cashFlowsStatementStartDate: MutableState<String>,
    cashFlowsStatementEndDate: MutableState<String>,
    isCashFlowsStatementItemsEditItemScreen: MutableState<Boolean>,
    cashFlowsStatementItemId: MutableState<Long>,
    cashFlowsStatementItemType: MutableState<String>,
    cashFlowsStatementItemDescription: MutableState<String>,
    cashFlowsStatementItemDate: MutableState<String>,
    cashFlowsStatementItemValue: MutableState<Double>,
    cashFlowsStatementItemIsCredit: MutableState<Boolean>,
    ) {

    val context = LocalContext.current
    val accountingAccountlevel1 = remember { mutableStateOf(cashFlowsStatementItemType.value) }
    val accountingAccountlevel4Description = remember { mutableStateOf(cashFlowsStatementItemDescription.value) }
    val openFinancialMovementDateDialog= remember { mutableStateOf(false) }
    val financialMovementDate = remember { mutableStateOf(cashFlowsStatementItemDate.value) }
    val financialMovementValue = remember { mutableStateOf(cashFlowsStatementItemValue.value.toScreen()) }
    val financialMovementSignal= remember { mutableStateOf(cashFlowsStatementItemIsCredit.value) }

    val resultTypes = getResultTypes("")
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
                    text = cashFlowsStatementName.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = cashFlowsStatementStartDate.value + " - " + cashFlowsStatementEndDate.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Level1TypeResultDropdownAddItem(accountingAccountlevel1,accountingAccountlevel4Description,resultTypes)
            val accountingAccountsFlow=accountingAccountsViewModel.getAccountingAccounts(accountingAccountlevel1.value)
            val accountingAccounts by accountingAccountsFlow.collectAsStateWithLifecycle(initialValue = emptyList())
            val level4Descrition = ArrayList<String>()
            accountingAccounts.forEach { accountingAccount ->
                level4Descrition.add(accountingAccount.level4 + ": " + accountingAccount.description)
            }
            Level4DropdownMenuAddItem(accountingAccountlevel4Description, level4Descrition)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier=Modifier.fillMaxWidth().selectable(
                    selected = false,
                    onClick = {
                        openFinancialMovementDateDialog.value=true
                    },
                    role = Role.Button
                )
            )
            {
                OutlinedTextField(
                    value = financialMovementDate.value,
                    onValueChange = {
                        financialMovementDate.value = it
                    },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = stringResource(R.string.cash_flows_statement_date),
                        )
                    },
                    modifier = Modifier.onFocusChanged { focusState ->
                        if (focusState.isFocused){
                            openFinancialMovementDateDialog.value=true
                            focusManager.clearFocus()
                        }
                    },
                )
                TextButton(
                    modifier = Modifier.padding(3.dp),
                    onClick =
                        {
                            openFinancialMovementDateDialog.value = true
                        }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.calendar_month_24px),
                        contentDescription = stringResource(R.string.cash_flows_statement_financial_move_date),
                        modifier = Modifier
                    )
                }

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            )
            {

                OutlinedTextField(
                    value = financialMovementValue.value,
                    onValueChange = {
                        financialMovementValue.value = it
                    },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = stringResource(R.string.cash_flows_statement_value),
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
                        checked = financialMovementSignal.value,
                        onCheckedChange = { financialMovementSignal.value = it },
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = if (financialMovementSignal.value) stringResource(R.string.cash_flows_statement_credit) else stringResource(R.string.cash_flows_statement_debit),
                        maxLines = 1
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        isCashFlowsStatementItemsEditItemScreen.value = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.cash_flows_statement_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

                val financialMoveAddedSuccess = stringResource(R.string.cash_flows_statement_financial_move_changed_success)
                val fmtPattern = stringResource(R.string.fmtDatePattern)
                val emptyTypeMsg = stringResource(R.string.cash_flows_statement_Item_empty_type)
                val emptyDescrMsg = stringResource(R.string.cash_flows_statement_Item_empty_result_account)
                val emptyDateMsg = stringResource(R.string.cash_flows_statement_Item_empty_date)
                val emptyValueMsg = stringResource(R.string.cash_flows_statement_Item_empty_value)

                Button(
                    onClick = {
                        val fmt = SimpleDateFormat(fmtPattern)

                        if (accountingAccountlevel1.value.isEmpty()){
                            Toast.makeText(
                                context,
                                emptyTypeMsg,
                                Toast.LENGTH_SHORT,
                            ).show()
                        }else
                            if (accountingAccountlevel4Description.value.isEmpty()){
                                Toast.makeText(
                                    context,
                                    emptyDescrMsg,
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }else
                                if (financialMovementDate.value.isEmpty()){
                                    Toast.makeText(
                                        context,
                                        emptyDateMsg,
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }else
                                    if (financialMovementValue.value.screenToDouble()==0.0){
                                        Toast.makeText(
                                            context,
                                            emptyValueMsg,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }else {
                                        try {
                                            cashFlowsStatementViewModel.saveCashFlowStatementItem(
                                                context,
                                                CashFlowStatementItem(
                                                    cashFlowsStatementItemId.value,
                                                    cashFlowsStatementId.value,
                                                    accountingAccountlevel4Description.value,
                                                    "",
                                                    0,
                                                    accountingAccountlevel1.value,
                                                    financialMovementValue.value.screenToDouble(),
                                                    financialMovementSignal.value,
                                                    fmt.parse(financialMovementDate.value)!!,
                                                    0
                                                ),
                                                CashFlowStatement(0,
                                                    "",
                                                    fmt.parse(cashFlowsStatementStartDate.value)!!,
                                                    fmt.parse(cashFlowsStatementEndDate.value)!!,
                                                    0.0,
                                                    false,
                                                    0)
                                            )
                                            isCashFlowsStatementItemsEditItemScreen.value = false
                                            Toast.makeText(
                                                context,
                                                financialMoveAddedSuccess,
                                                Toast.LENGTH_SHORT,
                                            ).show()

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
                        text = stringResource(R.string.cash_flows_statement_save),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
            }
        }
    }

    when{
        openFinancialMovementDateDialog.value -> {
            val fmt = stringResource(R.string.fmtDatePattern)
            DatePickerModal(
                onDateSelected = {
                    if (it != null) {
                        financialMovementDate.value = SimpleDateFormat(fmt).format(Date(it))
                    }
                },
                openDialog = openFinancialMovementDateDialog, title = stringResource(R.string.cash_flows_statement_financial_move_date)
            )
        }
    }
}


@Composable
fun Level1TypeResultDropdownAddItem(level1DropdownMenu: MutableState<String>, accountingAccountlevel4Description: MutableState<String>, level1List: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Row {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                },
            value = level1DropdownMenu.value,
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
                            onDismissRequest = { expanded = false }
                        ) {
                            level1List.forEach { item ->

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = item,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                    },
                                    onClick = {
                                        level1DropdownMenu.value = item
                                        accountingAccountlevel4Description.value=""
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = stringResource(R.string.cash_flows_statement_cash_flow),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

            }, enabled = false
        )
    }
}


@Composable
fun Level4DropdownMenuAddItem(level4DescriptionDropdownMenu: MutableState<String>, level4Descrition: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Row {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                },
            value = level4DescriptionDropdownMenu.value,
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
                            onDismissRequest = { expanded = false }
                        ) {
                            level4Descrition.forEach { item ->

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = item,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                    },
                                    onClick = {
                                        level4DescriptionDropdownMenu.value = item
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = stringResource(R.string.cash_flows_statement_result_account),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

            }, enabled = false
        )
    }
}


@Composable
fun CashFlowsStatementItemsEditInitialCashBalanceDialog(
    isItemsPageEditInitialCashBalance: MutableState<Boolean>,
    isItemsName: MutableState<String>,
    isItemsId: MutableState<Long>,
    isItemsStartDate: MutableState<String>,
    isItemsEndDate: MutableState<String>,
    isItemsInitialCashBalance: MutableState<Double>,
    isItemsInitialCashBalanceIsCredit: MutableState<Boolean>,
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    context: Context
) {
    if (isItemsPageEditInitialCashBalance.value) {

        val cashBalance = remember { mutableStateOf(isItemsInitialCashBalance.value.absoluteValue.toScreen()) }
        val cashBalanceSignalIsCredit = remember { mutableStateOf(isItemsInitialCashBalanceIsCredit.value) }

        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                isItemsPageEditInitialCashBalance.value = false
            },
            modifier = Modifier
                .width(800.dp),
                //.height(500.dp),

            title = {
                Text(
                    text = stringResource(R.string.cash_flows_statement_change_initial_balance),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                ) {
                        OutlinedTextField(
                            value = cashBalance.value,
                            onValueChange = {
                                cashBalance.value = it
                            },
                            placeholder = { Text("") },
                            label = {
                                Text(
                                    text = stringResource(R.string.cash_flows_statement_initial_balance),
                                )
                            }
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.padding(8.dp)
                        )
                        {
                            Switch(
                                checked = cashBalanceSignalIsCredit.value,
                                onCheckedChange = { cashBalanceSignalIsCredit.value = it },
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = if (cashBalanceSignalIsCredit.value) stringResource(R.string.cash_flows_statement_credit) else stringResource(
                                    R.string.cash_flows_statement_debit
                                ),
                                maxLines = 1
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
                            isItemsPageEditInitialCashBalance.value = false
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.cash_flows_statement_cancel),
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }


                    val chgSuccess = stringResource(R.string.cash_flows_statement_initial_balance_changed_success)
                    val fmtPattern = stringResource(R.string.fmtDatePattern)
                    val missingNameMsg = stringResource(R.string.cash_flows_statement_missing_name)
                    val missingStartDateMsg = stringResource(R.string.cash_flows_statement_missing_startdate)
                    val missingEndDateMsg = stringResource(R.string.cash_flows_statement_missing_enddate)
                    Button(
                        onClick = {

                            if (isItemsName.value.isEmpty()){
                                Toast.makeText(
                                    context,
                                    missingNameMsg,
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }else
                                if (isItemsStartDate.value.isEmpty()){
                                    Toast.makeText(
                                        context,
                                        missingStartDateMsg,
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }else
                                    if (isItemsEndDate.value.isEmpty()){
                                        Toast.makeText(
                                            context,
                                            missingEndDateMsg,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }else {
                                        try {
                                            val fmt = SimpleDateFormat(fmtPattern)
                                            cashFlowsStatementViewModel.saveCashFlowStatement(
                                                context,
                                                CashFlowStatement(
                                                    isItemsId.value,
                                                    isItemsName.value,
                                                    fmt.parse(isItemsStartDate.value)!!,
                                                    fmt.parse(isItemsEndDate.value)!!,
                                                    cashBalance.value.screenToDouble(),
                                                    cashBalanceSignalIsCredit.value,
                                                    0
                                                )
                                            )
                                            isItemsPageEditInitialCashBalance.value = false
                                            isItemsInitialCashBalance.value = cashBalance.value.screenToDouble()
                                            isItemsInitialCashBalanceIsCredit.value = cashBalanceSignalIsCredit.value
                                            Toast.makeText(
                                                context,
                                                chgSuccess,
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                        } catch (e: Exception) {
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
                            text = stringResource(R.string.cash_flows_statement_save),
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



/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun CashFlowsStatementHomeScreenContent(
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    modifier: Modifier = Modifier,
    isCashFlowsStatementEditScreen: MutableState<Boolean>,
    cashFlowsStatementName: MutableState<String>,
    cashFlowsStatementId: MutableState<Long>,
    cashFlowsStatementStartDate: MutableState<String>,
    cashFlowsStatementEndDate: MutableState<String>,
    isCashFlowsStatementItemsScreen: MutableState<Boolean>,
    cashFlowsStatementInitialCashBalance: MutableState<Double>,
    cashFlowsStatementInitialCashBalanceIsCredit: MutableState<Boolean>,
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
            val cashFlowsStatementsFlow = cashFlowsStatementViewModel.getCashFlowStatements()
            val cashFlowsStatements by cashFlowsStatementsFlow.collectAsStateWithLifecycle(initialValue = emptyList())

            cashFlowsStatements.forEach { cashFlowsStatement ->

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = false,
                            onClick = {
                                isCashFlowsStatementEditScreen.value=true
                                cashFlowsStatementName.value=cashFlowsStatement.name
                                cashFlowsStatementId.value=cashFlowsStatement.cashFlowStatementId
                                cashFlowsStatementStartDate.value = fmt.format(cashFlowsStatement.startDate)
                                cashFlowsStatementEndDate.value = fmt.format(cashFlowsStatement.endDate)
                            },
                            role = Role.Button
                        )
                ) {


                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier,)
                                 {
                            Text(
                                text = cashFlowsStatement.name,
                                style = TextStyle(fontFamily = FontFamily.SansSerif),
                                color = MaterialTheme.colorScheme.primary,

                            )
                            Text(
                                text = fmt.format(cashFlowsStatement.startDate) + " - " + fmt.format(cashFlowsStatement.endDate),
                                style = TextStyle(fontFamily = FontFamily.SansSerif),
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }

                    Row( horizontalArrangement = Arrangement.End){
                        TextButton(
                        modifier = Modifier.padding(1.dp),
                        onClick =
                            {
                                isCashFlowsStatementEditScreen.value=true
                                cashFlowsStatementName.value=cashFlowsStatement.name
                                cashFlowsStatementId.value=cashFlowsStatement.cashFlowStatementId
                                cashFlowsStatementStartDate.value = fmt.format(cashFlowsStatement.startDate)
                                cashFlowsStatementEndDate.value = fmt.format(cashFlowsStatement.endDate)
                            }
                        ) {
                            Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.edit_24px),
                            contentDescription = stringResource(R.string.cash_flows_statement_add),
                            modifier = Modifier
                            )
                        }

                        TextButton(
                            modifier = Modifier.padding(3.dp),
                            onClick =
                            {
                                isCashFlowsStatementItemsScreen.value = true
                                cashFlowsStatementId.value = cashFlowsStatement.cashFlowStatementId
                                cashFlowsStatementName.value = cashFlowsStatement.name
                                cashFlowsStatementStartDate.value = fmt.format(cashFlowsStatement.startDate)
                                cashFlowsStatementEndDate.value = fmt.format(cashFlowsStatement.endDate)
                                cashFlowsStatementInitialCashBalance.value = cashFlowsStatement.initialCashBalance
                                cashFlowsStatementInitialCashBalanceIsCredit.value = cashFlowsStatement.initialCashBalanceIsCredit
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
                                cashFlowsStatementId.value = cashFlowsStatement.cashFlowStatementId
                                cashFlowsStatementName.value = cashFlowsStatement.name
                            }
                        ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.delete_24px),
                            contentDescription = stringResource(R.string.cash_flows_statement_delete),
                            modifier = Modifier,
                            tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }

    when {
        isDeleting.value -> {
            CashFlowsStatementDeleteDialog(isDeleting, cashFlowsStatementId, cashFlowsStatementName, cashFlowsStatementViewModel,context)
        }
    }
}



/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun CashFlowsStatementAddScreenContent(
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    modifier: Modifier = Modifier,
    isCashFlowsStatementAddScreen: MutableState<Boolean>
) {

    val cashFlowsStatementName = remember { mutableStateOf("") }
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
                value = cashFlowsStatementName.value,
                onValueChange = {
                    cashFlowsStatementName.value = it
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
                            isCashFlowsStatementAddScreen.value = false
                            startDate = ""
                            endDate = ""
                        },
                    )
                {
                        Text(
                            text = stringResource(R.string.cash_flows_statement_cancel),
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
                            if (cashFlowsStatementName.value.isEmpty()){
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
                                            cashFlowsStatementViewModel.saveCashFlowStatement(
                                                context,
                                                CashFlowStatement(
                                                    0,
                                                    cashFlowsStatementName.value,
                                                    fmt.parse(startDate)!!,
                                                    fmt.parse(endDate)!!,
                                                    0.0,
                                                    true,
                                                    0
                                                )
                                            )
                                            Toast.makeText(
                                                context,
                                                sucessMsg,
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                            isCashFlowsStatementAddScreen.value = false
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
                        text = stringResource(R.string.cash_flows_statement_save),
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
fun CashFlowsStatementDeleteDialog(
    isDeleting: MutableState<Boolean>,
    isDeletingId: MutableState<Long>,
    isDeletingName: MutableState<String>,
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    context: Context
) {
    if (isDeleting.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                isDeleting.value = false
            },
            modifier = Modifier
                .width(550.dp),
                //.height(300.dp)

            title = {
                Text(
                    text = stringResource(R.string.cash_flows_statement_delete_cash_flows_statement),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            },
            text = {

                        Text(
                            text = isDeletingName.value,
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )

            },
            confirmButton = {


            }, dismissButton = {

                Row(
                    verticalAlignment = Alignment.Bottom,
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
                            text = stringResource(R.string.cash_flows_statement_cancel),
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    val deleteSucessMsg = stringResource(R.string.cash_flows_statement_deleted_sucess)
                    Button(
                        onClick = {
                            cashFlowsStatementViewModel.deleteCashFlowStatement(CashFlowStatement(isDeletingId.value,isDeletingName.value, Date(0), Date(0), 0.0,true,0))
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
fun CashFlowsStatementItemDeleteDialog(
    isDeletingCashFlowStatementItem: MutableState<Boolean>,
    isDeletingCashFlowStatementItemId: MutableState<Long>,
    isDeletingCashFlowStatementItemType: MutableState<String>,
    isDeletingCashFlowStatementItemDescription: MutableState<String>,
    isDeletingCashFlowStatementItemValue: MutableState<Double>,
    isDeletingCashFlowStatementItemIsCredit: MutableState<Boolean>,
    cashFlowsStatementViewModel: CashFlowsStatementViewModel,
    context: Context
) {
    if (isDeletingCashFlowStatementItem.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                isDeletingCashFlowStatementItem.value = false
            },
            modifier = Modifier
                .width(550.dp),
                //.height(500.dp),

            title = {
                Text(
                    text = stringResource(R.string.cash_flows_statement_delete_statement),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(2.dp)

                ) {
                    Text(
                        text = isDeletingCashFlowStatementItemDescription.value,
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    if (isDeletingCashFlowStatementItemIsCredit.value)
                    Text(
                        text = isDeletingCashFlowStatementItemValue.value.absoluteValue.toScreen(),
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    else
                        Text(
                            text = "("+isDeletingCashFlowStatementItemValue.value.absoluteValue.toScreen()+")",
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
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
                            isDeletingCashFlowStatementItem.value = false
                            isDeletingCashFlowStatementItemId.value=0
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.cash_flows_statement_cancel),
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    val financialMoveDeletedSucessMsg = stringResource(R.string.cash_flows_statement_financial_move_deleted_sucess)
                    Button(
                        onClick = {
                            cashFlowsStatementViewModel.deleteCashFlowStatementItem(CashFlowStatementItem(isDeletingCashFlowStatementItemId.value,0, "", "", 0,"",0.0,false,Date(0),0))
                            isDeletingCashFlowStatementItem.value = false
                            isDeletingCashFlowStatementItemId.value=0
                            isDeletingCashFlowStatementItemType.value=""
                            isDeletingCashFlowStatementItemDescription.value=""
                            isDeletingCashFlowStatementItemValue.value=0.0
                            isDeletingCashFlowStatementItemIsCredit.value=true
                            Toast.makeText(
                                context,
                                financialMoveDeletedSucessMsg,
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