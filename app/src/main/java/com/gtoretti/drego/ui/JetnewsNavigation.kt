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

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Destinations used in the [JetnewsApp].
 */
object JetnewsDestinations {
    const val HOME = "Início"
    const val ACCOUNTING_ACCOUNTS = "Plano de Contas"
    const val CASH_FLOWS_STATEMENT = "DFC - Demonstração dos Fluxos de Caixa"
    const val PERIOD_RESULTS_STATEMENT = "DRE - Demonstração dos Resultados do Exercício"
    const val BALANCE_SHEET = "BP - Balanço Patrimonial"
    const val EQUITY_CHANGES_STATEMENT = "DMPL - Demonstração das Mutações do Patrimônio Líquido"
    const val EXPLANATORY_NOTES = "Notas Explicativas"

}

/**
 * Models the navigation actions in the app.
 */
class JetnewsNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(JetnewsDestinations.HOME) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    val navigateToAccountingAccounts: () -> Unit = {
        navController.navigate(JetnewsDestinations.ACCOUNTING_ACCOUNTS) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToBalanceSheet: () -> Unit = {
        navController.navigate(JetnewsDestinations.BALANCE_SHEET) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToEquityChangesStatement: () -> Unit = {
        navController.navigate(JetnewsDestinations.EQUITY_CHANGES_STATEMENT) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToCashFlowsStatement: () -> Unit = {
        navController.navigate(JetnewsDestinations.CASH_FLOWS_STATEMENT) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToPeriodResultsStatement: () -> Unit = {
        navController.navigate(JetnewsDestinations.PERIOD_RESULTS_STATEMENT) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToExplanatoryNotes: () -> Unit = {
        navController.navigate(JetnewsDestinations.EXPLANATORY_NOTES) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
