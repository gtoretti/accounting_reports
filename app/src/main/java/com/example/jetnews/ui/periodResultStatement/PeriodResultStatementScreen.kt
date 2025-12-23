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

package com.example.jetnews.ui.periodResultStatement

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
import com.example.jetnews.data.periodResultStatement.PeriodResultStatement
import com.example.jetnews.data.periodResultStatement.PeriodResultStatementItem
import com.example.jetnews.ui.accountingAccounts.displayEachExpandableTitleRow
import com.example.jetnews.ui.utils.DatePickerModal
import com.example.jetnews.ui.utils.screenToDouble
import com.example.jetnews.ui.utils.toScreen
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_FINANCING
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_FINANCING_INTEREST_EXPENSES_ON_LOANS_AND_LEASE_LIABILITIES
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_FINANCING_INTEREST_EXPENSES_ON_RETIREMENT_LIABILITIES_AND_PROVISIONS
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_INVESTING
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_INVESTING_PROFIT_SHARING_AND_GAINS_FROM_SALE_OF_INTERESTS_IN_ASSOCIATES_AND_JOINT_VENTURES
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_OPERATING
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_OPERATING_COST_OF_SALES
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_OPERATING_GENERAL_AND_ADMINISTRATIVE_EXPENSES
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_OPERATING_GOODWILL_IMPAIRMENT_LOSS
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_OPERATING_OTHER_OPERATING_EXPENSES
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_OPERATING_OTHER_OPERATING_REVENUES
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_OPERATING_RESEARCH_AND_DEVELOPMENT_EXPENSES
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_OPERATING_REVENUE
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_OPERATING_SELLING_EXPENSES
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_OPERATION_DISCONTINUED
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_OPERATION_DISCONTINUED_LOSS_FROM_DISCONTINUED_OPERATIONS
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_TAX_ON_PROFIT
import com.example.jetnews.utils.PERIOD_RESULT_STATEMENT_TYPE_TAX_ON_PROFIT_TAX_EXPENSES_ON_PROFIT
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
fun PeriodResultStatementScreen(
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isPeriodResultStatementAddScreen = remember { mutableStateOf(false) }
    val isPeriodResultStatementEditScreen = remember { mutableStateOf(false) }
    val isPeriodResultStatementItemsAddItemScreen = remember { mutableStateOf(false) }
    val isPeriodResultStatementItemsEditItemScreen = remember { mutableStateOf(false) }
    val isPeriodResultStatementItemsScreen = remember { mutableStateOf(false) }

    val periodResultStatementName = remember { mutableStateOf("") }
    val periodResultStatementId = remember { mutableLongStateOf(0) }
    val periodResultStatementStartDate = remember { mutableStateOf("") }
    val periodResultStatementEndDate = remember { mutableStateOf("") }

    val periodResultStatementDescriptionList = remember { mutableListOf<String>() }

    val periodResultStatementItemId = remember { mutableLongStateOf(0) }
    val periodResultStatementItemType = remember { mutableStateOf("") }
    val periodResultStatementItemDescription = remember { mutableStateOf("") }
    val periodResultStatementItemValue = remember { mutableDoubleStateOf(0.0) }


    when {
        isPeriodResultStatementAddScreen.value -> {
            PeriodResultStatementAddScreen(
                periodResultStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isPeriodResultStatementAddScreen
            )
        }
        isPeriodResultStatementEditScreen.value -> {
            PeriodResultStatementEditScreen(
                periodResultStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isPeriodResultStatementEditScreen,
                periodResultStatementName,
                periodResultStatementId,
                periodResultStatementStartDate,
                periodResultStatementEndDate
            )
        }
        isPeriodResultStatementItemsEditItemScreen.value -> {
            PeriodResultStatementItemsEditItemScreen(
                periodResultStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                periodResultStatementName,
                periodResultStatementId,
                periodResultStatementStartDate,
                periodResultStatementEndDate,
                isPeriodResultStatementItemsEditItemScreen,
                periodResultStatementItemId,
                periodResultStatementItemType,
                periodResultStatementItemDescription,
                periodResultStatementItemValue,
                periodResultStatementDescriptionList
            )
        }
        isPeriodResultStatementItemsAddItemScreen.value -> {
            PeriodResultStatementItemsAddItemScreen(
                periodResultStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                periodResultStatementName,
                periodResultStatementId,
                periodResultStatementStartDate,
                periodResultStatementEndDate,
                isPeriodResultStatementItemsAddItemScreen,
                periodResultStatementDescriptionList
            )
        }
        isPeriodResultStatementItemsScreen.value -> {
            PeriodResultStatementItemsScreen(
                periodResultStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isPeriodResultStatementItemsScreen,
                periodResultStatementName,
                periodResultStatementId,
                periodResultStatementStartDate,
                periodResultStatementEndDate,
                isPeriodResultStatementItemsAddItemScreen,
                isPeriodResultStatementItemsEditItemScreen,
                periodResultStatementItemId,
                periodResultStatementItemType,
                periodResultStatementItemDescription,
                periodResultStatementItemValue,
                periodResultStatementDescriptionList,
            )
        }
        else -> {
            PeriodResultStatementHomeScreen(
                periodResultStatementViewModel,
                isExpandedScreen,
                openDrawer,
                snackbarHostState,
                isPeriodResultStatementAddScreen,
                isPeriodResultStatementEditScreen,
                periodResultStatementName,
                periodResultStatementId,
                periodResultStatementStartDate,
                periodResultStatementEndDate,
                isPeriodResultStatementItemsScreen,
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
fun PeriodResultStatementHomeScreen(
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isPeriodResultStatementAddScreen: MutableState<Boolean>,
    isPeriodResultStatementEditScreen: MutableState<Boolean>,
    periodResultStatementName: MutableState<String>,
    periodResultStatementId: MutableState<Long>,
    periodResultStatementStartDate: MutableState<String>,
    periodResultStatementEndDate: MutableState<String>,
    isPeriodResultStatementItemsScreen: MutableState<Boolean>,
) {


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.period_results_statement_title),
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
                            isPeriodResultStatementAddScreen.value=true
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = stringResource(R.string.period_results_statement_add_statement),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        PeriodResultStatementHomeScreenContent(
            periodResultStatementViewModel,
            screenModifier,
            isPeriodResultStatementEditScreen,
            periodResultStatementName,
            periodResultStatementId,
            periodResultStatementStartDate,
            periodResultStatementEndDate,
            isPeriodResultStatementItemsScreen,
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
fun PeriodResultStatementAddScreen(
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isPeriodResultStatementAddScreen: MutableState<Boolean>
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.period_results_statement_add_statement),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isPeriodResultStatementAddScreen.value=false
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
        PeriodResultStatementAddScreenContent(
            periodResultStatementViewModel,
            screenModifier,
            isPeriodResultStatementAddScreen
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
fun PeriodResultStatementEditScreen(
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isPeriodResultStatementEditScreen: MutableState<Boolean>,
    periodResultStatementName: MutableState<String>,
    periodResultStatementId: MutableState<Long>,
    periodResultStatementStartDate: MutableState<String>,
    periodResultStatementEndDate: MutableState<String>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.period_results_statement_edit_statement),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isPeriodResultStatementEditScreen.value=false
                        periodResultStatementName.value=""
                        periodResultStatementId.value=0
                        periodResultStatementStartDate.value=""
                        periodResultStatementEndDate.value=""
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
        PeriodResultStatementEditScreenContent(
            periodResultStatementViewModel,
            screenModifier,
            isPeriodResultStatementEditScreen,
            periodResultStatementName,
            periodResultStatementId,
            periodResultStatementStartDate,
            periodResultStatementEndDate,
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
fun PeriodResultStatementItemsScreen(
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isPeriodResultStatementItemsScreen: MutableState<Boolean>,
    periodResultStatementName: MutableState<String>,
    periodResultStatementId: MutableState<Long>,
    periodResultStatementStartDate: MutableState<String>,
    periodResultStatementEndDate: MutableState<String>,
    isPeriodResultStatementItemsAddItemScreen: MutableState<Boolean>,
    isPeriodResultStatementItemsEditItemScreen: MutableState<Boolean>,
    periodResultStatementItemId: MutableState<Long>,
    periodResultStatementItemType: MutableState<String>,
    periodResultStatementItemDescription: MutableState<String>,
    periodResultStatementItemValue: MutableState<Double>,
    periodResultStatementDescriptionList: MutableList<String>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.period_results_statement_items),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isPeriodResultStatementItemsScreen.value=false
                        periodResultStatementName.value=""
                        periodResultStatementId.value=0
                        periodResultStatementStartDate.value=""
                        periodResultStatementEndDate.value=""
                        periodResultStatementDescriptionList.removeAll(periodResultStatementDescriptionList)
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
        PeriodResultStatementItemsScreenContent(
            periodResultStatementViewModel,
            screenModifier,
            periodResultStatementName,
            periodResultStatementId,
            periodResultStatementStartDate,
            periodResultStatementEndDate,
            isPeriodResultStatementItemsAddItemScreen,
            isPeriodResultStatementItemsEditItemScreen,
            periodResultStatementItemId,
            periodResultStatementItemType,
            periodResultStatementItemDescription,
            periodResultStatementItemValue,
            periodResultStatementDescriptionList
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
fun PeriodResultStatementItemsAddItemScreen(
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    periodResultStatementName: MutableState<String>,
    periodResultStatementId: MutableState<Long>,
    periodResultStatementStartDate: MutableState<String>,
    periodResultStatementEndDate: MutableState<String>,
    isPeriodResultStatementItemsAddItemScreen: MutableState<Boolean>,
    periodResultStatementDescriptionList: MutableList<String>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.period_results_statement_items_add_item),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isPeriodResultStatementItemsAddItemScreen.value=false
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
        PeriodResultStatementItemsAddItemScreenContent(
            periodResultStatementViewModel,
            screenModifier,
            periodResultStatementName,
            periodResultStatementId,
            periodResultStatementStartDate,
            periodResultStatementEndDate,
            isPeriodResultStatementItemsAddItemScreen,
            periodResultStatementDescriptionList
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
fun PeriodResultStatementItemsEditItemScreen(
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    periodResultStatementName: MutableState<String>,
    periodResultStatementId: MutableState<Long>,
    periodResultStatementStartDate: MutableState<String>,
    periodResultStatementEndDate: MutableState<String>,
    isPeriodResultStatementItemsEditItemScreen: MutableState<Boolean>,
    periodResultStatementItemId: MutableState<Long>,
    periodResultStatementItemType: MutableState<String>,
    periodResultStatementItemDescription: MutableState<String>,
    periodResultStatementItemValue: MutableState<Double>,
    periodResultStatementDescriptionList: MutableList<String>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.period_results_statement_items_edit),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isPeriodResultStatementItemsEditItemScreen.value=false
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
        PeriodResultStatementItemsEditItemScreenContent(
            periodResultStatementViewModel,
            screenModifier,
            periodResultStatementName,
            periodResultStatementId,
            periodResultStatementStartDate,
            periodResultStatementEndDate,
            isPeriodResultStatementItemsEditItemScreen,
            periodResultStatementItemId,
            periodResultStatementItemType,
            periodResultStatementItemDescription,
            periodResultStatementItemValue,
            periodResultStatementDescriptionList,
        )
    }
}



/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun PeriodResultStatementEditScreenContent(
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    modifier: Modifier = Modifier,
    isPeriodResultStatementEditScreen: MutableState<Boolean>,
    periodResultStatementName: MutableState<String>,
    periodResultStatementId: MutableState<Long>,
    periodResultStatementStartDate: MutableState<String>,
    periodResultStatementEndDate: MutableState<String>,
) {

    val name = remember { mutableStateOf(periodResultStatementName.value) }
    val openStartDateEditDialog= remember { mutableStateOf(false) }
    val startDate = remember { mutableStateOf(periodResultStatementStartDate.value) }
    val openEndDateEditDialog= remember { mutableStateOf(false) }
    val endDate = remember { mutableStateOf(periodResultStatementEndDate.value) }
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
                        text = stringResource(R.string.period_results_statement_name),
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
                            text = stringResource(R.string.period_results_statement_startDate),
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
                        contentDescription = stringResource(R.string.period_results_statement_startDate),
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
                            text = stringResource(R.string.period_results_statement_endDate),
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
                        contentDescription = stringResource(R.string.period_results_statement_endDate),
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
                        isPeriodResultStatementEditScreen.value=false
                        periodResultStatementName.value=""
                        name.value=""
                        periodResultStatementId.value=0
                        startDate.value = ""
                        endDate.value = ""
                        openStartDateEditDialog.value=false
                        openEndDateEditDialog.value=false
                    },

                    ) {
                    Text(
                        text = stringResource(R.string.period_results_statement_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

                val changedSuccess = stringResource(R.string.period_results_statement_changed_success)
                val missingNameMsg = stringResource(R.string.period_results_statement_missing_name)
                val missingStartDateMsg = stringResource(R.string.period_results_statement_missing_startdate)
                val missingEndDateMsg = stringResource(R.string.period_results_statement_missing_enddate)

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

                                        periodResultStatementViewModel.savePeriodResultStatement(
                                            context,
                                            PeriodResultStatement(
                                                periodResultStatementId.value,
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
                                        isPeriodResultStatementEditScreen.value = false
                                        periodResultStatementName.value = ""
                                        name.value = ""
                                        periodResultStatementId.value = 0
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
                        text = stringResource(R.string.period_results_statement_save),
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
                openDialog = openStartDateEditDialog, title = stringResource(R.string.period_results_statement_startDate)
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
                openDialog = openEndDateEditDialog, title = stringResource(R.string.period_results_statement_endDate)
            )
        }
    }
}


/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun PeriodResultStatementItemsScreenContent(
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    modifier: Modifier = Modifier,
    periodResultStatementName: MutableState<String>,
    periodResultStatementId: MutableState<Long>,
    periodResultStatementStartDate: MutableState<String>,
    periodResultStatementEndDate: MutableState<String>,
    isPeriodResultStatementItemsAddItemScreen: MutableState<Boolean>,
    isPeriodResultStatementItemsEditItemScreen: MutableState<Boolean>,
    periodResultStatementItemId: MutableState<Long>,
    periodResultStatementItemType: MutableState<String>,
    periodResultStatementItemDescription: MutableState<String>,
    periodResultStatementItemValue: MutableState<Double>,
    periodResultStatementDescriptionList: MutableList<String>,
) {

    val context = LocalContext.current
    val isItemsPageDeleteItem = remember { mutableStateOf(false) }
    val periodResultItemsFlow = periodResultStatementViewModel.getPeriodResultStatementItems(periodResultStatementId.value)
    val periodResultItems by periodResultItemsFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    periodResultStatementDescriptionList.removeAll(periodResultStatementDescriptionList)

    var grossProfit = 0.0
    var operatingProfit = 0.0
    var profitBeforeFinancingAndTaxes = 0.0
    var profitBeforeTaxes = 0.0
    var profitFromContinuingOperations = 0.0
    var netProfit = 0.0

    val operatingItems = periodResultItems.filter { it.type == PERIOD_RESULT_STATEMENT_TYPE_OPERATING }
    val revenueList = operatingItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_OPERATING_REVENUE }
    if (revenueList.isNotEmpty()){
        grossProfit += revenueList[0].value
        periodResultStatementDescriptionList.add(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_REVENUE)
    }

    val costOfSalesList = operatingItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_OPERATING_COST_OF_SALES }
    if (costOfSalesList.isNotEmpty()){
        grossProfit += costOfSalesList[0].value
        periodResultStatementDescriptionList.add(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_COST_OF_SALES)
    }

    var grossProfitText = grossProfit.toScreen()
    if (grossProfit<0.0)
        grossProfitText = "(" + grossProfit.absoluteValue.toScreen() + ")"

    operatingProfit = grossProfit

    val otherOperatingRevenuesList = operatingItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_OPERATING_OTHER_OPERATING_REVENUES }
    if (otherOperatingRevenuesList.isNotEmpty()){
        operatingProfit += otherOperatingRevenuesList[0].value
        periodResultStatementDescriptionList.add(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_OTHER_OPERATING_REVENUES)
    }

    val sellingExpensesList = operatingItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_OPERATING_SELLING_EXPENSES }
    if (sellingExpensesList.isNotEmpty()){
        operatingProfit += sellingExpensesList[0].value
        periodResultStatementDescriptionList.add(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_SELLING_EXPENSES)
    }

    val researchAndDevelopmentExpensesList = operatingItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_OPERATING_RESEARCH_AND_DEVELOPMENT_EXPENSES }
    if (researchAndDevelopmentExpensesList.isNotEmpty()){
        operatingProfit += researchAndDevelopmentExpensesList[0].value
        periodResultStatementDescriptionList.add(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_RESEARCH_AND_DEVELOPMENT_EXPENSES)
    }

    val generalAndAdministrativeExpensesList = operatingItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_OPERATING_GENERAL_AND_ADMINISTRATIVE_EXPENSES }
    if (generalAndAdministrativeExpensesList.isNotEmpty()){
        operatingProfit += generalAndAdministrativeExpensesList[0].value
        periodResultStatementDescriptionList.add(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_GENERAL_AND_ADMINISTRATIVE_EXPENSES)
    }

    val goodwillImpairmentLossExpensesList = operatingItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_OPERATING_GOODWILL_IMPAIRMENT_LOSS }
    if (goodwillImpairmentLossExpensesList.isNotEmpty()){
        operatingProfit += goodwillImpairmentLossExpensesList[0].value
        periodResultStatementDescriptionList.add(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_GOODWILL_IMPAIRMENT_LOSS)
    }

    val otherOperatingExpensesList = operatingItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_OPERATING_OTHER_OPERATING_EXPENSES }
    if (otherOperatingExpensesList.isNotEmpty()){
        operatingProfit += otherOperatingExpensesList[0].value
        periodResultStatementDescriptionList.add(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_OTHER_OPERATING_EXPENSES)
    }

    var operatingProfitText = operatingProfit.toScreen()
    if (operatingProfit<0.0)
        operatingProfitText = "(" + operatingProfit.absoluteValue.toScreen() + ")"

    profitBeforeFinancingAndTaxes = operatingProfit

    val investingItems = periodResultItems.filter { it.type == PERIOD_RESULT_STATEMENT_TYPE_INVESTING }

    val investingProfitSharingList = investingItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_INVESTING_PROFIT_SHARING_AND_GAINS_FROM_SALE_OF_INTERESTS_IN_ASSOCIATES_AND_JOINT_VENTURES }
    if (investingProfitSharingList.isNotEmpty()){
        profitBeforeFinancingAndTaxes += investingProfitSharingList[0].value
        periodResultStatementDescriptionList.add(PERIOD_RESULT_STATEMENT_TYPE_INVESTING_PROFIT_SHARING_AND_GAINS_FROM_SALE_OF_INTERESTS_IN_ASSOCIATES_AND_JOINT_VENTURES)
    }

    var profitBeforeFinancingAndTaxesText = profitBeforeFinancingAndTaxes.toScreen()
    if (profitBeforeFinancingAndTaxes<0.0)
        profitBeforeFinancingAndTaxesText = "(" + profitBeforeFinancingAndTaxes.absoluteValue.toScreen() + ")"

    profitBeforeTaxes = profitBeforeFinancingAndTaxes

    val financingItems = periodResultItems.filter { it.type == PERIOD_RESULT_STATEMENT_TYPE_FINANCING }

    val interestExpensesOnLoansAndLeaseLiabilitiesList = financingItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_FINANCING_INTEREST_EXPENSES_ON_LOANS_AND_LEASE_LIABILITIES }
    if (interestExpensesOnLoansAndLeaseLiabilitiesList.isNotEmpty()) {
        profitBeforeTaxes += interestExpensesOnLoansAndLeaseLiabilitiesList[0].value
        periodResultStatementDescriptionList.add(PERIOD_RESULT_STATEMENT_TYPE_FINANCING_INTEREST_EXPENSES_ON_LOANS_AND_LEASE_LIABILITIES)
    }

    val retirementExpensesList = financingItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_FINANCING_INTEREST_EXPENSES_ON_RETIREMENT_LIABILITIES_AND_PROVISIONS }
    if (retirementExpensesList.isNotEmpty()) {
        profitBeforeTaxes += retirementExpensesList[0].value
        periodResultStatementDescriptionList.add(PERIOD_RESULT_STATEMENT_TYPE_FINANCING_INTEREST_EXPENSES_ON_RETIREMENT_LIABILITIES_AND_PROVISIONS)
    }

    var profitBeforeTaxesText = profitBeforeTaxes.toScreen()
    if (profitBeforeTaxes<0.0)
        profitBeforeTaxesText = "(" + profitBeforeTaxes.absoluteValue.toScreen() + ")"

    profitFromContinuingOperations = profitBeforeTaxes

    val taxOnProfitItems = periodResultItems.filter { it.type == PERIOD_RESULT_STATEMENT_TYPE_TAX_ON_PROFIT }

    val taxExpensesOnProfitList = taxOnProfitItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_TAX_ON_PROFIT_TAX_EXPENSES_ON_PROFIT }
    if (taxExpensesOnProfitList.isNotEmpty()) {
        profitFromContinuingOperations += taxExpensesOnProfitList[0].value
        periodResultStatementDescriptionList.add(PERIOD_RESULT_STATEMENT_TYPE_TAX_ON_PROFIT_TAX_EXPENSES_ON_PROFIT)
    }

    var profitFromContinuingOperationsText = profitFromContinuingOperations.toScreen()
    if (profitFromContinuingOperations<0.0)
        profitFromContinuingOperationsText = "(" + profitFromContinuingOperations.absoluteValue.toScreen() + ")"

    netProfit = profitFromContinuingOperations

    val operationDiscontinuedItems = periodResultItems.filter { it.type == PERIOD_RESULT_STATEMENT_TYPE_OPERATION_DISCONTINUED }

    val lossFromDiscontinuedOperationsList = operationDiscontinuedItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_OPERATION_DISCONTINUED_LOSS_FROM_DISCONTINUED_OPERATIONS }
    if (lossFromDiscontinuedOperationsList.isNotEmpty()) {
        netProfit += lossFromDiscontinuedOperationsList[0].value
        periodResultStatementDescriptionList.add(PERIOD_RESULT_STATEMENT_TYPE_OPERATION_DISCONTINUED_LOSS_FROM_DISCONTINUED_OPERATIONS)
    }

    var netProfitText = netProfit.toScreen()
    if (netProfit<0.0)
        netProfitText = "(" + netProfit.absoluteValue.toScreen() + ")"


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
                    text = periodResultStatementName.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = periodResultStatementStartDate.value + " - " + periodResultStatementEndDate.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                )
                Spacer(Modifier.height(10.dp))

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
                            modifier = Modifier
                        )
                    }

                    val allDescriptionsAlreadyAdded = stringResource(R.string.period_results_statement_all_items_already_added)
                    TextButton(
                        modifier = Modifier.padding(1.dp),
                        onClick =
                            {
                                val resultTypes = getMissingCategoryTypesForDescriptionList("",periodResultStatementDescriptionList)
                                if (resultTypes.isNotEmpty()){
                                    isPeriodResultStatementItemsAddItemScreen.value = true
                                }else{
                                    Toast.makeText(
                                        context,
                                        allDescriptionsAlreadyAdded,
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
                val operatingExpanded = remember { mutableStateOf(false) }
                displayEachExpandableTitleRow(operatingExpanded, PERIOD_RESULT_STATEMENT_TYPE_OPERATING,0)

                if (operatingExpanded.value){
                    if (revenueList.isNotEmpty()){
                        PeriodResultStatementItemRow(revenueList[0],
                            isItemsPageDeleteItem,
                            isPeriodResultStatementItemsEditItemScreen,
                            periodResultStatementItemId,
                            periodResultStatementItemType,
                            periodResultStatementItemDescription,
                            periodResultStatementItemValue)
                    }

                    if (costOfSalesList.isNotEmpty()){
                        PeriodResultStatementItemRow(costOfSalesList[0],
                            isItemsPageDeleteItem,
                            isPeriodResultStatementItemsEditItemScreen,
                            periodResultStatementItemId,
                            periodResultStatementItemType,
                            periodResultStatementItemDescription,
                            periodResultStatementItemValue)
                    }

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    )
                    Spacer(Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(0.82f)
                    ){
                        Text(
                            text = stringResource(R.string.period_results_statement_gross_profit),
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = grossProfitText,
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    val otherOperatingRevenuesList = operatingItems.filter { it.description == PERIOD_RESULT_STATEMENT_TYPE_OPERATING_OTHER_OPERATING_REVENUES }
                    if (otherOperatingRevenuesList.isNotEmpty()){
                        PeriodResultStatementItemRow(otherOperatingRevenuesList[0],
                            isItemsPageDeleteItem,
                            isPeriodResultStatementItemsEditItemScreen,
                            periodResultStatementItemId,
                            periodResultStatementItemType,
                            periodResultStatementItemDescription,
                            periodResultStatementItemValue)
                    }

                    if (sellingExpensesList.isNotEmpty()){
                        PeriodResultStatementItemRow(sellingExpensesList[0],
                            isItemsPageDeleteItem,
                            isPeriodResultStatementItemsEditItemScreen,
                            periodResultStatementItemId,
                            periodResultStatementItemType,
                            periodResultStatementItemDescription,
                            periodResultStatementItemValue)
                    }

                    if (researchAndDevelopmentExpensesList.isNotEmpty()){
                        PeriodResultStatementItemRow(researchAndDevelopmentExpensesList[0],
                            isItemsPageDeleteItem,
                            isPeriodResultStatementItemsEditItemScreen,
                            periodResultStatementItemId,
                            periodResultStatementItemType,
                            periodResultStatementItemDescription,
                            periodResultStatementItemValue)
                    }

                    if (generalAndAdministrativeExpensesList.isNotEmpty()){
                        PeriodResultStatementItemRow(generalAndAdministrativeExpensesList[0],
                            isItemsPageDeleteItem,
                            isPeriodResultStatementItemsEditItemScreen,
                            periodResultStatementItemId,
                            periodResultStatementItemType,
                            periodResultStatementItemDescription,
                            periodResultStatementItemValue)
                    }

                    if (goodwillImpairmentLossExpensesList.isNotEmpty()){
                        PeriodResultStatementItemRow(goodwillImpairmentLossExpensesList[0],
                            isItemsPageDeleteItem,
                            isPeriodResultStatementItemsEditItemScreen,
                            periodResultStatementItemId,
                            periodResultStatementItemType,
                            periodResultStatementItemDescription,
                            periodResultStatementItemValue)
                    }

                    if (otherOperatingExpensesList.isNotEmpty()){
                        PeriodResultStatementItemRow(otherOperatingExpensesList[0],
                            isItemsPageDeleteItem,
                            isPeriodResultStatementItemsEditItemScreen,
                            periodResultStatementItemId,
                            periodResultStatementItemType,
                            periodResultStatementItemDescription,
                            periodResultStatementItemValue)
                    }

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    )
                    Spacer(Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(0.82f)
                    ){
                        Text(
                            text = stringResource(R.string.period_results_statement_operating_profit),
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = operatingProfitText,
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                val investingExpanded = remember { mutableStateOf(false) }
                displayEachExpandableTitleRow(investingExpanded, PERIOD_RESULT_STATEMENT_TYPE_INVESTING,0)
                if (investingExpanded.value){

                    if (investingProfitSharingList.isNotEmpty()){
                        PeriodResultStatementItemRow(investingProfitSharingList[0],
                            isItemsPageDeleteItem,
                            isPeriodResultStatementItemsEditItemScreen,
                            periodResultStatementItemId,
                            periodResultStatementItemType,
                            periodResultStatementItemDescription,
                            periodResultStatementItemValue)
                    }

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    )
                    Spacer(Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(0.82f)
                    ){
                        Text(
                            text = stringResource(R.string.period_results_statement_profit_before_financing_and_taxes),
                            modifier = Modifier
                                .fillMaxWidth(0.5f),
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = profitBeforeFinancingAndTaxesText,
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                val financingExpanded = remember { mutableStateOf(false) }
                displayEachExpandableTitleRow(financingExpanded, PERIOD_RESULT_STATEMENT_TYPE_FINANCING,0)
                if (financingExpanded.value){

                    if (interestExpensesOnLoansAndLeaseLiabilitiesList.isNotEmpty()) {
                        PeriodResultStatementItemRow(interestExpensesOnLoansAndLeaseLiabilitiesList[0],
                            isItemsPageDeleteItem,
                            isPeriodResultStatementItemsEditItemScreen,
                            periodResultStatementItemId,
                            periodResultStatementItemType,
                            periodResultStatementItemDescription,
                            periodResultStatementItemValue,
                        )
                    }

                    if (retirementExpensesList.isNotEmpty()) {
                        PeriodResultStatementItemRow(retirementExpensesList[0],
                            isItemsPageDeleteItem,
                            isPeriodResultStatementItemsEditItemScreen,
                            periodResultStatementItemId,
                            periodResultStatementItemType,
                            periodResultStatementItemDescription,
                            periodResultStatementItemValue,
                        )
                    }

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    )
                    Spacer(Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(0.82f)
                    ){
                        Text(
                            text = stringResource(R.string.period_results_statement_profit_before_taxes),
                            modifier = Modifier
                                .fillMaxWidth(0.5f),
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = profitBeforeTaxesText,
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                val taxOnProfitExpanded = remember { mutableStateOf(false) }
                displayEachExpandableTitleRow(taxOnProfitExpanded, PERIOD_RESULT_STATEMENT_TYPE_TAX_ON_PROFIT,0)
                if (taxOnProfitExpanded.value){
                    if (taxExpensesOnProfitList.isNotEmpty()) {
                        PeriodResultStatementItemRow(taxExpensesOnProfitList[0],
                            isItemsPageDeleteItem,
                            isPeriodResultStatementItemsEditItemScreen,
                            periodResultStatementItemId,
                            periodResultStatementItemType,
                            periodResultStatementItemDescription,
                            periodResultStatementItemValue,
                        )
                    }

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    )
                    Spacer(Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(0.82f)
                    ){
                        Text(
                            text = stringResource(R.string.period_results_statement_profit_from_continuing_operations),
                            modifier = Modifier
                                .fillMaxWidth(0.5f),
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = profitFromContinuingOperationsText,
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }


                val operationDiscontinuedExpanded = remember { mutableStateOf(false) }
                displayEachExpandableTitleRow(operationDiscontinuedExpanded, PERIOD_RESULT_STATEMENT_TYPE_OPERATION_DISCONTINUED,0)
                if (operationDiscontinuedExpanded.value){

                    if (lossFromDiscontinuedOperationsList.isNotEmpty()) {
                        PeriodResultStatementItemRow(lossFromDiscontinuedOperationsList[0],
                            isItemsPageDeleteItem,
                            isPeriodResultStatementItemsEditItemScreen,
                            periodResultStatementItemId,
                            periodResultStatementItemType,
                            periodResultStatementItemDescription,
                            periodResultStatementItemValue,
                        )
                    }
                }

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                )
                Spacer(Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(0.82f)
                ){
                    Text(
                        text = stringResource(R.string.period_results_statement_net_profit),
                        modifier = Modifier
                            .fillMaxWidth(0.5f),
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = netProfitText,
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }

    when {

        isItemsPageDeleteItem.value -> {
            PeriodResultStatementItemDeleteDialog(isItemsPageDeleteItem,
                periodResultStatementItemId,
                periodResultStatementItemType,
                periodResultStatementItemDescription,
                periodResultStatementItemValue,
                periodResultStatementViewModel ,
                context)
        }
    }
}


@Composable
private fun PeriodResultStatementItemRow(
    item: PeriodResultStatementItem,
    isPeriodResultStatementItemsDeleteItemDialog: MutableState<Boolean>,
    isPeriodResultStatementItemsEditItemScreen: MutableState<Boolean>,
    periodResultStatementItemId: MutableState<Long>,
    periodResultStatementItemType: MutableState<String>,
    periodResultStatementItemDescription: MutableState<String>,
    periodResultStatementItemValue: MutableState<Double>,
){
    var value = item.value.toScreen()
    if (item.value<0.0)
        value = "("+item.value.absoluteValue.toScreen()+")"

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
                        isPeriodResultStatementItemsEditItemScreen.value=true
                        periodResultStatementItemId.value=item.periodResultStatementItemId
                        periodResultStatementItemType.value=item.type
                        periodResultStatementItemDescription.value=item.description
                        periodResultStatementItemValue.value=item.value.absoluteValue
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
                        isPeriodResultStatementItemsDeleteItemDialog.value = true
                        periodResultStatementItemId.value = item.periodResultStatementItemId
                        periodResultStatementItemType.value = item.type
                        periodResultStatementItemDescription.value = item.description
                        periodResultStatementItemValue.value = item.value.absoluteValue
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

fun getMissingCategoryTypesForDescriptionList(pre:String, descriptionList: List<String>): List<String>{
    var types = ArrayList(listOf(PERIOD_RESULT_STATEMENT_TYPE_OPERATING, PERIOD_RESULT_STATEMENT_TYPE_INVESTING,
        PERIOD_RESULT_STATEMENT_TYPE_FINANCING,PERIOD_RESULT_STATEMENT_TYPE_TAX_ON_PROFIT,PERIOD_RESULT_STATEMENT_TYPE_OPERATION_DISCONTINUED
    ))
    if (pre.isNotEmpty()){
        types = ArrayList(listOf(pre, PERIOD_RESULT_STATEMENT_TYPE_OPERATING, PERIOD_RESULT_STATEMENT_TYPE_INVESTING,
            PERIOD_RESULT_STATEMENT_TYPE_FINANCING,PERIOD_RESULT_STATEMENT_TYPE_TAX_ON_PROFIT,PERIOD_RESULT_STATEMENT_TYPE_OPERATION_DISCONTINUED))
    }

    if (descriptionList.isNotEmpty() && descriptionList.contains(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_REVENUE) && descriptionList.contains(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_COST_OF_SALES) && descriptionList.contains(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_OTHER_OPERATING_REVENUES) && descriptionList.contains(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_SELLING_EXPENSES) && descriptionList.contains(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_RESEARCH_AND_DEVELOPMENT_EXPENSES) && descriptionList.contains(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_GENERAL_AND_ADMINISTRATIVE_EXPENSES) && descriptionList.contains(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_GOODWILL_IMPAIRMENT_LOSS) && descriptionList.contains(PERIOD_RESULT_STATEMENT_TYPE_OPERATING_OTHER_OPERATING_EXPENSES)){
        //contains all operating options already
        types.remove(PERIOD_RESULT_STATEMENT_TYPE_OPERATING)
    }

    if (descriptionList.isNotEmpty() && descriptionList.contains(PERIOD_RESULT_STATEMENT_TYPE_INVESTING_PROFIT_SHARING_AND_GAINS_FROM_SALE_OF_INTERESTS_IN_ASSOCIATES_AND_JOINT_VENTURES)){
        //contains all investing options already
        types.remove(PERIOD_RESULT_STATEMENT_TYPE_INVESTING)
    }

    if (descriptionList.isNotEmpty() && descriptionList.contains(PERIOD_RESULT_STATEMENT_TYPE_FINANCING_INTEREST_EXPENSES_ON_LOANS_AND_LEASE_LIABILITIES) && descriptionList.contains(PERIOD_RESULT_STATEMENT_TYPE_FINANCING_INTEREST_EXPENSES_ON_RETIREMENT_LIABILITIES_AND_PROVISIONS)){
        //contains all financing options already
        types.remove(PERIOD_RESULT_STATEMENT_TYPE_FINANCING)
    }

    if (descriptionList.isNotEmpty() && descriptionList.contains(PERIOD_RESULT_STATEMENT_TYPE_TAX_ON_PROFIT_TAX_EXPENSES_ON_PROFIT)){
        //contains all taxes options already
        types.remove(PERIOD_RESULT_STATEMENT_TYPE_TAX_ON_PROFIT)
    }

    if (descriptionList.isNotEmpty() && descriptionList.contains(PERIOD_RESULT_STATEMENT_TYPE_OPERATION_DISCONTINUED_LOSS_FROM_DISCONTINUED_OPERATIONS)){
        //contains all discontinued options already
        types.remove(PERIOD_RESULT_STATEMENT_TYPE_OPERATION_DISCONTINUED)
    }
    return types
}

fun getSignal(resultType: String): Int {
    val positives = listOf(
        PERIOD_RESULT_STATEMENT_TYPE_OPERATING_REVENUE,
        PERIOD_RESULT_STATEMENT_TYPE_OPERATING_OTHER_OPERATING_REVENUES,
        PERIOD_RESULT_STATEMENT_TYPE_INVESTING_PROFIT_SHARING_AND_GAINS_FROM_SALE_OF_INTERESTS_IN_ASSOCIATES_AND_JOINT_VENTURES,
    )
    return if (positives.contains(resultType))
        1
    else
        -1
}

fun getPeriodResultItemTypes(pre:String, category: String, excludeitems: List<String>): List<String>{
    var result = ArrayList(listOf<String>())
    if (category.isNotEmpty() && category==PERIOD_RESULT_STATEMENT_TYPE_OPERATING) {
         result = ArrayList(listOf(
             PERIOD_RESULT_STATEMENT_TYPE_OPERATING_REVENUE,
             PERIOD_RESULT_STATEMENT_TYPE_OPERATING_COST_OF_SALES,
             PERIOD_RESULT_STATEMENT_TYPE_OPERATING_OTHER_OPERATING_REVENUES,
             PERIOD_RESULT_STATEMENT_TYPE_OPERATING_SELLING_EXPENSES,
             PERIOD_RESULT_STATEMENT_TYPE_OPERATING_RESEARCH_AND_DEVELOPMENT_EXPENSES,
             PERIOD_RESULT_STATEMENT_TYPE_OPERATING_GENERAL_AND_ADMINISTRATIVE_EXPENSES,
             PERIOD_RESULT_STATEMENT_TYPE_OPERATING_GOODWILL_IMPAIRMENT_LOSS,
             PERIOD_RESULT_STATEMENT_TYPE_OPERATING_OTHER_OPERATING_EXPENSES
        ))
    }else
        if (category.isNotEmpty() && category==PERIOD_RESULT_STATEMENT_TYPE_INVESTING) {
             result = ArrayList(listOf(
                 PERIOD_RESULT_STATEMENT_TYPE_INVESTING_PROFIT_SHARING_AND_GAINS_FROM_SALE_OF_INTERESTS_IN_ASSOCIATES_AND_JOINT_VENTURES
            ))
        }else
            if (category.isNotEmpty() && category==PERIOD_RESULT_STATEMENT_TYPE_FINANCING) {
                 result = ArrayList(listOf(
                     PERIOD_RESULT_STATEMENT_TYPE_FINANCING_INTEREST_EXPENSES_ON_LOANS_AND_LEASE_LIABILITIES,
                     PERIOD_RESULT_STATEMENT_TYPE_FINANCING_INTEREST_EXPENSES_ON_RETIREMENT_LIABILITIES_AND_PROVISIONS,
                ))
            }else
                if (category.isNotEmpty() && category== PERIOD_RESULT_STATEMENT_TYPE_TAX_ON_PROFIT) {
                    result = ArrayList(listOf(
                        PERIOD_RESULT_STATEMENT_TYPE_TAX_ON_PROFIT_TAX_EXPENSES_ON_PROFIT,
                    ))
                }else
                    if (category.isNotEmpty() && category== PERIOD_RESULT_STATEMENT_TYPE_OPERATION_DISCONTINUED) {
                        result = ArrayList(listOf(
                            PERIOD_RESULT_STATEMENT_TYPE_OPERATION_DISCONTINUED_LOSS_FROM_DISCONTINUED_OPERATIONS,
                        ))
                    }
    if (pre.isNotEmpty()){
        val tmp = ArrayList<String>(result)
        tmp.add(0,pre)
        return tmp
    }
    result.removeAll(excludeitems.toSet())
    return result
}



/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun PeriodResultStatementItemsAddItemScreenContent(
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    modifier: Modifier = Modifier,
    periodResultStatementName: MutableState<String>,
    periodResultStatementId: MutableState<Long>,
    periodResultStatementStartDate: MutableState<String>,
    periodResultStatementEndDate: MutableState<String>,
    isPeriodResultStatementItemsAddItemScreen: MutableState<Boolean>,
    periodResultStatementDescriptionList: MutableList<String>,
) {

    val context = LocalContext.current
    val accountingAccountlevel1 = remember { mutableStateOf("") }
    val accountingAccountlevel4Description = remember { mutableStateOf("") }
    val resultItemValue = remember { mutableStateOf("") }
    val resultTypes = getMissingCategoryTypesForDescriptionList("",periodResultStatementDescriptionList)

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
                    text = periodResultStatementName.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = periodResultStatementStartDate.value + " - " + periodResultStatementEndDate.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Level1TypeResultDropdownAddItem(accountingAccountlevel1,accountingAccountlevel4Description,resultTypes)
            val level4Descrition = getPeriodResultItemTypes("",accountingAccountlevel1.value,periodResultStatementDescriptionList)
            Level4DropdownMenuAddItem(accountingAccountlevel4Description, level4Descrition)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            )
            {
                OutlinedTextField(
                    value = resultItemValue.value,
                    onValueChange = {
                        resultItemValue.value = it
                    },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = stringResource(R.string.period_results_statement_value),
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
                        isPeriodResultStatementItemsAddItemScreen.value = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.period_results_statement_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

                val financialMoveAddedSuccess = stringResource(R.string.period_results_statement_item_added_success)
                val emptyTypeMsg = stringResource(R.string.period_results_statement_Item_empty_type)
                val emptyDescrMsg = stringResource(R.string.period_results_statement_Item_empty_result_account)
                val emptyValueMsg = stringResource(R.string.period_results_statement_Item_empty_value)

                Button(
                    onClick = {

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
                                    if (resultItemValue.value.screenToDouble()==0.0){
                                        Toast.makeText(
                                            context,
                                            emptyValueMsg,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }else {
                                        try {
                                            val signal = getSignal(accountingAccountlevel4Description.value)
                                            val resultDouble = signal * resultItemValue.value.screenToDouble().absoluteValue
                                            periodResultStatementViewModel.savePeriodResultStatementItem(
                                                context,
                                                PeriodResultStatementItem(
                                                    0,
                                                    periodResultStatementId.value,
                                                    accountingAccountlevel4Description.value,
                                                    "",
                                                    0,
                                                    accountingAccountlevel1.value,
                                                    resultDouble,
                                                    0
                                                )
                                            )
                                            isPeriodResultStatementItemsAddItemScreen.value = false
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
                        text = stringResource(R.string.period_results_statement_save),
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
private fun PeriodResultStatementItemsEditItemScreenContent(
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    modifier: Modifier = Modifier,
    periodResultStatementName: MutableState<String>,
    periodResultStatementId: MutableState<Long>,
    periodResultStatementStartDate: MutableState<String>,
    periodResultStatementEndDate: MutableState<String>,
    isPeriodResultStatementItemsEditItemScreen: MutableState<Boolean>,
    periodResultStatementItemId: MutableState<Long>,
    periodResultStatementItemType: MutableState<String>,
    periodResultStatementItemDescription: MutableState<String>,
    periodResultStatementItemValue: MutableState<Double>,
    periodResultStatementDescriptionList: MutableList<String>,
) {

    val context = LocalContext.current
    val accountingAccountlevel1 = remember { mutableStateOf(periodResultStatementItemType.value) }
    val accountingAccountlevel4Description = remember { mutableStateOf(periodResultStatementItemDescription.value) }
    val resultItemValue = remember { mutableStateOf(periodResultStatementItemValue.value.toScreen()) }

    val resultTypes = getMissingCategoryTypesForDescriptionList("",periodResultStatementDescriptionList)

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
                    text = periodResultStatementName.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = periodResultStatementStartDate.value + " - " + periodResultStatementEndDate.value,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Level1TypeResultDropdownAddItem(accountingAccountlevel1,accountingAccountlevel4Description,resultTypes)
            val level4Description = getPeriodResultItemTypes("",accountingAccountlevel1.value,periodResultStatementDescriptionList)
            Level4DropdownMenuAddItem(accountingAccountlevel4Description, level4Description)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            )
            {

                OutlinedTextField(
                    value = resultItemValue.value,
                    onValueChange = {
                        resultItemValue.value = it
                    },
                    placeholder = { Text("") },
                    label = {
                        Text(
                            text = stringResource(R.string.cash_flows_statement_value),
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
                        isPeriodResultStatementItemsEditItemScreen.value = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.period_results_statement_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

                val financialMoveAddedSuccess = stringResource(R.string.period_results_statement_item_changed_success)
                val emptyTypeMsg = stringResource(R.string.period_results_statement_Item_empty_type)
                val emptyDescrMsg = stringResource(R.string.period_results_statement_Item_empty_result_account)
                val emptyValueMsg = stringResource(R.string.period_results_statement_Item_empty_value)

                Button(
                    onClick = {

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
                                    if (resultItemValue.value.screenToDouble()==0.0){
                                        Toast.makeText(
                                            context,
                                            emptyValueMsg,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }else {
                                        try {
                                            val signal = getSignal(accountingAccountlevel4Description.value)
                                            val resultDouble = signal * resultItemValue.value.screenToDouble().absoluteValue
                                            periodResultStatementViewModel.savePeriodResultStatementItem(
                                                context,
                                                PeriodResultStatementItem(
                                                    periodResultStatementItemId.value,
                                                    periodResultStatementId.value,
                                                    accountingAccountlevel4Description.value,
                                                    "",
                                                    0,
                                                    accountingAccountlevel1.value,
                                                    resultDouble,
                                                    0
                                                )
                                            )
                                            isPeriodResultStatementItemsEditItemScreen.value = false
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
                        text = stringResource(R.string.period_results_statement_save),
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
                        text = stringResource(R.string.period_results_statement_category),
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





/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun PeriodResultStatementHomeScreenContent(
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    modifier: Modifier = Modifier,
    isPeriodResultStatementEditScreen: MutableState<Boolean>,
    periodResultStatementName: MutableState<String>,
    periodResultStatementId: MutableState<Long>,
    periodResultStatementStartDate: MutableState<String>,
    periodResultStatementEndDate: MutableState<String>,
    isPeriodResultStatementItemsScreen: MutableState<Boolean>,
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
            val periodResultStatementsFlow = periodResultStatementViewModel.getPeriodResultStatements()
            val periodResultStatements by periodResultStatementsFlow.collectAsStateWithLifecycle(initialValue = emptyList())

            periodResultStatements.forEach { periodResultStatement ->

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = false,
                            onClick = {
                                isPeriodResultStatementEditScreen.value=true
                                periodResultStatementName.value=periodResultStatement.name
                                periodResultStatementId.value=periodResultStatement.periodResultStatementId
                                periodResultStatementStartDate.value = fmt.format(periodResultStatement.startDate)
                                periodResultStatementEndDate.value = fmt.format(periodResultStatement.endDate)
                            },
                            role = Role.Button
                        )
                ) {



                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                ) {
                            Text(
                                text = periodResultStatement.name,
                                style = TextStyle(fontFamily = FontFamily.SansSerif),
                                color = MaterialTheme.colorScheme.primary,

                            )
                            Text(
                                text = fmt.format(periodResultStatement.startDate) + " - " + fmt.format(periodResultStatement.endDate),
                                style = TextStyle(fontFamily = FontFamily.SansSerif),
                                color = MaterialTheme.colorScheme.primary,

                            )
                        }

                    Row( horizontalArrangement = Arrangement.End) {
                        TextButton(
                            modifier = Modifier.padding(1.dp),
                            onClick =
                                {
                                    isPeriodResultStatementEditScreen.value=true
                                    periodResultStatementName.value=periodResultStatement.name
                                    periodResultStatementId.value=periodResultStatement.periodResultStatementId
                                    periodResultStatementStartDate.value = fmt.format(periodResultStatement.startDate)
                                    periodResultStatementEndDate.value = fmt.format(periodResultStatement.endDate)
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
                                    isPeriodResultStatementItemsScreen.value = true
                                    periodResultStatementId.value = periodResultStatement.periodResultStatementId
                                    periodResultStatementName.value = periodResultStatement.name
                                    periodResultStatementStartDate.value = fmt.format(periodResultStatement.startDate)
                                    periodResultStatementEndDate.value = fmt.format(periodResultStatement.endDate)
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
                                    periodResultStatementId.value = periodResultStatement.periodResultStatementId
                                    periodResultStatementName.value = periodResultStatement.name
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
            PeriodResultStatementDeleteDialog(isDeleting, periodResultStatementId, periodResultStatementName, periodResultStatementViewModel,context)
        }
    }
}



/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun PeriodResultStatementAddScreenContent(
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    modifier: Modifier = Modifier,
    isPeriodResultStatementAddScreen: MutableState<Boolean>
) {

    val periodResultStatementName = remember { mutableStateOf("") }
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
                value = periodResultStatementName.value,
                onValueChange = {
                    periodResultStatementName.value = it
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
                            isPeriodResultStatementAddScreen.value = false
                            startDate = ""
                            endDate = ""
                        },
                )
                {
                    Text(
                        text = stringResource(R.string.period_results_statement_cancel),
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
                            if (periodResultStatementName.value.isEmpty()){
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
                                            periodResultStatementViewModel.savePeriodResultStatement(
                                                context,
                                                PeriodResultStatement(
                                                    0,
                                                    periodResultStatementName.value,
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
                                            isPeriodResultStatementAddScreen.value = false
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
                        text = stringResource(R.string.period_results_statement_save),
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
fun PeriodResultStatementDeleteDialog(
    isDeleting: MutableState<Boolean>,
    isDeletingId: MutableState<Long>,
    isDeletingName: MutableState<String>,
    periodResultStatementViewModel: PeriodResultStatementViewModel,
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
                        )


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
                            text = stringResource(R.string.period_results_statement_cancel),
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    val deleteSucessMsg = stringResource(R.string.cash_flows_statement_deleted_sucess)
                    Button(
                        onClick = {
                            periodResultStatementViewModel.deletePeriodResultStatement(PeriodResultStatement(isDeletingId.value,isDeletingName.value, Date(0), Date(0), 0))
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
fun PeriodResultStatementItemDeleteDialog(
    isDeletingPeriodResultStatementItem: MutableState<Boolean>,
    isDeletingPeriodResultStatementItemId: MutableState<Long>,
    isDeletingPeriodResultStatementItemType: MutableState<String>,
    isDeletingPeriodResultStatementItemDescription: MutableState<String>,
    isDeletingPeriodResultStatementItemValue: MutableState<Double>,
    periodResultStatementViewModel: PeriodResultStatementViewModel,
    context: Context
) {
    if (isDeletingPeriodResultStatementItem.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                isDeletingPeriodResultStatementItem.value = false
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
                        text = isDeletingPeriodResultStatementItemType.value,
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = isDeletingPeriodResultStatementItemDescription.value,
                        style = TextStyle(fontFamily = FontFamily.SansSerif),
                        color = MaterialTheme.colorScheme.primary,
                    )

                        Text(
                            text = isDeletingPeriodResultStatementItemValue.value.toScreen(),
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
                            isDeletingPeriodResultStatementItem.value = false
                            isDeletingPeriodResultStatementItemId.value=0
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.period_results_statement_cancel),
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    val financialMoveDeletedSucessMsg = stringResource(R.string.cash_flows_statement_financial_move_deleted_sucess)
                    Button(
                        onClick = {
                            periodResultStatementViewModel.deletePeriodResultStatementItem(PeriodResultStatementItem(isDeletingPeriodResultStatementItemId.value,0, "", "", 0,"",0.0,0))
                            isDeletingPeriodResultStatementItem.value = false
                            isDeletingPeriodResultStatementItemId.value=0
                            isDeletingPeriodResultStatementItemType.value=""
                            isDeletingPeriodResultStatementItemDescription.value=""
                            isDeletingPeriodResultStatementItemValue.value=0.0
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