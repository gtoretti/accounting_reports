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

package com.gtoretti.buffet.ui.menu

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtoretti.buffet.R
import com.gtoretti.buffet.ui.theme.BuffetTheme

@Composable
fun AppDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToTesouro: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalDrawerSheet(
        drawerState = drawerState,
        modifier = modifier,
    ) {
        BuffetLogo(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.home_title)) },
            icon = { Icon(painterResource(R.drawable.ic_home), null) },
            selected = currentRoute == BuffetDestinations.HOME,
            onClick = {
                navigateToHome()
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )

        val context = LocalContext.current
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.istagram_title)) },
            icon = { Icon(painterResource(R.drawable.ic_list_alt), null) },
            selected = currentRoute == BuffetDestinations.INSTAGRAM,
            onClick = {
                closeDrawer()
                try{
                    val uri = Uri.parse("https://www.instagram.com/buffetrealizeeventos")
                    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                        setPackage("com.instagram.android")
                    }
                    context.startActivity(intent)
                }catch (e: Exception){
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/buffetrealizeeventos"))
                    context.startActivity(browserIntent)
                }
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )

        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.facebook_title)) },
            icon = { Icon(painterResource(R.drawable.ic_list_alt), null) },
            selected = currentRoute == BuffetDestinations.FACEBOOK,
            onClick = {
                closeDrawer()
                try{
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/realizeeventospaulinia/"))
                    context.startActivity(browserIntent)
                }catch (e: Exception){


                }
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )



        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.tesouro_title)) },
            icon = { Icon(painterResource(R.drawable.ic_list_alt), null) },
            selected = currentRoute == BuffetDestinations.TESOURO,
            onClick = {
                navigateToTesouro()
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )


    }
}

@Composable
private fun BuffetLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,) {
        Image(
            painter = painterResource(id = R.drawable.logo_realize),
            contentDescription = "Buffet",
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
    BuffetTheme {
        AppDrawer(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
            currentRoute = BuffetDestinations.INSTAGRAM,
            navigateToHome = {},
            navigateToTesouro = {},
            closeDrawer = { },
        )
    }
}
