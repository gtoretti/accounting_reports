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

package com.example.jetnews.ui.balanceSheet

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetnews.R
import com.example.jetnews.data.balanceSheet.BalanceSheet
import com.example.jetnews.data.balanceSheet.BalanceSheetItem
import com.example.jetnews.ui.accountingAccounts.AccountingAccountsViewModel
import com.example.jetnews.ui.accountingAccounts.displayEachExpandableTitleRow
import com.example.jetnews.ui.accountingAccounts.getLevel1Types
import com.example.jetnews.ui.accountingAccounts.getLevel2Types
import com.example.jetnews.ui.accountingAccounts.getLevel3Types
import com.example.jetnews.ui.utils.DatePickerModal
import com.example.jetnews.ui.utils.getLightBlueColor
import com.example.jetnews.ui.utils.getLightGreenColor
import com.example.jetnews.ui.utils.getLightRedColor
import com.example.jetnews.ui.utils.getPhoneColor
import com.example.jetnews.ui.utils.getRedTextColor
import com.example.jetnews.ui.utils.screenToDouble
import com.example.jetnews.ui.utils.toScreen
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL1_ATIVO
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL1_PASSIVO
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_CIRCULANTE
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_NAO_CIRCULANTE
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_CIRCULANTE
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_NAO_CIRCULANTE
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_PATRIMONIO_LIQUIDO
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_CAIXA_E_EQUIVALENTES_DE_CAIXA
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_CONTAS_A_RECEBER
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_DESPESAS_ANTECIPADAS
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_ESTOQUES
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_ATIVOS_INTANGIVEIS
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_ATIVOS_REALIZAVEIS_A_LONGO_PRAZO
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_IMOBILIZADO
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_INVESTIMENTOS_DE_LONGO_PRAZO
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_CIRCULANTE_FINANCEIRO
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_CIRCULANTE_OPERACIONAL
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_ARRENDAMENTO_MERCANTIL_FINANCEIRO
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_DEBENTURES
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_EMPRESTIMOS_E_FINANCIAMENTOS_DE_LONGO_PRAZO
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_OBRIGACOES_FISCAIS_E_TRABALHISTAS_DE_LONGO_PRAZO
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_PROVISOES_PARA_CONTINGENCIAS
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_ACOES_EM_TESOURARIA
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_AJUSTES_DE_AVALIACAO_PATRIMONIAL
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_CAPITAL_SOCIAL
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_PREJUIZOS_ACUMULADOS
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_RESERVAS_DE_CAPITAL
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_RESERVAS_DE_LUCROS
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.Boolean
import kotlin.Long
import kotlin.String

import kotlin.text.isEmpty



