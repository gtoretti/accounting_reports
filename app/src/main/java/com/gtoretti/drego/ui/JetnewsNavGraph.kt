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

package com.gtoretti.drego.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gtoretti.drego.data.AppContainer

import com.gtoretti.drego.ui.accountingAccounts.AccountingAccountsScreen
import com.gtoretti.drego.ui.accountingAccounts.AccountingAccountsViewModel

import com.gtoretti.drego.ui.equityChangesStatement.EquityChangesStatementScreen
import com.gtoretti.drego.ui.equityChangesStatement.EquityChangesStatementViewModel

import com.gtoretti.drego.ui.balanceSheet.BalanceSheetScreen
import com.gtoretti.drego.ui.balanceSheet.BalanceSheetViewModel
import com.gtoretti.drego.ui.cashFlowsStatement.CashFlowsStatementScreen
import com.gtoretti.drego.ui.cashFlowsStatement.CashFlowsStatementViewModel
import com.gtoretti.drego.ui.explanatoryNotes.ExplanatoryNotesScreen
import com.gtoretti.drego.ui.explanatoryNotes.ExplanatoryNotesViewModel

import com.gtoretti.drego.ui.home.HomeScreen

import com.gtoretti.drego.ui.periodResultStatement.PeriodResultStatementScreen
import com.gtoretti.drego.ui.periodResultStatement.PeriodResultStatementViewModel


const val POST_ID = "postId"

@Composable
fun JetnewsNavGraph(
    appContainer: AppContainer,
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = JetnewsDestinations.HOME,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(JetnewsDestinations.HOME) {
            HomeScreen(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }

        composable(JetnewsDestinations.ACCOUNTING_ACCOUNTS) {
            val accountingAccountsViewModel: AccountingAccountsViewModel = hiltViewModel()
            AccountingAccountsScreen(
                accountingAccountsViewModel = accountingAccountsViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
        composable(JetnewsDestinations.BALANCE_SHEET) {
            val balanceSheetViewModel: BalanceSheetViewModel = hiltViewModel()
            val accountingAccountsViewModel: AccountingAccountsViewModel = hiltViewModel()
            BalanceSheetScreen(
                balanceSheetViewModel = balanceSheetViewModel,
                accountingAccountsViewModel = accountingAccountsViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
        composable(JetnewsDestinations.EQUITY_CHANGES_STATEMENT) {
            val equityChangesStatementViewModel: EquityChangesStatementViewModel = hiltViewModel()
            EquityChangesStatementScreen(
                equityChangesStatementViewModel = equityChangesStatementViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
        composable(JetnewsDestinations.EXPLANATORY_NOTES) {
            val explanatoryNotesViewModel: ExplanatoryNotesViewModel = hiltViewModel()
            ExplanatoryNotesScreen(
                explanatoryNotesViewModel = explanatoryNotesViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
        composable(JetnewsDestinations.CASH_FLOWS_STATEMENT) {
            val cashFlowsStatementViewModel: CashFlowsStatementViewModel = hiltViewModel()
            val accountingAccountsViewModel: AccountingAccountsViewModel = hiltViewModel()
            CashFlowsStatementScreen(
                cashFlowsStatementViewModel = cashFlowsStatementViewModel,
                accountingAccountsViewModel = accountingAccountsViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
        composable(JetnewsDestinations.PERIOD_RESULTS_STATEMENT) {
            val periodResultStatementViewModel: PeriodResultStatementViewModel = hiltViewModel()
            PeriodResultStatementScreen(
                periodResultStatementViewModel = periodResultStatementViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
    }
}
