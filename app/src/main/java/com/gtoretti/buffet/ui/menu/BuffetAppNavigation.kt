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

package com.gtoretti.buffet.ui.menu

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Destinations used in the [BuffetApp].
 */
object BuffetDestinations {
    const val HOME = "Início"
    const val ATRACOES = "Atrações"
    const val FESTAS = "Festas"
    const val O_BUFFET = "O Buffet"
    const val TESOURO = "Caça ao Tesouro"
}

/**
 * Models the navigation actions in the app.
 */
class BuffetNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(BuffetDestinations.HOME) {
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
        navController.navigate(BuffetDestinations.ATRACOES) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToCashFlowsStatement: () -> Unit = {
        navController.navigate(BuffetDestinations.FESTAS) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToPeriodResultsStatement: () -> Unit = {
        navController.navigate(BuffetDestinations.TESOURO) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToExplanatoryNotes: () -> Unit = {
        navController.navigate(BuffetDestinations.O_BUFFET) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