/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceSheetScreen(
    balanceSheetViewModel: BalanceSheetViewModel,
    accountingAccountsViewModel: AccountingAccountsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isBalanceSheetAddScreen = remember { mutableStateOf(false) }
    val isBalanceSheetEditScreen = remember { mutableStateOf(false) }
    val isBalanceSheetItemsAddItemScreen = remember { mutableStateOf(false) }
    val isBalanceSheetItemsEditItemScreen = remember { mutableStateOf(false) }
    val isBalanceSheetItemsScreen = remember { mutableStateOf(false) }

    val balanceSheetName = remember { mutableStateOf("") }
    val balanceSheetId = remember { mutableLongStateOf(0) }
    val balanceSheetDate = remember { mutableStateOf("") }

    val balanceSheetItemId = remember { mutableLongStateOf(0) }
    val balanceSheetItemLevel1 = remember { mutableStateOf("") }
    val balanceSheetItemLevel2 = remember { mutableStateOf("") }
    val balanceSheetItemLevel3 = remember { mutableStateOf("") }
    val balanceSheetItemLevel4Description = remember { mutableStateOf("") }
    val balanceSheetItemValue = remember { mutableDoubleStateOf(0.0) }

    when {
        isBalanceSheetAddScreen.value -> {
            BalanceSheetAddScreen(
                balanceSheetViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isBalanceSheetAddScreen
            )
        }
        isBalanceSheetEditScreen.value -> {
            BalanceSheetEditScreen(
                balanceSheetViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isBalanceSheetEditScreen,
                balanceSheetName,
                balanceSheetId,
                balanceSheetDate,
            )
        }
        isBalanceSheetItemsEditItemScreen.value -> {
            BalanceSheetItemsEditItemScreen(
                balanceSheetViewModel,
                accountingAccountsViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                balanceSheetName,
                balanceSheetId,
                balanceSheetDate,
                isBalanceSheetItemsEditItemScreen,
                balanceSheetItemId,
                balanceSheetItemLevel1,
                balanceSheetItemLevel2,
                balanceSheetItemLevel3,
                balanceSheetItemLevel4Description,
                balanceSheetItemValue
            )
        }
        isBalanceSheetItemsAddItemScreen.value -> {
            BalanceSheetItemsAddItemScreen(
                balanceSheetViewModel,
                accountingAccountsViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                balanceSheetName,
                balanceSheetId,
                balanceSheetDate,
                isBalanceSheetItemsAddItemScreen
            )
        }
        isBalanceSheetItemsScreen.value -> {
            BalanceSheetItemsScreen(
                balanceSheetViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isBalanceSheetItemsScreen,
                balanceSheetName,
                balanceSheetId,
                balanceSheetDate,
                isBalanceSheetItemsAddItemScreen,
                isBalanceSheetItemsEditItemScreen,
                balanceSheetItemId,
                balanceSheetItemLevel1,
                balanceSheetItemLevel2,
                balanceSheetItemLevel3,
                balanceSheetItemLevel4Description,
                balanceSheetItemValue,
            )
        }
        else -> {
            BalanceSheetHomeScreen(
                balanceSheetViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isBalanceSheetAddScreen,
                isBalanceSheetEditScreen,
                balanceSheetName,
                balanceSheetId,
                balanceSheetDate,
                isBalanceSheetItemsScreen,
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
fun BalanceSheetHomeScreen(
    balanceSheetViewModel: BalanceSheetViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isBalanceSheetAddScreen: MutableState<Boolean>,
    isBalanceSheetEditScreen: MutableState<Boolean>,
    balanceSheetName: MutableState<String>,
    balanceSheetId: MutableState<Long>,
    balanceSheetDate: MutableState<String>,
    isBalanceSheetItemsScreen: MutableState<Boolean>,
) {


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.balance_sheet_title),
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
                            isBalanceSheetAddScreen.value=true
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = stringResource(R.string.addBalanceSheet),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        BalanceSheetHomeScreenContent(
            balanceSheetViewModel,
            screenModifier,
            isBalanceSheetEditScreen,
            balanceSheetName,
            balanceSheetId,
            balanceSheetDate,
            isBalanceSheetItemsScreen,
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
fun BalanceSheetAddScreen(
    balanceSheetViewModel: BalanceSheetViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isBalanceSheetAddScreen: MutableState<Boolean>
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.addBalanceSheet),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isBalanceSheetAddScreen.value=false
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
        BalanceSheetAddScreenContent(
            balanceSheetViewModel,
            screenModifier,
            isBalanceSheetAddScreen
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
fun BalanceSheetEditScreen(
    balanceSheetViewModel: BalanceSheetViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isBalanceSheetEditScreen: MutableState<Boolean>,
    balanceSheetName: MutableState<String>,
    balanceSheetId: MutableState<Long>,
    balanceSheetDate: MutableState<String>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.editBalanceSheet),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isBalanceSheetEditScreen.value=false
                        balanceSheetName.value=""
                        balanceSheetId.value=0
                        balanceSheetDate.value=""
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
        BalanceSheetEditScreenContent(
            balanceSheetViewModel,
            screenModifier,
            isBalanceSheetEditScreen,
            balanceSheetName,
            balanceSheetId,
            balanceSheetDate,
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
fun BalanceSheetItemsScreen(
    balanceSheetViewModel: BalanceSheetViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isBalanceSheetItemsScreen: MutableState<Boolean>,
    balanceSheetName: MutableState<String>,
    balanceSheetId: MutableState<Long>,
    balanceSheetDate: MutableState<String>,
    isBalanceSheetItemsAddItemScreen: MutableState<Boolean>,
    isBalanceSheetItemsEditItemScreen: MutableState<Boolean>,
    balanceSheetItemId: MutableState<Long>,
    balanceSheetItemLevel1: MutableState<String>,
    balanceSheetItemLevel2: MutableState<String>,
    balanceSheetItemLevel3: MutableState<String>,
    balanceSheetItemLevel4Description: MutableState<String>,
    balanceSheetItemValue: MutableState<Double>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.balance_sheet_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isBalanceSheetItemsScreen.value=false
                        balanceSheetName.value=""
                        balanceSheetId.value=0
                        balanceSheetDate.value=""
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
        BalanceSheetItemsScreenContent(
            balanceSheetViewModel,
            screenModifier,
            balanceSheetName,
            balanceSheetId,
            balanceSheetDate,
            isBalanceSheetItemsAddItemScreen,
            isBalanceSheetItemsEditItemScreen,
            balanceSheetItemId,
            balanceSheetItemLevel1,
            balanceSheetItemLevel2,
            balanceSheetItemLevel3,
            balanceSheetItemLevel4Description,
            balanceSheetItemValue,
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
fun BalanceSheetItemsAddItemScreen(
    balanceSheetViewModel: BalanceSheetViewModel,
    accountingAccountsViewModel: AccountingAccountsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    balanceSheetName: MutableState<String>,
    balanceSheetId: MutableState<Long>,
    balanceSheetDate: MutableState<String>,
    isBalanceSheetItemsAddItemScreen: MutableState<Boolean>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.balance_sheet_add_accounting_account),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isBalanceSheetItemsAddItemScreen.value=false
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
        BalanceSheetItemsAddItemScreenContent(
            balanceSheetViewModel,
            accountingAccountsViewModel,
            screenModifier,
            balanceSheetName,
            balanceSheetId,
            balanceSheetDate,
            isBalanceSheetItemsAddItemScreen,
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
fun BalanceSheetItemsEditItemScreen(
    balanceSheetViewModel: BalanceSheetViewModel,
    accountingAccountsViewModel: AccountingAccountsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    balanceSheetName: MutableState<String>,
    balanceSheetId: MutableState<Long>,
    balanceSheetDate: MutableState<String>,
    isBalanceSheetItemsEditItemScreen: MutableState<Boolean>,
    balanceSheetItemId: MutableState<Long>,
    balanceSheetItemLevel1: MutableState<String>,
    balanceSheetItemLevel2: MutableState<String>,
    balanceSheetItemLevel3: MutableState<String>,
    balanceSheetItemLevel4Description: MutableState<String>,
    balanceSheetItemValue: MutableState<Double>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.balance_sheet_edit_Item_accounting_account),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isBalanceSheetItemsEditItemScreen.value=false
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
        BalanceSheetItemsEditItemScreenContent(
            balanceSheetViewModel,
            accountingAccountsViewModel,
            screenModifier,
            balanceSheetName,
            balanceSheetId,
            balanceSheetDate,
            isBalanceSheetItemsEditItemScreen,
            balanceSheetItemId,
            balanceSheetItemLevel1,
            balanceSheetItemLevel2,
            balanceSheetItemLevel3,
            balanceSheetItemLevel4Description,
            balanceSheetItemValue,
        )
    }
}



/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun BalanceSheetEditScreenContent(
    balanceSheetViewModel: BalanceSheetViewModel,
    modifier: Modifier = Modifier,
    isBalanceSheetEditScreen: MutableState<Boolean>,
    balanceSheetName: MutableState<String>,
    balanceSheetId: MutableState<Long>,
    balanceSheetDate: MutableState<String>,
) {

    val name = remember { mutableStateOf(balanceSheetName.value) }
    val openDateEditDialog= remember { mutableStateOf(false) }
    val date = remember { mutableStateOf(balanceSheetDate.value) }
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
                        text = stringResource(R.string.balance_sheet_name),
                    )
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier=Modifier.fillMaxWidth().selectable(
                    selected = false,
                    onClick = {
                        openDateEditDialog.value=true
                    },
                    role = Role.Button
                )
                ) {
                OutlinedTextField(
                    value = date.value,
                    onValueChange = {
                        date.value = it
                    },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = stringResource(R.string.balance_sheet_date),
                        )
                    },
                    modifier = Modifier.onFocusChanged { focusState ->
                        if (focusState.isFocused){
                            openDateEditDialog.value=true
                            focusManager.clearFocus()
                        }
                    },
                )
                TextButton(
                    modifier = Modifier.padding(3.dp),
                    onClick =
                        {
                            openDateEditDialog.value=true
                        }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.calendar_month_24px),
                        contentDescription = stringResource(R.string.balance_sheet_date),
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
                        isBalanceSheetEditScreen.value=false
                        balanceSheetName.value=""
                        name.value=""
                        balanceSheetId.value=0
                        date.value = ""
                        openDateEditDialog.value=false
                    },

                    ) {
                    Text(
                        text = stringResource(R.string.balance_sheet_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

                val changedSucess = stringResource(R.string.balance_sheet_changed_sucess)
                val missingNameMsg = stringResource(R.string.balance_sheet_missing_name)
                val missingDateMsg = stringResource(R.string.balance_sheet_missing_date)

                Button(
                    onClick = {
                        if (name.value.isEmpty()){
                            Toast.makeText(
                                context,
                                missingNameMsg,
                                Toast.LENGTH_SHORT,
                            ).show()
                        }else
                            if (date.value.isEmpty()){
                                Toast.makeText(
                                    context,
                                    missingDateMsg,
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }else{
                                    try {
                                        balanceSheetViewModel.saveBalanceSheet(BalanceSheet(balanceSheetId.value, name.value, fmt.parse(date.value)!!, 0))
                                        Toast.makeText(
                                            context,
                                            changedSucess,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                        isBalanceSheetEditScreen.value = false
                                        balanceSheetName.value = ""
                                        name.value = ""
                                        balanceSheetId.value = 0
                                        date.value = ""
                                        openDateEditDialog.value = false
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
                        text = stringResource(R.string.balance_sheet_save),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
            }
        }
    }

    when {
        openDateEditDialog.value -> {
            val fmt = stringResource(R.string.fmtDatePattern)
            DatePickerModal(
                onDateSelected = {
                    if (it != null) {
                        date.value = SimpleDateFormat(fmt).format(Date(it))
                    }
                },
                openDialog = openDateEditDialog, title = stringResource(R.string.balance_sheet_date)
            )
        }
    }
}



/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun Scale(totalCurrentAssets: Double,
                  totalNonCurrentAssets: Double,
                  totalCurrentLiabilities: Double,
                  totalNonCurrentLiabilities: Double,
                  totalNetWorth: Double,
) {
    if (totalNetWorth + totalCurrentLiabilities + totalNonCurrentLiabilities == totalCurrentAssets + totalNonCurrentAssets) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(1.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.scales_centergreen_transp),
                contentDescription = "Balanço Patrimonial",
                modifier = Modifier.size(width = 150.dp, height = 50.dp)
            )
        }
    }
    if (totalNetWorth + totalCurrentLiabilities + totalNonCurrentLiabilities < totalCurrentAssets + totalNonCurrentAssets) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(1.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.scales_left_red_transp),
                contentDescription = "Balanço Patrimonial",
                modifier = Modifier
                    .size(width = 150.dp, height = 50.dp)
                    .padding(1.dp),
            )
            Text(
                text = "Diferença:",
                style = TextStyle(
                    color = getRedTextColor(),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                ),
                modifier = Modifier
                    .padding(1.dp)
            )
            Text(
                text = ((totalCurrentAssets + totalNonCurrentAssets) - (totalNetWorth + totalCurrentLiabilities + totalNonCurrentLiabilities)).toScreen(),
                style = TextStyle(
                    color = getRedTextColor(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                ),
                modifier = Modifier
                    .padding(1.dp)

            )
        }
    }
    if (totalNetWorth + totalCurrentLiabilities + totalNonCurrentLiabilities > totalCurrentAssets + totalNonCurrentAssets) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(1.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.scales_right_red_transp),
                contentDescription = "Balanço Patrimonial",
                modifier = Modifier.size(width = 150.dp, height = 50.dp),
            )
            Text(
                text = "Diferença:",
                style = TextStyle(
                    color = getRedTextColor(),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                ),
                modifier = Modifier
                    .padding(1.dp)

            )
            Text(
                text = ((totalNetWorth + totalCurrentLiabilities + totalNonCurrentLiabilities) - (totalCurrentAssets + totalNonCurrentAssets)).toScreen(),
                style = TextStyle(
                    color = getRedTextColor(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                ),
                modifier = Modifier
                    .padding(1.dp)

            )
        }
    }
}

/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun AssetsAndLiabilitiesPanel(totalCurrentAssets: Double,
                                      totalNonCurrentAssets: Double,
                                      totalCurrentLiabilities: Double,
                                      totalNonCurrentLiabilities: Double,
                                      totalNetWorth: Double,
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 1.dp)
    ) {

        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = getLightGreenColor(),

                ),
            border = BorderStroke(2.dp, Color.Black),
            modifier = Modifier
                .size(width = screenWidth * 0.47f, height = 300.dp)
                .padding(2.dp),


            shape = RoundedCornerShape(8.dp),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )

        ) {
            Text(
                text = stringResource(R.string.balance_sheet_assets),
                style = TextStyle(
                    color = getPhoneColor(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                ),
                modifier = Modifier
                    .padding(5.dp),
            )

            Text(
                text = (totalNonCurrentAssets + totalCurrentAssets).toScreen(),
                style = TextStyle(

                    fontSize = 15.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .padding(5.dp),
            )

            Text(
                text = stringResource(R.string.balance_sheet_current),
                style = TextStyle(

                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                ),
                modifier = Modifier
                    .padding(5.dp),
            )

            Text(
                text = totalCurrentAssets.toScreen(),
                style = TextStyle(

                    fontSize = 15.sp,
                    fontFamily = FontFamily.SansSerif,
                ),
                modifier = Modifier
                    .padding(5.dp),
            )

            Text(
                text = stringResource(R.string.balance_sheet_non_current),
                style = TextStyle(

                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                ),
                modifier = Modifier
                    .padding(5.dp),
            )
            Text(
                text = totalNonCurrentAssets.toScreen(),
                style = TextStyle(

                    fontSize = 15.sp,
                    fontFamily = FontFamily.SansSerif,
                ),
                modifier = Modifier
                    .padding(5.dp),
            )
        }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)

        ) {

            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = getLightRedColor(),
                ),
                border = BorderStroke(2.dp, Color.Black),
                modifier = Modifier
                    .size(width = screenWidth * 0.47f, height = 300.dp)
                    .padding(2.dp),

                shape = RoundedCornerShape(8.dp),

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )

            ) {

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.balance_sheet_liabilities),
                        style = TextStyle(
                            color = getRedTextColor(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                        ),
                        modifier = Modifier
                            .padding(5.dp),
                    )


                    Text(
                        text = (totalCurrentLiabilities + totalNonCurrentLiabilities).toScreen(),
                        style = TextStyle(

                            fontSize = 15.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .padding(5.dp),
                    )
                }


                Text(
                    text = stringResource(R.string.balance_sheet_current),
                    style = TextStyle(

                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    ),
                    modifier = Modifier
                        .padding(5.dp),
                )
                Text(
                    text = totalCurrentLiabilities.toScreen(),
                    style = TextStyle(

                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                    ),
                    modifier = Modifier
                        .padding(5.dp),
                )

                Text(
                    text = stringResource(R.string.balance_sheet_non_current),
                    style = TextStyle(

                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    ),
                    modifier = Modifier
                        .padding(5.dp),
                )

                Text(
                    text = totalNonCurrentLiabilities.toScreen(),
                    style = TextStyle(

                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                    ),
                    modifier = Modifier
                        .padding(5.dp),
                )


                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxWidth().fillMaxHeight()
                ) {
                    OutlinedCard(
                        colors = CardDefaults.cardColors(
                            containerColor = getLightBlueColor(),
                        ),
                        border = BorderStroke(2.dp, Color.Black),
                        modifier = Modifier
                            .size(width = screenWidth * 0.47f, height = 100.dp)
                            .padding(all = 1.dp),

                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.balance_sheet_net_worth),
                            style = TextStyle(

                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif,
                            ),
                            modifier = Modifier
                                .padding(5.dp),
                        )

                        Text(
                            text = totalNetWorth.toScreen(),
                            style = TextStyle(

                                fontSize = 15.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier
                                .padding(5.dp),
                        )
                    }
                }
            }
        }
    }
}

