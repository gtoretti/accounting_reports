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

package com.example.jetnews.ui.accountingAccounts


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetnews.R
import com.example.jetnews.data.accountingAccounts.AccountingAccount

import com.example.jetnews.ui.utils.showExpandCollapseButton
import com.example.jetnews.ui.utils.switchState
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_ALL

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
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_TYPE_ASSET
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_TYPE_RESULT
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_FINANCING
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_INVESTING
import com.example.jetnews.utils.ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_OPERATING


import kotlin.String

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.Boolean

/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountingAccountsScreen(
    accountingAccountsViewModel: AccountingAccountsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isAdding = remember { mutableStateOf(false) }
    var isEditing = remember { mutableStateOf(false) }
    var isEditingType = remember { mutableStateOf("") }
    var isEditingLevel1 = remember { mutableStateOf("") }
    var isEditingLevel2 = remember { mutableStateOf("") }
    var isEditingLevel3 = remember { mutableStateOf("") }
    var isEditingLevel4 = remember { mutableStateOf("") }
    var isEditingDescription = remember { mutableStateOf("") }
    var isEditingId = remember { mutableLongStateOf(0) }

    when {
        isAdding.value -> {
            AccountingAccountsAddScreen(
                accountingAccountsViewModel = accountingAccountsViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                snackbarHostState,
                isAdding
            )
        }
        isEditing.value -> {
            AccountingAccountsEditScreen(
                accountingAccountsViewModel = accountingAccountsViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                snackbarHostState,
                isEditing,
                isEditingType,
                isEditingLevel1,
                isEditingLevel2,
                isEditingLevel3,
                isEditingLevel4,
                isEditingDescription,
                isEditingId
            )
        }
        else -> {
            AccountingAccountsHomeScreen(
                accountingAccountsViewModel = accountingAccountsViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                snackbarHostState,
                isAdding,
                isEditing,
                isEditingType,
                isEditingLevel1,
                isEditingLevel2,
                isEditingLevel3,
                isEditingLevel4,
                isEditingDescription,
                isEditingId
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
fun AccountingAccountsHomeScreen(
    accountingAccountsViewModel: AccountingAccountsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isAdding: MutableState<Boolean>,
    isEditing: MutableState<Boolean>,
    isEditingType: MutableState<String>,
    isEditingLevel1: MutableState<String>,
    isEditingLevel2: MutableState<String>,
    isEditingLevel3: MutableState<String>,
    isEditingLevel4: MutableState<String>,
    isEditingDescription: MutableState<String>,
    isEditingId: MutableState<Long>,
) {

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.accounting_accounts_title),
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
                            isAdding.value=true
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = stringResource(R.string.addAccountingAccount),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        AccountingAccountsHomeScreenContent(
            accountingAccountsViewModel,
            screenModifier,
            isEditing,
            isEditingId,
            isEditingType,
            isEditingLevel1,
            isEditingLevel2,
            isEditingLevel3,
            isEditingLevel4,
            isEditingDescription,
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
fun AccountingAccountsAddScreen(
    accountingAccountsViewModel: AccountingAccountsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isAdding: MutableState<Boolean>
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.addAccountingAccount),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                        IconButton(onClick = {
                            isAdding.value=false
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
        AccountingAccountAddScreenContent(
            accountingAccountsViewModel,
            screenModifier,
            isAdding
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
fun AccountingAccountsEditScreen(
    accountingAccountsViewModel: AccountingAccountsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isEditing: MutableState<Boolean>,
    isEditingType: MutableState<String>,
    isEditingLevel1: MutableState<String>,
    isEditingLevel2: MutableState<String>,
    isEditingLevel3: MutableState<String>,
    isEditingLevel4: MutableState<String>,
    isEditingDescription: MutableState<String>,
    isEditingId: MutableState<Long>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.editAccountingAccount),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isEditing.value=false
                        isEditingType.value = ""
                        isEditingLevel1.value=""
                        isEditingLevel2.value=""
                        isEditingLevel3.value=""
                        isEditingLevel4.value=""
                        isEditingDescription.value=""
                        isEditingId.value=0
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
        AccountingAccountsEditScreenContent(
            accountingAccountsViewModel,
            screenModifier,
            isEditing,
            isEditingType,
            isEditingLevel1,
            isEditingLevel2,
            isEditingLevel3,
            isEditingLevel4,
            isEditingDescription,
            isEditingId
        )
    }
}


/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun AccountingAccountsHomeScreenContent(
    accountingAccountsViewModel: AccountingAccountsViewModel,
    modifier: Modifier = Modifier,
    isEditing: MutableState<Boolean>,
    isEditingId: MutableState<Long>,
    isEditingType: MutableState<String>,
    isEditingLevel1: MutableState<String>,
    isEditingLevel2: MutableState<String>,
    isEditingLevel3: MutableState<String>,
    isEditingLevel4: MutableState<String>,
    isEditingDescription: MutableState<String>,
) {

    val context = LocalContext.current
    var isDeleting = remember { mutableStateOf(false) }
    var isDeletingId = remember { mutableLongStateOf(0) }
    var isDeletingType = remember { mutableStateOf("") }
    var isDeletingLevel1 = remember { mutableStateOf("") }
    var isDeletingLevel2 = remember { mutableStateOf("") }
    var isDeletingLevel3 = remember { mutableStateOf("") }
    var isDeletingLevel4 = remember { mutableStateOf("") }
    var isDeletingDescription = remember { mutableStateOf("") }
    var accountingAccountTypeFilter = remember { mutableStateOf("Selecione...") }
    var accountingAccountLevel1Filter = remember { mutableStateOf(ACCOUNTING_ACCOUNTS_ALL) }
    var accountingAccountLevel2Filter = remember { mutableStateOf(ACCOUNTING_ACCOUNTS_ALL) }
    var accountingAccountLevel3Filter = remember { mutableStateOf(ACCOUNTING_ACCOUNTS_ALL) }

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

            /*
            Search filters dropdowns displaying:
             */
            val types = getTypes("")
            val level1Types = getLevel1Types(ACCOUNTING_ACCOUNTS_ALL)
            val level2Types = getLevel2Types(ACCOUNTING_ACCOUNTS_ALL, accountingAccountLevel1Filter.value)
            val level3Types =
                getLevel3Types(ACCOUNTING_ACCOUNTS_ALL, accountingAccountLevel1Filter.value, accountingAccountLevel2Filter.value)

            TypeDropdownMenu(accountingAccountTypeFilter, types)
            if (accountingAccountTypeFilter.value.isNotEmpty() && accountingAccountTypeFilter.value == ACCOUNTING_ACCOUNTS_TYPE_ASSET) {
                Level1DropdownMenu(accountingAccountLevel1Filter, accountingAccountLevel2Filter, accountingAccountLevel3Filter, level1Types)
                if (accountingAccountLevel1Filter.value.isNotEmpty() && accountingAccountLevel1Filter.value != ACCOUNTING_ACCOUNTS_ALL) {
                    Level2DropdownMenu(accountingAccountLevel2Filter, accountingAccountLevel3Filter,level2Types)
                    if (accountingAccountLevel2Filter.value.isNotEmpty() && accountingAccountLevel2Filter.value != ACCOUNTING_ACCOUNTS_ALL) {
                        Level3DropdownMenu(accountingAccountLevel3Filter, level3Types)
                    }
                }
            }


            /*
            Database Searching using selected filters:
            */

            var accountingAccountsFlow: Flow<List<AccountingAccount>> = MutableStateFlow(emptyList())

            if (accountingAccountTypeFilter.value.isNotEmpty() && accountingAccountTypeFilter.value == ACCOUNTING_ACCOUNTS_TYPE_ASSET) {
                if (accountingAccountLevel1Filter.value != ACCOUNTING_ACCOUNTS_ALL && accountingAccountLevel2Filter.value != ACCOUNTING_ACCOUNTS_ALL && accountingAccountLevel3Filter.value != ACCOUNTING_ACCOUNTS_ALL) {
                    accountingAccountsFlow = accountingAccountsViewModel.getAccountingAccounts(
                        accountingAccountLevel1Filter.value,
                        accountingAccountLevel2Filter.value,
                        accountingAccountLevel3Filter.value
                    )
                } else
                    if (accountingAccountLevel1Filter.value != ACCOUNTING_ACCOUNTS_ALL && accountingAccountLevel2Filter.value != ACCOUNTING_ACCOUNTS_ALL) {
                        accountingAccountsFlow = accountingAccountsViewModel.getAccountingAccounts(
                            accountingAccountLevel1Filter.value,
                            accountingAccountLevel2Filter.value
                        )
                    } else
                        if (accountingAccountLevel1Filter.value != ACCOUNTING_ACCOUNTS_ALL) {
                            accountingAccountsFlow = accountingAccountsViewModel.getAccountingAccounts(accountingAccountLevel1Filter.value)
                        } else {
                            accountingAccountsFlow = accountingAccountsViewModel.getAccountingAccounts()
                        }
            } else {
                accountingAccountsFlow = accountingAccountsViewModel.getTypeResultAccountingAccounts()
            }

            val accountingAccounts by accountingAccountsFlow.collectAsStateWithLifecycle(initialValue = emptyList())

            /*
            Search results displaying, sorted by levels:
             */

            if (accountingAccountTypeFilter.value.isNotEmpty() && accountingAccountTypeFilter.value == ACCOUNTING_ACCOUNTS_TYPE_ASSET) {

                val level1Ativo = accountingAccounts.filter { it.level1 == ACCOUNTING_ACCOUNTS_LEVEL1_ATIVO }

                if (level1Ativo.isNotEmpty()) {
                    val level1AtivoExpanded = remember { mutableStateOf(false) }
                    displayEachExpandableTitleRow(level1AtivoExpanded, ACCOUNTING_ACCOUNTS_LEVEL1_ATIVO, 1)
                    val level2AtivoCirculante = level1Ativo.filter { it.level2 == ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_CIRCULANTE }

                    if (level1AtivoExpanded.value) {
                        if (level2AtivoCirculante.isNotEmpty()) {
                            val level2AtivoCirculanteExpanded = remember { mutableStateOf(false) }
                            displayEachExpandableTitleRow(level2AtivoCirculanteExpanded, ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_CIRCULANTE, 2)
                            if (level2AtivoCirculanteExpanded.value) {

                                val level3AtivoCirculanteTypes = listOf(
                                    ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_CAIXA_E_EQUIVALENTES_DE_CAIXA,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_CONTAS_A_RECEBER,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_ESTOQUES,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_DESPESAS_ANTECIPADAS,
                                )

                                level3AtivoCirculanteTypes.forEach {
                                    displayLevel3Group(
                                        level2AtivoCirculante,
                                        it,
                                        isEditing,
                                        isEditingType,
                                        isEditingLevel1,
                                        isEditingLevel2,
                                        isEditingLevel3,
                                        isEditingLevel4,
                                        isEditingDescription,
                                        isEditingId,
                                        isDeletingId,
                                        isDeleting,
                                        isDeletingType,
                                        isDeletingLevel1,
                                        isDeletingLevel2,
                                        isDeletingLevel3,
                                        isDeletingLevel4,
                                        isDeletingDescription
                                    )
                                }
                            }
                        }

                        val level2AtivoNaoCirculante = level1Ativo.filter { it.level2 == ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_NAO_CIRCULANTE }
                        if (level2AtivoNaoCirculante.isNotEmpty()) {
                            val level2AtivoNaoCirculanteExpanded = remember { mutableStateOf(false) }
                            displayEachExpandableTitleRow(level2AtivoNaoCirculanteExpanded, ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_NAO_CIRCULANTE, 2)
                            if (level2AtivoNaoCirculanteExpanded.value) {

                                val level3AtivoNaoCirculanteTypes = listOf(
                                    ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_IMOBILIZADO,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_INVESTIMENTOS_DE_LONGO_PRAZO,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_ATIVOS_INTANGIVEIS,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_ATIVOS_REALIZAVEIS_A_LONGO_PRAZO,
                                )

                                level3AtivoNaoCirculanteTypes.forEach {
                                    displayLevel3Group(
                                        level2AtivoNaoCirculante,
                                        it,
                                        isEditing,
                                        isEditingType,
                                        isEditingLevel1,
                                        isEditingLevel2,
                                        isEditingLevel3,
                                        isEditingLevel4,
                                        isEditingDescription,
                                        isEditingId,
                                        isDeletingId,
                                        isDeleting,
                                        isDeletingType,
                                        isDeletingLevel1,
                                        isDeletingLevel2,
                                        isDeletingLevel3,
                                        isDeletingLevel4,
                                        isDeletingDescription
                                    )
                                }
                            }
                        }
                    }
                }

                val level1Passivo = accountingAccounts.filter { it.level1 == ACCOUNTING_ACCOUNTS_LEVEL1_PASSIVO }
                if (level1Passivo.isNotEmpty()) {
                    val level1PassivoExpanded = remember { mutableStateOf(false) }
                    displayEachExpandableTitleRow(level1PassivoExpanded, ACCOUNTING_ACCOUNTS_LEVEL1_PASSIVO, 1)

                    if (level1PassivoExpanded.value) {
                        val level2PassivoCirculante = level1Passivo.filter { it.level2 == ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_CIRCULANTE }
                        if (level2PassivoCirculante.isNotEmpty()) {
                            val level2PassivoCirculanteExpanded = remember { mutableStateOf(false) }
                            displayEachExpandableTitleRow(level2PassivoCirculanteExpanded, ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_CIRCULANTE, 2)
                            if (level2PassivoCirculanteExpanded.value) {
                                val level3PassivoCirculante = listOf(
                                    ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_CIRCULANTE_FINANCEIRO,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_CIRCULANTE_OPERACIONAL,
                                )

                                level3PassivoCirculante.forEach {
                                    displayLevel3Group(
                                        level2PassivoCirculante,
                                        it,
                                        isEditing,
                                        isEditingType,
                                        isEditingLevel1,
                                        isEditingLevel2,
                                        isEditingLevel3,
                                        isEditingLevel4,
                                        isEditingDescription,
                                        isEditingId,
                                        isDeletingId,
                                        isDeleting,
                                        isDeletingType,
                                        isDeletingLevel1,
                                        isDeletingLevel2,
                                        isDeletingLevel3,
                                        isDeletingLevel4,
                                        isDeletingDescription
                                    )
                                }
                            }
                        }

                        val level2PassivoNaoCirculante = level1Passivo.filter { it.level2 == ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_NAO_CIRCULANTE }
                        if (level2PassivoNaoCirculante.isNotEmpty()) {
                            val level2PassivoNaoCirculanteExpanded = remember { mutableStateOf(false) }
                            displayEachExpandableTitleRow(
                                level2PassivoNaoCirculanteExpanded,
                                ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_NAO_CIRCULANTE,
                                2
                            )
                            if (level2PassivoNaoCirculanteExpanded.value) {

                                val level3PassivoNaoCirculante = listOf(
                                    ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_EMPRESTIMOS_E_FINANCIAMENTOS_DE_LONGO_PRAZO,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_DEBENTURES,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_OBRIGACOES_FISCAIS_E_TRABALHISTAS_DE_LONGO_PRAZO,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_PROVISOES_PARA_CONTINGENCIAS,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_ARRENDAMENTO_MERCANTIL_FINANCEIRO,
                                )

                                level3PassivoNaoCirculante.forEach {
                                    displayLevel3Group(
                                        level2PassivoNaoCirculante,
                                        it,
                                        isEditing,
                                        isEditingType,
                                        isEditingLevel1,
                                        isEditingLevel2,
                                        isEditingLevel3,
                                        isEditingLevel4,
                                        isEditingDescription,
                                        isEditingId,
                                        isDeletingId,
                                        isDeleting,
                                        isDeletingType,
                                        isDeletingLevel1,
                                        isDeletingLevel2,
                                        isDeletingLevel3,
                                        isDeletingLevel4,
                                        isDeletingDescription
                                    )
                                }
                            }
                        }

                        val level2PassivoPatrimonioLiquido =
                            level1Passivo.filter { it.level2 == ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_PATRIMONIO_LIQUIDO }
                        if (level2PassivoPatrimonioLiquido.isNotEmpty()) {
                            val level2PassivoPatrimonioLiquidoExpanded = remember { mutableStateOf(false) }
                            displayEachExpandableTitleRow(
                                level2PassivoPatrimonioLiquidoExpanded,
                                ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_PATRIMONIO_LIQUIDO, 2
                            )
                            if (level2PassivoPatrimonioLiquidoExpanded.value) {

                                val level3PatrimonioLiquidoypes = listOf(
                                    ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_CAPITAL_SOCIAL,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_RESERVAS_DE_CAPITAL,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_AJUSTES_DE_AVALIACAO_PATRIMONIAL,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_RESERVAS_DE_LUCROS,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_ACOES_EM_TESOURARIA,
                                    ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_PREJUIZOS_ACUMULADOS
                                )

                                level3PatrimonioLiquidoypes.forEach {
                                    displayLevel3Group(
                                        level1Passivo,
                                        it,
                                        isEditing,
                                        isEditingType,
                                        isEditingLevel1,
                                        isEditingLevel2,
                                        isEditingLevel3,
                                        isEditingLevel4,
                                        isEditingDescription,
                                        isEditingId,
                                        isDeletingId,
                                        isDeleting,
                                        isDeletingType,
                                        isDeletingLevel1,
                                        isDeletingLevel2,
                                        isDeletingLevel3,
                                        isDeletingLevel4,
                                        isDeletingDescription
                                    )
                                }
                            }
                        }
                    }
                }
            }else if (accountingAccountTypeFilter.value.isNotEmpty() && accountingAccountTypeFilter.value == ACCOUNTING_ACCOUNTS_TYPE_RESULT) {

                val level1Operating = accountingAccounts.filter { it.level1 == ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_OPERATING }
                if (level1Operating.isNotEmpty()) {
                    val level1OperatingExpanded = remember { mutableStateOf(false) }
                    displayEachExpandableTitleRow(level1OperatingExpanded, ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_OPERATING, 1)
                    if (level1OperatingExpanded.value){
                        level1Operating.forEach { accountingAccount ->
                            displayEachAccountingAccountLine(
                                isEditing,
                                isEditingType,
                                isEditingLevel1,
                                isEditingLevel2,
                                isEditingLevel3,
                                isEditingLevel4,
                                isEditingDescription,
                                isEditingId,
                                isDeletingId,
                                isDeleting,
                                isDeletingType,
                                isDeletingLevel1,
                                isDeletingLevel2,
                                isDeletingLevel3,
                                isDeletingLevel4,
                                isDeletingDescription,
                                accountingAccount)
                        }
                    }
                }

                val level1Investing = accountingAccounts.filter { it.level1 == ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_INVESTING }
                if (level1Investing.isNotEmpty()) {
                    val level1InvestingExpanded = remember { mutableStateOf(false) }
                    displayEachExpandableTitleRow(level1InvestingExpanded, ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_INVESTING, 1)
                    if (level1InvestingExpanded.value){
                        level1Investing.forEach { accountingAccount ->
                            displayEachAccountingAccountLine(
                                isEditing,
                                isEditingType,
                                isEditingLevel1,
                                isEditingLevel2,
                                isEditingLevel3,
                                isEditingLevel4,
                                isEditingDescription,
                                isEditingId,
                                isDeletingId,
                                isDeleting,
                                isDeletingType,
                                isDeletingLevel1,
                                isDeletingLevel2,
                                isDeletingLevel3,
                                isDeletingLevel4,
                                isDeletingDescription,
                                accountingAccount)
                        }
                    }
                }

                val level1Financing = accountingAccounts.filter { it.level1 == ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_FINANCING }
                if (level1Financing.isNotEmpty()) {
                    val level1FinancingExpanded = remember { mutableStateOf(false) }
                    displayEachExpandableTitleRow(level1FinancingExpanded, ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_FINANCING, 1)
                    if (level1FinancingExpanded.value){
                        level1Financing.forEach { accountingAccount ->
                            displayEachAccountingAccountLine(
                                isEditing,
                                isEditingType,
                                isEditingLevel1,
                                isEditingLevel2,
                                isEditingLevel3,
                                isEditingLevel4,
                                isEditingDescription,
                                isEditingId,
                                isDeletingId,
                                isDeleting,
                                isDeletingType,
                                isDeletingLevel1,
                                isDeletingLevel2,
                                isDeletingLevel3,
                                isDeletingLevel4,
                                isDeletingDescription,
                                accountingAccount)
                        }
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {

                    }
                ) {
                    Text(
                        text = "Relatório",
                    )
                }
            }


        }
    }
    when {
        isDeleting.value -> {
            AccountingAccountsDeleteDialog(isDeleting, isDeletingId, isDeletingType, isDeletingLevel1, isDeletingLevel2,isDeletingLevel3,isDeletingLevel4, isDeletingDescription, accountingAccountsViewModel,context)
        }
    }
}

fun getResultTypes(pre:String): List<String>{
    var types = listOf(ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_OPERATING, ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_INVESTING,
        ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_FINANCING
    )
    if (pre.isNotEmpty()){
        types = listOf(pre, ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_OPERATING, ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_INVESTING,ACCOUNTING_ACCOUNTS_TYPE_RESULT_LEVEL1_FINANCING)
    }
    return types
}

fun getTypes(pre:String): List<String>{
    var types = listOf(ACCOUNTING_ACCOUNTS_TYPE_ASSET, ACCOUNTING_ACCOUNTS_TYPE_RESULT)
    if (pre.isNotEmpty()){
        types = listOf(pre, ACCOUNTING_ACCOUNTS_TYPE_ASSET, ACCOUNTING_ACCOUNTS_TYPE_RESULT)
    }
    return types
}

fun getLevel1Types(pre:String): List<String>{
    var level1Types = listOf(ACCOUNTING_ACCOUNTS_LEVEL1_ATIVO, ACCOUNTING_ACCOUNTS_LEVEL1_PASSIVO)
    if (pre.isNotEmpty()){
        level1Types = listOf(pre, ACCOUNTING_ACCOUNTS_LEVEL1_ATIVO, ACCOUNTING_ACCOUNTS_LEVEL1_PASSIVO)
    }
    return level1Types
}

fun getLevel2Types(pre:String, level1: String): List<String>{
    if (level1.isNotEmpty() && level1!=ACCOUNTING_ACCOUNTS_ALL){
        var level2Types = listOf<String>()
        if (level1 == ACCOUNTING_ACCOUNTS_LEVEL1_PASSIVO){
            level2Types = listOf(ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_CIRCULANTE, ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_NAO_CIRCULANTE,
                ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_PATRIMONIO_LIQUIDO
            )
        }else
            if (level1 == ACCOUNTING_ACCOUNTS_LEVEL1_ATIVO){
                level2Types = listOf(ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_CIRCULANTE, ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_NAO_CIRCULANTE
                )
            }
        if (pre.isNotEmpty()){
            val tmp = ArrayList<String>(level2Types)
            tmp.add(0,pre)
            return tmp
        }
        return level2Types
    }
    return listOf()
}

fun getLevel3Types(pre:String, level1: String,level2: String): List<String>{
    if (level2.isNotEmpty() && level2!=ACCOUNTING_ACCOUNTS_ALL) {

        var level3Types = listOf(
            ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_CAPITAL_SOCIAL,
            ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_RESERVAS_DE_CAPITAL,
            ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_AJUSTES_DE_AVALIACAO_PATRIMONIAL,
            ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_RESERVAS_DE_LUCROS,
            ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_ACOES_EM_TESOURARIA,
            ACCOUNTING_ACCOUNTS_LEVEL3_PATRIMONIO_LIQUIDO_PREJUIZOS_ACUMULADOS
        )
        if (level1 == ACCOUNTING_ACCOUNTS_LEVEL1_ATIVO && level2 == ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_CIRCULANTE) {
            level3Types = listOf(
                ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_CAIXA_E_EQUIVALENTES_DE_CAIXA,
                ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_CONTAS_A_RECEBER,
                ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_ESTOQUES,
                ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_CIRCULANTE_DESPESAS_ANTECIPADAS
            )
        } else
            if (level1 == ACCOUNTING_ACCOUNTS_LEVEL1_ATIVO && level2 == ACCOUNTING_ACCOUNTS_LEVEL2_ATIVO_NAO_CIRCULANTE) {
                level3Types = listOf(
                    ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_IMOBILIZADO,
                    ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_INVESTIMENTOS_DE_LONGO_PRAZO,
                    ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_ATIVOS_INTANGIVEIS,
                    ACCOUNTING_ACCOUNTS_LEVEL3_ATIVO_NAO_CIRCULANTE_ATIVOS_REALIZAVEIS_A_LONGO_PRAZO
                )
            } else
                if (level1 == ACCOUNTING_ACCOUNTS_LEVEL1_PASSIVO && level2 == ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_CIRCULANTE) {
                    level3Types = listOf(
                        ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_CIRCULANTE_FINANCEIRO,
                        ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_CIRCULANTE_OPERACIONAL,
                    )
                } else
                    if (level1 == ACCOUNTING_ACCOUNTS_LEVEL1_PASSIVO && level2 == ACCOUNTING_ACCOUNTS_LEVEL2_PASSIVO_NAO_CIRCULANTE) {
                        level3Types = listOf(
                            ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_EMPRESTIMOS_E_FINANCIAMENTOS_DE_LONGO_PRAZO,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_DEBENTURES,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_OBRIGACOES_FISCAIS_E_TRABALHISTAS_DE_LONGO_PRAZO,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_PROVISOES_PARA_CONTINGENCIAS,
                            ACCOUNTING_ACCOUNTS_LEVEL3_PASSIVO_NAO_CIRCULANTE_ARRENDAMENTO_MERCANTIL_FINANCEIRO
                        )
                    }
        if (pre.isNotEmpty()){
            val tmp = ArrayList<String>(level3Types)
            tmp.add(0,pre)
            return tmp
        }
        return level3Types
    }
    return listOf()
}

/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun AccountingAccountAddScreenContent(
    accountingAccountsViewModel: AccountingAccountsViewModel,
    modifier: Modifier = Modifier,
    isAdding: MutableState<Boolean>
) {

    var accountingAccountType = remember { mutableStateOf("") }
    var accountingAccountlevel1 = remember { mutableStateOf("") }
    var accountingAccountlevel2 = remember { mutableStateOf("") }
    var accountingAccountlevel3 = remember { mutableStateOf("") }
    var accountingAccountlevel4 = remember { mutableStateOf("") }
    var description = remember { mutableStateOf("") }

    val context = LocalContext.current

    val types = getTypes("")
    val level1Types = getLevel1Types("")
    val level2Types = getLevel2Types("",accountingAccountlevel1.value)
    val level3Types = getLevel3Types("",accountingAccountlevel1.value,accountingAccountlevel2.value)

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

            TypeDropdownMenu(accountingAccountType,types)
            if (accountingAccountType.value==ACCOUNTING_ACCOUNTS_TYPE_ASSET){
                Level1DropdownMenu(accountingAccountlevel1,accountingAccountlevel2,accountingAccountlevel3, level1Types)
                Level2DropdownMenu(accountingAccountlevel2,accountingAccountlevel3, level2Types)
                Level3DropdownMenu(accountingAccountlevel3,level3Types)
            }else{
                val resultTypes = getResultTypes("")
                Level1TypeResultDropdownMenu(accountingAccountlevel1,resultTypes)
            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = accountingAccountlevel4.value,
                onValueChange = {
                    accountingAccountlevel4.value = it
                },
                placeholder = { Text("") },
                label = {
                    Text(
                        text = "Conta Sintética:",
                    )
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = description.value,
                onValueChange = {
                    description.value = it
                },
                placeholder = { Text("") },
                label = {
                    Text(
                        text = "Conta Analítica:",
                    )
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().border(width = 5.dp, Color(0x00C0D1DF))
            ) {
                Button(
                    onClick = {
                        isAdding.value=false
                    },

                    ) {
                    Text(
                        text = "Cancelar",
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
                Button(
                    onClick = {
                        accountingAccountsViewModel.saveAccountingAccount(AccountingAccount(0, accountingAccountType.value, accountingAccountlevel1.value,accountingAccountlevel2.value,accountingAccountlevel3.value,accountingAccountlevel4.value,description.value,0))
                        Toast.makeText(
                            context,
                            "Conta contábil adicionada com sucesso.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        isAdding.value=false
                    },

                    ) {
                    Text(
                        text = "Salvar",
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
private fun AccountingAccountsEditScreenContent(
    accountingAccountsViewModel: AccountingAccountsViewModel,
    modifier: Modifier = Modifier,
    isEditing: MutableState<Boolean>,
    isEditingType: MutableState<String>,
    isEditingLevel1: MutableState<String>,
    isEditingLevel2: MutableState<String>,
    isEditingLevel3: MutableState<String>,
    isEditingLevel4: MutableState<String>,
    isEditingDescription: MutableState<String>,
    isEditingId: MutableState<Long>,
) {

    var type = remember { mutableStateOf(isEditingType.value) }
    var level1 = remember { mutableStateOf(isEditingLevel1.value) }
    var level2 = remember { mutableStateOf(isEditingLevel2.value) }
    var level3 = remember { mutableStateOf(isEditingLevel3.value) }
    var level4 = remember { mutableStateOf(isEditingLevel4.value) }
    var description = remember { mutableStateOf(isEditingDescription.value) }
    val context = LocalContext.current

    val types = getTypes("")
    val level1Types = getLevel1Types("")
    val level2Types = getLevel2Types("",level1.value)
    val level3Types = getLevel3Types("",level1.value,level2.value)

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

            TypeDropdownMenu(type,types)
            if (type.value==ACCOUNTING_ACCOUNTS_TYPE_ASSET){
                Level1DropdownMenu(level1,level2,level3, level1Types)
                Level2DropdownMenu(level2,level3, level2Types)
                Level3DropdownMenu(level3,level3Types)
            }else{
                val resultTypes = getResultTypes("")
                Level1TypeResultDropdownMenu(level1,resultTypes)
            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = level4.value,
                onValueChange = {
                    level4.value = it
                },
                placeholder = { Text("") },
                label = {
                    Text(
                        text = "Conta Sintética:",
                    )
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = description.value,
                onValueChange = {
                    description.value = it
                },
                placeholder = { Text("") },
                label = {
                    Text(
                        text = "Conta Analítica:",
                    )
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().border(width = 5.dp, Color(0x00C0D1DF))
            ) {
                Button(
                    onClick = {
                        isEditing.value=false
                        isEditingType.value=""
                        isEditingLevel1.value=""
                        isEditingLevel2.value=""
                        isEditingLevel3.value=""
                        isEditingLevel4.value=""
                        isEditingDescription.value=""
                        type.value=""
                        level1.value=""
                        level2.value=""
                        level3.value=""
                        level4.value=""
                        description.value=""
                        isEditingId.value=0
                    },

                    ) {
                    Text(
                        text = "Cancelar",
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
                Button(
                    onClick = {
                        accountingAccountsViewModel.saveAccountingAccount(AccountingAccount(isEditingId.value, type.value, level1.value,level2.value,level3.value,level4.value, description.value,0))
                        Toast.makeText(
                            context,
                            "Conta contábil modificada com sucesso.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        isEditing.value=false
                        isEditingType.value=""
                        isEditingLevel1.value=""
                        isEditingLevel2.value=""
                        isEditingLevel3.value=""
                        isEditingLevel4.value=""
                        isEditingDescription.value=""
                        type.value=""
                        level1.value=""
                        level2.value=""
                        level3.value=""
                        level4.value=""
                        description.value=""
                        isEditingId.value=0
                    },

                    ) {
                    Text(
                        text = "Salvar",
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
fun AccountingAccountsDeleteDialog(
    isDeleting: MutableState<Boolean>,
    isDeletingId: MutableState<Long>,
    isDeletingType: MutableState<String>,
    isDeletingLevel1: MutableState<String>,
    isDeletingLevel2: MutableState<String>,
    isDeletingLevel3: MutableState<String>,
    isDeletingLevel4: MutableState<String>,
    isDeletingDescription: MutableState<String>,
    accountingAccountsViewModel: AccountingAccountsViewModel,
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
                    text = "Excluir Conta Contábil:",
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
                            text = isDeletingType.value
                        )
                    }

                    if (isDeletingType.value==ACCOUNTING_ACCOUNTS_TYPE_ASSET){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ){
                            Text(
                                text = isDeletingLevel1.value
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ){
                            Text(
                                text = isDeletingLevel2.value
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ){
                            Text(
                                text = isDeletingLevel3.value
                            )
                        }
                    }


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Text(
                            text = isDeletingLevel4.value
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Text(
                            text = isDeletingDescription.value
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
                            isDeletingType.value = ""
                            isDeletingLevel1.value = ""
                            isDeletingLevel2.value = ""
                            isDeletingLevel3.value = ""
                            isDeletingLevel4.value = ""
                            isDeletingDescription.value = ""
                        }
                    ) {
                        Text(
                            text = "Cancelar",
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    Button(
                        onClick = {
                            accountingAccountsViewModel.deleteAccountingAccount(AccountingAccount(isDeletingId.value, isDeletingType.value, isDeletingLevel1.value,isDeletingLevel2.value,isDeletingLevel3.value,isDeletingLevel4.value,isDeletingDescription.value,0))
                            isDeleting.value = false
                            isDeletingId.value = 0
                            isDeletingType.value = ""
                            isDeletingLevel1.value = ""
                            isDeletingLevel2.value = ""
                            isDeletingLevel3.value = ""
                            isDeletingLevel4.value = ""
                            isDeletingDescription.value = ""
                            Toast.makeText(
                                context,
                                "Conta contábil excluída com sucesso.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        },
                    ) {
                        Text(
                            text = "Excluir",
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
fun TypeDropdownMenu(typeDropdownMenu: MutableState<String>, typeList: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Row() {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                },
            value = typeDropdownMenu.value,
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
                                        typeDropdownMenu.value = item
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = "Tipo:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }

            }, enabled = false
        )
    }
}

@Composable
fun Level1DropdownMenu(level1DropdownMenu: MutableState<String>,level2DropdownMenu: MutableState<String>,level3DropdownMenu: MutableState<String>, level1List: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Row() {
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
                    )
                }

            }, enabled = false
        )
    }
}


@Composable
fun Level1TypeResultDropdownMenu(level1DropdownMenu: MutableState<String>, level1List: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Row() {
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
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = "Subtipo:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }

            }, enabled = false
        )
    }
}

@Composable
fun Level2DropdownMenu(level2DropdownMenu: MutableState<String>,level3DropdownMenu: MutableState<String>, level2List: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Row() {
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
                    )
                }

            }, enabled = false
        )
    }
}



@Composable
fun Level3DropdownMenu(level3DropdownMenu: MutableState<String>, level3List: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Row() {
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
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                    },
                                    onClick = {
                                        level3DropdownMenu.value = item
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = "Subtipo:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }

            }, enabled = false
        )
    }
}

@Composable
fun displayEachAccountingAccountLine(
    isEditing: MutableState<Boolean>,
    isEditingType: MutableState<String>,
    isEditingLevel1: MutableState<String>,
    isEditingLevel2: MutableState<String>,
    isEditingLevel3: MutableState<String>,
    isEditingLevel4: MutableState<String>,
    isEditingDescription: MutableState<String>,
    isEditingId: MutableState<Long>,
    isDeletingId: MutableState<Long>,
    isDeleting: MutableState<Boolean>,
    isDeletingType: MutableState<String>,
    isDeletingLevel1: MutableState<String>,
    isDeletingLevel2: MutableState<String>,
    isDeletingLevel3: MutableState<String>,
    isDeletingLevel4: MutableState<String>,
    isDeletingDescription: MutableState<String>,
    accountingAccount: AccountingAccount){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = false,
                onClick = {
                    isEditing.value=true
                    isEditingType.value=accountingAccount.type
                    isEditingLevel1.value=accountingAccount.level1
                    isEditingLevel2.value=accountingAccount.level2
                    isEditingLevel3.value=accountingAccount.level3
                    isEditingLevel4.value=accountingAccount.level4
                    isEditingDescription.value=accountingAccount.description
                    isEditingId.value=accountingAccount.accountingAccountId
                },
                role = Role.Button
            )
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth(0.65f)
                .padding(horizontal = 10.dp)
        ) {
            if (accountingAccount.type==ACCOUNTING_ACCOUNTS_TYPE_ASSET) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.subdirectory_arrow_right_transp_24px),
                    contentDescription = "",
                    modifier = Modifier
                )
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.subdirectory_arrow_right_transp_24px),
                    contentDescription = "",
                    modifier = Modifier
                )
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.subdirectory_arrow_right_transp_24px),
                    contentDescription = "",
                    modifier = Modifier
                )
            }

            Text(
                text = accountingAccount.level4 + ": " + accountingAccount.description
            )
        }

        TextButton(
            modifier = Modifier.padding(3.dp),
            onClick =
                {
                    isDeleting.value = true
                    isDeletingId.value = accountingAccount.accountingAccountId
                    isDeletingType.value = accountingAccount.type
                    isDeletingLevel1.value = accountingAccount.level1
                    isDeletingLevel2.value = accountingAccount.level2
                    isDeletingLevel3.value = accountingAccount.level3
                    isDeletingLevel4.value = accountingAccount.level4
                    isDeletingDescription.value = accountingAccount.description
                }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.delete_24px),
                contentDescription = "Excluir",
                modifier = Modifier
            )
        }
    }
}

@Composable
fun displayEachExpandableTitleRow(expandCollapseFlag: MutableState<Boolean>,title: String, level: Int){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Left,
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = false,
                onClick = {
                    switchState(expandCollapseFlag)
                },
                role = Role.Button
            )
    ) {

        TextButton(
            modifier = Modifier.padding(3.dp),
            onClick =
                {
                    switchState(expandCollapseFlag)
                }
        ) {
            showExpandCollapseButton(expandCollapseFlag.value,level)
        }

        Text(
            text = title
        )
    }
}

