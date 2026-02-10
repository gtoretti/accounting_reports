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

package com.example.jetnews.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.jetnews.JetnewsApplication.Companion.JETNEWS_APP_URI
import com.example.jetnews.data.AppContainer

import com.example.jetnews.ui.accountingAccounts.AccountingAccountsScreen
import com.example.jetnews.ui.accountingAccounts.AccountingAccountsViewModel

import com.example.jetnews.ui.equityChangesStatement.EquityChangesStatementScreen
import com.example.jetnews.ui.equityChangesStatement.EquityChangesStatementViewModel

import com.example.jetnews.ui.balanceSheet.BalanceSheetScreen
import com.example.jetnews.ui.balanceSheet.BalanceSheetViewModel
import com.example.jetnews.ui.cashFlowsStatement.CashFlowsStatementScreen
import com.example.jetnews.ui.cashFlowsStatement.CashFlowsStatementViewModel
import com.example.jetnews.ui.explanatoryNotes.ExplanatoryNotesScreen
import com.example.jetnews.ui.explanatoryNotes.ExplanatoryNotesViewModel
import com.example.jetnews.ui.home.HomeRoute
import com.example.jetnews.ui.home.HomeScreen
import com.example.jetnews.ui.home.HomeViewModel
import com.example.jetnews.ui.periodResultStatement.PeriodResultStatementScreen
import com.example.jetnews.ui.periodResultStatement.PeriodResultStatementViewModel


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