/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun BalanceSheetItemsScreenContent(
    balanceSheetViewModel: BalanceSheetViewModel,
    modifier: Modifier = Modifier,
    balanceSheetName: MutableState<String>,
    balanceSheetId: MutableState<Long>,
    balanceSheetDate: MutableState<String>,
    isBalanceSheetItemsAddItemScreen: MutableState<Boolean>,
    isBalanceSheetItemsEditItemScreen: MutableState<Boolean>,
    balanceSheetItemId: MutableState<Long>,
    balanceSheetItemLevel1: MutableState<String>,
    balanceSheetItemLevel2: MutableState<String>,
    balanceSheetItemLevel3: MutableState<String>,
    balanceSheetItemLevel4Description: MutableState<String>,
    balanceSheetItemValue: MutableState<Double>,
) {

    val context = LocalContext.current
    val isBalanceSheetItemsDeleteItemDialog = remember { mutableStateOf(false) }

    Column(modifier) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)

        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth())
            {
                Text(
                    text = balanceSheetName.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = balanceSheetDate.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
            }
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )
            Spacer(Modifier.height(10.dp))

            //scale
            var totalAssets = 0.0
            var totalCurrentAssets = 0.0
            var totalNonCurrentAssets = 0.0
            var totalLiabilities = 0.0
            var totalCurrentLiabilities = 0.0
            var totalNonCurrentLiabilities = 0.0
            var totalNetWorth = 0.0

            val balanceSheetItemsFlow = balanceSheetViewModel.getBalanceSheetItems(balanceSheetId.value)
            val balanceSheetItems by balanceSheetItemsFlow.collectAsStateWithLifecycle(initialValue = emptyList())

            val assets = balanceSheetItems.filter { it.level1 == ACCOUNTING_ACCOUNTS_LEVEL1_ATIVO }
            val assetsCurrent = assets.filter { it.level2 == ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_CIRCULANTE }
            val assetsNonCurrent = assets.filter { it.level2 == ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_NAO_CIRCULANTE }

            val liabilities = balanceSheetItems.filter { it.level1 == ACCOUNTING_ACCOUNTS_LEVEL1_PASSIVO && it.level2 != ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_PATRIMONIO_LIQUIDO}
            val liabilitiesCurrent = liabilities.filter { it.level2 == ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_CIRCULANTE }
            val liabilitiesNonCurrent = liabilities.filter { it.level2 == ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_NAO_CIRCULANTE }

            val netWorth = balanceSheetItems.filter { it.level2 == ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_PATRIMONIO_LIQUIDO  }

            assets.forEach {
                totalAssets += it.value
            }
            assetsCurrent.forEach {
                totalCurrentAssets += it.value
            }
            assetsNonCurrent.forEach {
                totalNonCurrentAssets += it.value
            }
            liabilities.forEach {
                totalLiabilities += it.value
            }
            liabilitiesCurrent.forEach {
                totalCurrentLiabilities += it.value
            }
            liabilitiesNonCurrent.forEach {
                totalNonCurrentLiabilities += it.value
            }
            netWorth.forEach {
                totalNetWorth += it.value
            }


            AssetsAndLiabilitiesPanel(totalCurrentAssets,
                totalNonCurrentAssets,
                totalCurrentLiabilities,
                totalNonCurrentLiabilities,
                totalNetWorth)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Scale(totalCurrentAssets,
                    totalNonCurrentAssets,
                    totalCurrentLiabilities,
                    totalNonCurrentLiabilities,
                    totalNetWorth)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            )
            {
                TextButton(
                    modifier = Modifier.padding(1.dp),
                    onClick =
                        {

                        }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.convert_to_text_24px),
                        contentDescription = stringResource(R.string.cash_flows_statement_add),
                        modifier = Modifier,
                        tint = getLightRedColor()
                    )
                }
                TextButton(
                    modifier = Modifier.padding(1.dp),
                    onClick =
                        {
                            isBalanceSheetItemsAddItemScreen.value = true
                        }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.forms_add_on_24px),
                        contentDescription = stringResource(R.string.cash_flows_statement_add),
                        modifier = Modifier,
                        tint = getLightGreenColor()
                    )
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )
            Spacer(Modifier.height(10.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp).verticalScroll(rememberScrollState()),

                ) {
                val assetsExpanded = remember { mutableStateOf(false) }
                displayEachExpandableTitleRow(assetsExpanded, ACCOUNTING_ACCOUNTS_LEVEL1_ATIVO,0)
                if (assetsExpanded.value){

                    val assetsCurrentExpanded = remember { mutableStateOf(false) }
                    displayEachExpandableTitleRow(assetsCurrentExpanded, ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_CIRCULANTE,2)

                    if (assetsCurrentExpanded.value){

                        val assetsCurrentExpanded1 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(assetsCurrentExpanded1, ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_CAIXA_E_EQUIVALENTES_DE_CAIXA,3)
                        if (assetsCurrentExpanded1.value){
                            assetsCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_CAIXA_E_EQUIVALENTES_DE_CAIXA }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }


                        val assetsCurrentExpanded2 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(assetsCurrentExpanded2, ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_CONTAS_A_RECEBER,3)
                        if (assetsCurrentExpanded2.value){
                            assetsCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_CONTAS_A_RECEBER }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }

                        val assetsCurrentExpanded3 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(assetsCurrentExpanded3, ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_ESTOQUES,3)
                        if (assetsCurrentExpanded3.value){
                            assetsCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_ESTOQUES }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }


                        val assetsCurrentExpanded4 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(assetsCurrentExpanded4, ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_DESPESAS_ANTECIPADAS,3)
                        if (assetsCurrentExpanded4.value){
                            assetsCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_DESPESAS_ANTECIPADAS }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }
                    }

                    val assetsNonCurrentExpanded = remember { mutableStateOf(false) }
                    displayEachExpandableTitleRow(assetsNonCurrentExpanded, ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_NAO_CIRCULANTE,2)
                    if (assetsNonCurrentExpanded.value) {

                        val assetsNonCurrentExpanded1 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(
                            assetsNonCurrentExpanded1,
                            ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_ATIVOS_REALIZAVEIS_A_LONGO_PRAZO,
                            3
                        )
                        if (assetsNonCurrentExpanded1.value) {
                            assetsNonCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_ATIVOS_REALIZAVEIS_A_LONGO_PRAZO }
                                .forEach { asset ->
                                    BalanceSheetItemRow(
                                        asset,
                                        isBalanceSheetItemsDeleteItemDialog,
                                        isBalanceSheetItemsEditItemScreen,
                                        balanceSheetItemId,
                                        balanceSheetItemLevel1,
                                        balanceSheetItemLevel2,
                                        balanceSheetItemLevel3,
                                        balanceSheetItemLevel4Description,
                                        balanceSheetItemValue
                                    )
                                }
                        }


                        val assetsNonCurrentExpanded2 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(
                            assetsNonCurrentExpanded2,
                            ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_INVESTIMENTOS_DE_LONGO_PRAZO,
                            3
                        )
                        if (assetsNonCurrentExpanded2.value) {
                            if (assetsNonCurrentExpanded2.value) {
                                assetsNonCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_INVESTIMENTOS_DE_LONGO_PRAZO }
                                    .forEach { asset ->
                                        BalanceSheetItemRow(
                                            asset,
                                            isBalanceSheetItemsDeleteItemDialog,
                                            isBalanceSheetItemsEditItemScreen,
                                            balanceSheetItemId,
                                            balanceSheetItemLevel1,
                                            balanceSheetItemLevel2,
                                            balanceSheetItemLevel3,
                                            balanceSheetItemLevel4Description,
                                            balanceSheetItemValue
                                        )
                                    }
                            }
                        }


                        val assetsNonCurrentExpanded3 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(assetsNonCurrentExpanded3, ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_IMOBILIZADO, 3)
                        if (assetsNonCurrentExpanded3.value) {
                            assetsNonCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_IMOBILIZADO }
                                .forEach { asset ->
                                    BalanceSheetItemRow(
                                        asset,
                                        isBalanceSheetItemsDeleteItemDialog,
                                        isBalanceSheetItemsEditItemScreen,
                                        balanceSheetItemId,
                                        balanceSheetItemLevel1,
                                        balanceSheetItemLevel2,
                                        balanceSheetItemLevel3,
                                        balanceSheetItemLevel4Description,
                                        balanceSheetItemValue
                                    )
                                }
                        }

                        val assetsNonCurrentExpanded4 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(
                            assetsNonCurrentExpanded4,
                            ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_ATIVOS_INTANGIVEIS,
                            3
                        )
                        if (assetsNonCurrentExpanded4.value) {
                            assetsNonCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_ATIVOS_INTANGIVEIS }
                                .forEach { asset ->
                                    BalanceSheetItemRow(
                                        asset,
                                        isBalanceSheetItemsDeleteItemDialog,
                                        isBalanceSheetItemsEditItemScreen,
                                        balanceSheetItemId,
                                        balanceSheetItemLevel1,
                                        balanceSheetItemLevel2,
                                        balanceSheetItemLevel3,
                                        balanceSheetItemLevel4Description,
                                        balanceSheetItemValue
                                    )
                                }
                        }

                    }
                }

                val liabilitiesExpanded = remember { mutableStateOf(false) }
                displayEachExpandableTitleRow(liabilitiesExpanded, ACCOUNTING_ACCOUNTS_LEVEL1_PASSIVO,0)
                if (liabilitiesExpanded.value){

                    val liabilitiesCurrentExpanded = remember { mutableStateOf(false) }
                    displayEachExpandableTitleRow(liabilitiesCurrentExpanded, ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_CIRCULANTE,2)
                    if (liabilitiesCurrentExpanded.value){


                        val liabilitiesCurrentExpanded1 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(liabilitiesCurrentExpanded1, ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_CIRCULANTE_FINANCEIRO,3)
                        if (liabilitiesCurrentExpanded1.value){
                            liabilitiesCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_CIRCULANTE_FINANCEIRO }
                                .forEach { asset ->
                                    BalanceSheetItemRow(asset,
                                        isBalanceSheetItemsDeleteItemDialog,
                                        isBalanceSheetItemsEditItemScreen,
                                        balanceSheetItemId,
                                        balanceSheetItemLevel1,
                                        balanceSheetItemLevel2,
                                        balanceSheetItemLevel3,
                                        balanceSheetItemLevel4Description,
                                        balanceSheetItemValue)
                                }
                        }


                        val liabilitiesCurrentExpanded2 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(liabilitiesCurrentExpanded2, ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_CIRCULANTE_OPERACIONAL,3)
                        if (liabilitiesCurrentExpanded2.value){
                            liabilitiesCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_CIRCULANTE_OPERACIONAL }
                                .forEach { asset ->
                                    BalanceSheetItemRow(asset,
                                        isBalanceSheetItemsDeleteItemDialog,
                                        isBalanceSheetItemsEditItemScreen,
                                        balanceSheetItemId,
                                        balanceSheetItemLevel1,
                                        balanceSheetItemLevel2,
                                        balanceSheetItemLevel3,
                                        balanceSheetItemLevel4Description,
                                        balanceSheetItemValue)
                                }
                        }
                    }

                    val liabilitiesNonCurrentExpanded = remember { mutableStateOf(false) }
                    displayEachExpandableTitleRow(liabilitiesNonCurrentExpanded, ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_NAO_CIRCULANTE,2)

                    if (liabilitiesNonCurrentExpanded.value){


                        val liabilitiesNonCurrentExpanded1 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(liabilitiesNonCurrentExpanded1,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_EMPRESTIMOS_E_FINANCIAMENTOS_DE_LONGO_PRAZO,3)
                        if (liabilitiesNonCurrentExpanded1.value){
                            liabilitiesNonCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_EMPRESTIMOS_E_FINANCIAMENTOS_DE_LONGO_PRAZO }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }


                        val liabilitiesNonCurrentExpanded2 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(liabilitiesNonCurrentExpanded2,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_DEBENTURES,3)
                        if (liabilitiesNonCurrentExpanded2.value){
                            liabilitiesNonCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_DEBENTURES }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }


                        val liabilitiesNonCurrentExpanded3 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(liabilitiesNonCurrentExpanded3,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_OBRIGACOES_FISCAIS_E_TRABALHISTAS_DE_LONGO_PRAZO,3)
                        if (liabilitiesNonCurrentExpanded3.value){
                            liabilitiesNonCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_OBRIGACOES_FISCAIS_E_TRABALHISTAS_DE_LONGO_PRAZO }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }


                        val liabilitiesNonCurrentExpanded4 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(liabilitiesNonCurrentExpanded4,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_PROVISOES_PARA_CONTINGENCIAS,3)
                        if (liabilitiesNonCurrentExpanded4.value){
                            liabilitiesNonCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_PROVISOES_PARA_CONTINGENCIAS }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }


                        val liabilitiesNonCurrentExpanded5 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(liabilitiesNonCurrentExpanded5,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_ARRENDAMENTO_MERCANTIL_FINANCEIRO,3)
                        if (liabilitiesNonCurrentExpanded5.value){
                            liabilitiesNonCurrent.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_ARRENDAMENTO_MERCANTIL_FINANCEIRO }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }
                    }

                    val netWorthExpanded = remember { mutableStateOf(false) }
                    displayEachExpandableTitleRow(netWorthExpanded, ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_PATRIMONIO_LIQUIDO,2)
                    if (netWorthExpanded.value) {

                        val netWorthExpanded1 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(netWorthExpanded1,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_CAPITAL_SOCIAL,3)
                        if (netWorthExpanded1.value){
                            netWorth.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_PATRIMONIO_LIQUIDO }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }

                        val netWorthExpanded2 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(netWorthExpanded2,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_RESERVAS_DE_CAPITAL,3)
                        if (netWorthExpanded2.value){
                            netWorth.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_RESERVAS_DE_CAPITAL }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }

                        val netWorthExpanded3 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(netWorthExpanded3,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_AJUSTES_DE_AVALIACAO_PATRIMONIAL,3)
                        if (netWorthExpanded3.value){
                            netWorth.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_AJUSTES_DE_AVALIACAO_PATRIMONIAL }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }

                        val netWorthExpanded4 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(netWorthExpanded4,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_RESERVAS_DE_LUCROS,3)
                        if (netWorthExpanded4.value){
                            netWorth.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_RESERVAS_DE_LUCROS }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }

                        val netWorthExpanded5 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(netWorthExpanded5,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_ACOES_EM_TESOURARIA,3)
                        if (netWorthExpanded5.value){
                            netWorth.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_ACOES_EM_TESOURARIA }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }

                        val netWorthExpanded6 = remember { mutableStateOf(false) }
                        displayEachExpandableTitleRow(netWorthExpanded6,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_PREJUIZOS_ACUMULADOS,3)
                        if (netWorthExpanded6.value){
                            netWorth.filter { it.level3 == ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_PREJUIZOS_ACUMULADOS }.forEach { asset->
                                BalanceSheetItemRow(asset,
                                    isBalanceSheetItemsDeleteItemDialog,
                                    isBalanceSheetItemsEditItemScreen,
                                    balanceSheetItemId,
                                    balanceSheetItemLevel1,
                                    balanceSheetItemLevel2,
                                    balanceSheetItemLevel3,
                                    balanceSheetItemLevel4Description,
                                    balanceSheetItemValue)
                            }
                        }
                    }
                }
            }
        }
    }

    when {
        isBalanceSheetItemsDeleteItemDialog.value -> {
            BalanceSheetItemDeleteDialog(isBalanceSheetItemsDeleteItemDialog,
                balanceSheetItemId,
                balanceSheetItemLevel4Description,
                balanceSheetItemValue,
                balanceSheetViewModel,
                context,
                )
        }
    }
}