@Composable
fun displayLevel3Group(level2GroupList: List<AccountingAccount>, level3GroupName: String,
                       isEditing: MutableState<Boolean>,
                       isEditingType: MutableState<String>,
                       isEditingLevel1: MutableState<String>,
                       isEditingLevel2: MutableState<String>,
                       isEditingLevel3: MutableState<String>,
                       isEditingLevel4: MutableState<String>,
                       isEditingDescription: MutableState<String>,
                       isEditingId: MutableState<Long>,
                       isDeletingId: MutableState<Long>,
                       isDeleting: MutableState<Boolean>,
                       isDeletingType: MutableState<String>,
                       isDeletingLevel1: MutableState<String>,
                       isDeletingLevel2: MutableState<String>,
                       isDeletingLevel3: MutableState<String>,
                       isDeletingLevel4: MutableState<String>,
                       isDeletingDescription: MutableState<String>,){

    val level3List = level2GroupList.filter { it.level3== level3GroupName }
    if (level3List.isNotEmpty()) {
        val level3AtivoCirculanteContasAReceberExpanded = remember { mutableStateOf(false) }
        displayEachExpandableTitleRow(
            level3AtivoCirculanteContasAReceberExpanded,
            level3GroupName,3
        )
        if (level3AtivoCirculanteContasAReceberExpanded.value){
            level3List.forEach { accountingAccount ->
                displayEachAccountingAccountLine(
                    isEditing,
                    isEditingType,
                    isEditingLevel1,
                    isEditingLevel2,
                    isEditingLevel3,
                    isEditingLevel4,
                    isEditingDescription,
                    isEditingId,
                    isDeletingId,
                    isDeleting,
                    isDeletingType,
                    isDeletingLevel1,
                    isDeletingLevel2,
                    isDeletingLevel3,
                    isDeletingLevel4,
                    isDeletingDescription,
                    accountingAccount)
            }
        }
    }
}