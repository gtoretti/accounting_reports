/*
 * Copyright 2020 The Android Open Source Project
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

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtoretti.drego.R
import com.gtoretti.drego.ui.theme.JetnewsTheme

@Composable
fun AppDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToAccountingAccounts: () -> Unit,
    navigateToBalanceSheet: () -> Unit,
    navigateToEquityChangesStatement: () -> Unit,
    navigateToCashFlowsStatement: () -> Unit,
    navigateToPeriodResultsStatement: () -> Unit,
    navigateToExplanatoryNotes: () -> Unit,

    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalDrawerSheet(
        drawerState = drawerState,
        modifier = modifier,
    ) {
        JetNewsLogo(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.home_title)) },
            icon = { Icon(painterResource(R.drawable.ic_home), null) },
            selected = currentRoute == JetnewsDestinations.HOME,
            onClick = {
                navigateToHome()
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )
        /* habilitar no ultimo ano de curso.
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.accounting_accounts_title)) },
            icon = { Icon(painterResource(R.drawable.ic_list_alt), null) },
            selected = currentRoute == JetnewsDestinations.ACCOUNTING_ACCOUNTS,
            onClick = {
                navigateToAccountingAccounts()
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.cash_flows_statement_title)) },
            icon = { Icon(painterResource(R.drawable.ic_list_alt), null) },
            selected = currentRoute == JetnewsDestinations.CASH_FLOWS_STATEMENT,
            onClick = {
                navigateToCashFlowsStatement()
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )*/
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.period_results_statement_title)) },
            icon = { Icon(painterResource(R.drawable.ic_list_alt), null) },
            selected = currentRoute == JetnewsDestinations.PERIOD_RESULTS_STATEMENT,
            onClick = {
                navigateToPeriodResultsStatement()
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )
        /* habilitar no ultimo ano de curso.
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.balance_sheet_title)) },
            icon = { Icon(painterResource(R.drawable.ic_list_alt), null) },
            selected = currentRoute == JetnewsDestinations.BALANCE_SHEET,
            onClick = {
                navigateToBalanceSheet()
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.equity_changes_statement_title)) },
            icon = { Icon(painterResource(R.drawable.ic_list_alt), null) },
            selected = currentRoute == JetnewsDestinations.EQUITY_CHANGES_STATEMENT,
            onClick = {
                navigateToEquityChangesStatement()
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.explanatory_notes_title)) },
            icon = { Icon(painterResource(R.drawable.ic_list_alt), null) },
            selected = currentRoute == JetnewsDestinations.EXPLANATORY_NOTES,
            onClick = {
                navigateToExplanatoryNotes()
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )

         */
    }
}

@Composable
private fun JetNewsLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_ico),
            contentDescription = "DRE go!",
            modifier = Modifier.size(width = 50.dp, height = 50.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(stringResource(id = R.string.app_name),fontWeight = FontWeight.Bold,)
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppDrawer() {
    JetnewsTheme {
        AppDrawer(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
            currentRoute = JetnewsDestinations.ACCOUNTING_ACCOUNTS,
            navigateToHome = {},
            navigateToAccountingAccounts = {},
            navigateToBalanceSheet = {},
            navigateToEquityChangesStatement = {},
            navigateToCashFlowsStatement = {},
            navigateToPeriodResultsStatement = {},
            navigateToExplanatoryNotes = {},
            closeDrawer = { },
        )
    }
}