@Composable
private fun BalanceSheetItemRow(
    item: BalanceSheetItem,
    isBalanceSheetItemsDeleteItemDialog: MutableState<Boolean>,
    isBalanceSheetItemsEditItemScreen: MutableState<Boolean>,
    balanceSheetItemId: MutableState<Long>,
    balanceSheetItemLevel1: MutableState<String>,
    balanceSheetItemLevel2: MutableState<String>,
    balanceSheetItemLevel3: MutableState<String>,
    balanceSheetItemLevel4Description: MutableState<String>,
    balanceSheetItemValue: MutableState<Double>,
){

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
                    isBalanceSheetItemsEditItemScreen.value=true
                    balanceSheetItemId.value=item.balanceSheetItemId
                    balanceSheetItemLevel1.value=item.level1
                    balanceSheetItemLevel2.value=item.level2
                    balanceSheetItemLevel3.value=item.level3
                    balanceSheetItemLevel4Description.value=item.level4Description
                    balanceSheetItemValue.value=item.value
                },
                role = Role.Button
            )
    ) {
        Text(
            text = item.level4Description,
            modifier = Modifier
                .fillMaxWidth(0.4f),
            style = TextStyle(fontFamily = FontFamily.SansSerif),
            color = MaterialTheme.colorScheme.primary,
        )


        Text(
            text = item.value.toScreen(),
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
                        isBalanceSheetItemsDeleteItemDialog.value = true
                        balanceSheetItemId.value=item.balanceSheetItemId
                        balanceSheetItemLevel1.value=item.level1
                        balanceSheetItemLevel2.value=item.level2
                        balanceSheetItemLevel3.value=item.level3
                        balanceSheetItemLevel4Description.value=item.level4Description
                        balanceSheetItemValue.value=item.value
                    }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.delete_24px),
                    contentDescription = stringResource(R.string.balance_sheet_delete),
                    modifier = Modifier,
                    tint = Color.Red
                )
            }
        }
    }
}


@Composable
fun Level4DropdownMenu(level4DescriptionDropdownMenu: MutableState<String>, level4Description: List<String>) {

    val expanded = remember { mutableStateOf(false) }

    Row {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded.value = !expanded.value
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
                        IconButton(onClick = { expanded.value = !expanded.value },
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(24.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "")
                        }
                        DropdownMenu (
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            level4Description.forEach { item ->

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
                                        expanded.value = !expanded.value
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = "Conta Sintética: Analítica",
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
private fun BalanceSheetItemsAddItemScreenContent(
    balanceSheetViewModel: BalanceSheetViewModel,
    accountingAccountsViewModel: AccountingAccountsViewModel,
    modifier: Modifier = Modifier,
    balanceSheetName: MutableState<String>,
    balanceSheetId: MutableState<Long>,
    balanceSheetDate: MutableState<String>,
    isBalanceSheetItemsAddItemScreen: MutableState<Boolean>,
) {

    val accountingAccountlevel1 = remember { mutableStateOf("") }
    val accountingAccountlevel2 = remember { mutableStateOf("") }
    val accountingAccountlevel3 = remember { mutableStateOf("") }
    val accountingAccountlevel4Description = remember { mutableStateOf("") }
    val itemValue = remember { mutableStateOf("") }

    val level1Types = getLevel1Types("")
    val level2Types = getLevel2Types("",accountingAccountlevel1.value)
    val level3Types = getLevel3Types("",accountingAccountlevel1.value,accountingAccountlevel2.value)

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
                    text = balanceSheetName.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = balanceSheetDate.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Level1DropdownMenu(accountingAccountlevel1,accountingAccountlevel2,accountingAccountlevel3,accountingAccountlevel4Description,level1Types)
            Level2DropdownMenu(accountingAccountlevel2,accountingAccountlevel3, accountingAccountlevel4Description, level2Types)
            Level3DropdownMenu(accountingAccountlevel3,accountingAccountlevel4Description,level3Types)

            val accountingAccountsFlow=accountingAccountsViewModel.getAccountingAccounts(accountingAccountlevel1.value,accountingAccountlevel2.value,accountingAccountlevel3.value)
            val accountingAccounts by accountingAccountsFlow.collectAsStateWithLifecycle(initialValue = emptyList())
            val level4Description = ArrayList<String>()
            accountingAccounts.forEach { accountingAccount ->
                level4Description.add(accountingAccount.level4 + ": " + accountingAccount.description)
            }
            Level4DropdownMenu(accountingAccountlevel4Description, level4Description)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            )
            {
                OutlinedTextField(
                    value = itemValue.value,
                    onValueChange = {
                        itemValue.value = it
                    },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = stringResource(R.string.balance_sheet_item_value),
                        )
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
                        isBalanceSheetItemsAddItemScreen.value = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.balance_sheet_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

                val context = LocalContext.current
                val itemAddedSuccess = stringResource(R.string.balance_sheet_item_added_success)
                val emptyTypeMsg = stringResource(R.string.balance_sheet_item_empty_type)
                val emptyCurrencyMsg = stringResource(R.string.balance_sheet_item_empty_currency)
                val emptySubTypeMsg = stringResource(R.string.balance_sheet_item_empty_subtype)
                val emptyAccountMsg = stringResource(R.string.balance_sheet_item_empty_account)
                val emptyValueMsg = stringResource(R.string.balance_sheet_item_empty_value)

                Button(
                    onClick = {

                        if (accountingAccountlevel1.value.isEmpty()){
                            Toast.makeText(
                                context,
                                emptyTypeMsg,
                                Toast.LENGTH_SHORT,
                            ).show()
                        }else
                            if (accountingAccountlevel2.value.isEmpty()){
                                Toast.makeText(
                                    context,
                                    emptyCurrencyMsg,
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }else
                                if (accountingAccountlevel3.value.isEmpty()){
                                    Toast.makeText(
                                        context,
                                        emptySubTypeMsg,
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }else
                                    if (accountingAccountlevel4Description.value.isEmpty()){
                                        Toast.makeText(
                                            context,
                                            emptyAccountMsg,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }else
                                        if (itemValue.value.screenToDouble()==0.0){
                                            Toast.makeText(
                                                context,
                                                emptyValueMsg,
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                        }else{
                                            balanceSheetViewModel.saveBalanceSheetItem(BalanceSheetItem(0,balanceSheetId.value,accountingAccountlevel1.value,accountingAccountlevel2.value,accountingAccountlevel3.value,accountingAccountlevel4Description.value,itemValue.value.screenToDouble(),0))
                                            isBalanceSheetItemsAddItemScreen.value = false
                                            Toast.makeText(
                                                context,
                                                itemAddedSuccess,
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                    }
                    },
                ) {
                    Text(
                        text = stringResource(R.string.balance_sheet_save),
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
fun Level1DropdownMenu(level1DropdownMenu: MutableState<String>,level2DropdownMenu: MutableState<String>,level3DropdownMenu: MutableState<String>,level4DropdownMenu: MutableState<String>, level1List: List<String>) {
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
                                        level2DropdownMenu.value = ""
                                        level3DropdownMenu.value = ""
                                        level4DropdownMenu.value = ""
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = "Conta Patrimonial:",
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
fun Level2DropdownMenu(level2DropdownMenu: MutableState<String>,level3DropdownMenu: MutableState<String>, level4DropdownMenu: MutableState<String>,level2List: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Row {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                },
            value = level2DropdownMenu.value,
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
                            level2List.forEach { item ->

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = item,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                    },
                                    onClick = {
                                        level2DropdownMenu.value = item
                                        level3DropdownMenu.value = ""
                                        level4DropdownMenu.value = ""
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = "Liquidez:",
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
fun Level3DropdownMenu(level3DropdownMenu: MutableState<String>,level4DropdownMenu: MutableState<String>, level3List: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Row {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                },
            value = level3DropdownMenu.value,
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
                            level3List.forEach { item ->

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = item,
                                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                    },
                                    onClick = {
                                        level3DropdownMenu.value = item
                                        level4DropdownMenu.value = ""
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = "Subtipo:",
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
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
private fun BalanceSheetItemsEditItemScreenContent(
    balanceSheetViewModel: BalanceSheetViewModel,
    accountingAccountsViewModel: AccountingAccountsViewModel,
    modifier: Modifier = Modifier,
    balanceSheetName: MutableState<String>,
    balanceSheetId: MutableState<Long>,
    balanceSheetDate: MutableState<String>,
    isBalanceSheetItemsEditItemScreen: MutableState<Boolean>,
    balanceSheetItemId: MutableState<Long>,
    balanceSheetItemLevel1: MutableState<String>,
    balanceSheetItemLevel2: MutableState<String>,
    balanceSheetItemLevel3: MutableState<String>,
    balanceSheetItemLevel4Description: MutableState<String>,
    balanceSheetItemValue: MutableState<Double>,
    ) {

    val accountingAccountlevel1 = remember { mutableStateOf(balanceSheetItemLevel1.value) }
    val accountingAccountlevel2 = remember { mutableStateOf(balanceSheetItemLevel2.value) }
    val accountingAccountlevel3 = remember { mutableStateOf(balanceSheetItemLevel3.value) }
    val accountingAccountlevel4Description = remember { mutableStateOf(balanceSheetItemLevel4Description.value) }
    val itemValue = remember { mutableStateOf(balanceSheetItemValue.value.toScreen()) }

    val level1Types = getLevel1Types("")
    val level2Types = getLevel2Types("",accountingAccountlevel1.value)
    val level3Types = getLevel3Types("",accountingAccountlevel1.value,accountingAccountlevel2.value)


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
                    text = balanceSheetName.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = balanceSheetDate.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Level1DropdownMenu(accountingAccountlevel1,accountingAccountlevel2,accountingAccountlevel3,accountingAccountlevel4Description,level1Types)
            Level2DropdownMenu(accountingAccountlevel2,accountingAccountlevel3,accountingAccountlevel4Description,level2Types)
            Level3DropdownMenu(accountingAccountlevel3,accountingAccountlevel4Description,level3Types)

            val accountingAccountsFlow=accountingAccountsViewModel.getAccountingAccounts(accountingAccountlevel1.value,accountingAccountlevel2.value,accountingAccountlevel3.value)
            val accountingAccounts by accountingAccountsFlow.collectAsStateWithLifecycle(initialValue = emptyList())
            val level4Description = ArrayList<String>()
            accountingAccounts.forEach { accountingAccount ->
                level4Description.add(accountingAccount.level4 + ": " + accountingAccount.description)
            }
            Level4DropdownMenu(accountingAccountlevel4Description, level4Description)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            )
            {
                OutlinedTextField(
                    value = itemValue.value,
                    onValueChange = {
                        itemValue.value = it
                    },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = stringResource(R.string.balance_sheet_item_value),
                        )
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
                        isBalanceSheetItemsEditItemScreen.value = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.balance_sheet_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

                val context = LocalContext.current
                val itemChangedSuccess = stringResource(R.string.balance_sheet_item_changed_success)
                val emptyTypeMsg = stringResource(R.string.balance_sheet_item_empty_type)
                val emptyCurrencyMsg = stringResource(R.string.balance_sheet_item_empty_currency)
                val emptySubTypeMsg = stringResource(R.string.balance_sheet_item_empty_subtype)
                val emptyAccountMsg = stringResource(R.string.balance_sheet_item_empty_account)
                val emptyValueMsg = stringResource(R.string.balance_sheet_item_empty_value)

                Button(
                    onClick = {

                        if (accountingAccountlevel1.value.isEmpty()){
                            Toast.makeText(
                                context,
                                emptyTypeMsg,
                                Toast.LENGTH_SHORT,
                            ).show()
                        }else
                            if (accountingAccountlevel2.value.isEmpty()){
                                Toast.makeText(
                                    context,
                                    emptyCurrencyMsg,
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }else
                                if (accountingAccountlevel3.value.isEmpty()){
                                    Toast.makeText(
                                        context,
                                        emptySubTypeMsg,
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }else
                                    if (accountingAccountlevel4Description.value.isEmpty()){
                                        Toast.makeText(
                                            context,
                                            emptyAccountMsg,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }else
                                        if (itemValue.value.screenToDouble()==0.0){
                                            Toast.makeText(
                                                context,
                                                emptyValueMsg,
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                        }else{
                                            balanceSheetViewModel.saveBalanceSheetItem(BalanceSheetItem(balanceSheetItemId.value,balanceSheetId.value,accountingAccountlevel1.value,accountingAccountlevel2.value,accountingAccountlevel3.value,accountingAccountlevel4Description.value,itemValue.value.screenToDouble(),0))
                                            isBalanceSheetItemsEditItemScreen.value = false
                                            Toast.makeText(
                                                context,
                                                itemChangedSuccess,
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                        }
                    },
                ) {
                    Text(
                        text = stringResource(R.string.balance_sheet_save),
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
private fun BalanceSheetHomeScreenContent(
    balanceSheetViewModel: BalanceSheetViewModel,
    modifier: Modifier = Modifier,
    isBalanceSheetEditScreen: MutableState<Boolean>,
    balanceSheetName: MutableState<String>,
    balanceSheetId: MutableState<Long>,
    balanceSheetDate: MutableState<String>,
    isBalanceSheetItemsScreen: MutableState<Boolean>,
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
            val balanceSheetsFlow = balanceSheetViewModel.getBalanceSheets()
            val balanceSheets by balanceSheetsFlow.collectAsStateWithLifecycle(initialValue = emptyList())

            balanceSheets.forEach { balanceSheet ->

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = false,
                            onClick = {
                                isBalanceSheetEditScreen.value=true
                                balanceSheetName.value=balanceSheet.name
                                balanceSheetId.value=balanceSheet.balanceSheetId
                                balanceSheetDate.value = fmt.format(balanceSheet.date)
                            },
                            role = Role.Button
                        )
                ) {

                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                ) {
                            Text(
                                text = balanceSheet.name,
                                style = TextStyle(fontFamily = FontFamily.SansSerif),
                                color = MaterialTheme.colorScheme.primary,
                            )
                            Text(
                                text = fmt.format(balanceSheet.date),
                                style = TextStyle(fontFamily = FontFamily.SansSerif),
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }

                    Row( horizontalArrangement = Arrangement.End) {
                        TextButton(
                            modifier = Modifier.padding(1.dp),
                            onClick =
                                {
                                    isBalanceSheetEditScreen.value = true
                                    balanceSheetName.value = balanceSheet.name
                                    balanceSheetId.value = balanceSheet.balanceSheetId
                                    balanceSheetDate.value = fmt.format(balanceSheet.date)
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
                                    isBalanceSheetItemsScreen.value = true
                                    balanceSheetId.value = balanceSheet.balanceSheetId
                                    balanceSheetName.value = balanceSheet.name
                                    balanceSheetDate.value = fmt.format(balanceSheet.date)
                                }
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.format_list_bulleted_24px),
                                contentDescription = stringResource(R.string.balance_sheet_items),
                                modifier = Modifier
                            )
                        }

                        TextButton(
                            modifier = Modifier.padding(3.dp),
                            onClick =
                                {
                                    isDeleting.value = true
                                    balanceSheetId.value = balanceSheet.balanceSheetId
                                    balanceSheetName.value = balanceSheet.name
                                }
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.delete_24px),
                                contentDescription = stringResource(R.string.balance_sheet_delete),
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
            BalanceSheetDeleteDialog(isDeleting, balanceSheetId, balanceSheetName, balanceSheetViewModel,context)
        }
    }
}



/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun BalanceSheetAddScreenContent(
    balanceSheetViewModel: BalanceSheetViewModel,
    modifier: Modifier = Modifier,
    isBalanceSheetAddScreen: MutableState<Boolean>
) {

    val balanceSheetName = remember { mutableStateOf("") }
    val balanceSheetDate = remember { mutableStateOf("") }
    val context = LocalContext.current
    val openDateCreateDialog= remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val fmt = SimpleDateFormat(stringResource(R.string.fmtDatePattern))

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
                value = balanceSheetName.value,
                onValueChange = {
                    balanceSheetName.value = it
                },
                placeholder = { Text("") },
                label = {
                    Text(
                        text = stringResource(R.string.balance_sheet_name),
                    )
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier=Modifier.fillMaxWidth().selectable(
                    selected = false,
                    onClick = {
                        openDateCreateDialog.value=true
                    },
                    role = Role.Button
                )
            ) {

                OutlinedTextField(
                    value = balanceSheetDate.value,
                    onValueChange =
                        {
                            balanceSheetDate.value = it
                        },
                    placeholder = { Text("") },
                    label = {
                            Text(
                                text = stringResource(R.string.balance_sheet_date),
                            )
                    },
                    modifier = Modifier.onFocusChanged { focusState ->
                        if (focusState.isFocused){
                            openDateCreateDialog.value=true
                            focusManager.clearFocus()
                        }
                    },
                )
                    TextButton(
                        modifier = Modifier.padding(3.dp),
                        onClick =
                            {
                                openDateCreateDialog.value=true
                            }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.calendar_month_24px),
                            contentDescription = stringResource(R.string.balance_sheet_date),
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
                            isBalanceSheetAddScreen.value = false
                            balanceSheetDate.value = ""
                            balanceSheetName.value = ""
                        },
                    )
                {
                    Text(
                        text = stringResource(R.string.balance_sheet_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                            )
                    )
                }

                val sucessMsg = stringResource(R.string.balance_sheet_added_sucess)
                val missingNameMsg = stringResource(R.string.balance_sheet_missing_name)
                val missingDateMsg = stringResource(R.string.balance_sheet_missing_date)

                Button(
                    onClick =
                        {
                            if (balanceSheetName.value.isEmpty()){
                                Toast.makeText(
                                        context,
                                        missingNameMsg,
                                        Toast.LENGTH_SHORT,
                                    ).show()
                            }else {
                                if (balanceSheetDate.value.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        missingDateMsg,
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                } else {
                                    try {
                                        balanceSheetViewModel.saveBalanceSheet(BalanceSheet(0, balanceSheetName.value, fmt.parse(balanceSheetDate.value)!!, 0))
                                        Toast.makeText(
                                            context,
                                            sucessMsg,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                        isBalanceSheetAddScreen.value = false
                                        balanceSheetDate.value = ""
                                        balanceSheetName.value = ""
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            e.message,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }
                                }
                            }
                        }
                )
                {
                    Text(
                        text = stringResource(R.string.balance_sheet_save),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
            }
        }
    }

    when {
        openDateCreateDialog.value -> {
            val fmt = stringResource(R.string.fmtDatePattern)
            DatePickerModal(
                onDateSelected = {
                    if (it != null) {
                        balanceSheetDate.value = SimpleDateFormat(fmt).format(Date(it))
                    }
                },
                openDialog = openDateCreateDialog, title = stringResource(R.string.balance_sheet_date)
            )
        }
    }

}


@Composable
fun BalanceSheetDeleteDialog(
    isDeleting: MutableState<Boolean>,
    isDeletingId: MutableState<Long>,
    isDeletingName: MutableState<String>,
    balanceSheetViewModel: BalanceSheetViewModel,
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
                //.height(500.dp),

            title = {
                Text(
                    text = stringResource(R.string.balance_sheet_delete_balance),
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
                            text = isDeletingName.value,
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
                            isDeleting.value = false
                            isDeletingId.value = 0
                            isDeletingName.value = ""
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.balance_sheet_cancel),
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    val deleteSuccessMsg = stringResource(R.string.balance_sheet_deleted_success)
                    Button(
                        onClick = {
                            balanceSheetViewModel.deleteBalanceSheet(BalanceSheet(isDeletingId.value,isDeletingName.value, Date(0), 0))
                            isDeleting.value = false
                            isDeletingId.value = 0
                            isDeletingName.value = ""
                            Toast.makeText(
                                context,
                                deleteSuccessMsg,
                                Toast.LENGTH_SHORT,
                            ).show()
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.balance_sheet_delete),
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
fun BalanceSheetItemDeleteDialog(
    isBalanceSheetItemsDeleteItemDialog: MutableState<Boolean>,
    balanceSheetItemId: MutableState<Long>,
    balanceSheetItemLevel4Description: MutableState<String>,
    balanceSheetItemValue: MutableState<Double>,
    balanceSheetViewModel: BalanceSheetViewModel,
    context: Context
) {
    if (isBalanceSheetItemsDeleteItemDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                isBalanceSheetItemsDeleteItemDialog.value = false
            },
            modifier = Modifier
                .width(550.dp),
                //.height(500.dp),

            title = {
                Text(
                    text = stringResource(R.string.balance_sheet_delete_account),
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
                        text = balanceSheetItemLevel4Description.value,
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = balanceSheetItemValue.value.toScreen(),
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
                            isBalanceSheetItemsDeleteItemDialog.value = false
                            balanceSheetItemId.value=0
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.balance_sheet_cancel),
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    val deletedSuccessMsg = stringResource(R.string.balance_sheet_deleted_success)
                    Button(
                        onClick = {
                            balanceSheetViewModel.deleteBalanceSheetItem(BalanceSheetItem(balanceSheetItemId.value,0, "", "", "","",0.0,0))
                            isBalanceSheetItemsDeleteItemDialog.value = false
                            balanceSheetItemId.value=0
                            Toast.makeText(
                                context,
                                deletedSuccessMsg,
                                Toast.LENGTH_SHORT,
                            ).show()
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.balance_sheet_delete),
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