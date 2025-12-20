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

package com.example.jetnews.ui.utils

import android.content.res.Configuration
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import com.example.jetnews.R
import java.math.RoundingMode
import kotlin.math.absoluteValue

@Composable
fun FavoriteButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            painter = painterResource(R.drawable.ic_thumb_up),
            contentDescription = stringResource(R.string.cd_add_to_favorites),
        )
    }
}

@Composable
fun BookmarkButton(isBookmarked: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val clickLabel = stringResource(
        if (isBookmarked) R.string.unbookmark else R.string.bookmark,
    )
    IconToggleButton(
        checked = isBookmarked,
        onCheckedChange = { onClick() },
        modifier = modifier.semantics {
            // Use a custom click label that accessibility services can communicate to the user.
            // We only want to override the label, not the actual action, so for the action we pass null.
            this.onClick(label = clickLabel, action = null)
        },
    ) {
        Icon(
            painter = if (isBookmarked) painterResource(R.drawable.ic_bookmark) else painterResource(R.drawable.ic_bookmark_outline),
            contentDescription = null, // handled by click label of parent
        )
    }
}

@Composable
fun ShareButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            painter = painterResource(R.drawable.ic_share),
            contentDescription = stringResource(R.string.cd_share),
        )
    }
}

@Composable
fun TextSettingsButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            painter = painterResource(R.drawable.ic_text_settings),
            contentDescription = stringResource(R.string.cd_text_settings),
        )
    }
}

fun switchState(state: MutableState<Boolean>){
    if (state.value) {
        state.value = false
    } else {
        state.value = true
    }
}

@Composable
fun showExpandCollapseButton(expanded: Boolean,level: Int){
    if (level == 2){
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.subdirectory_arrow_right_transp_24px),
            contentDescription = "Abrir",
            modifier = Modifier
        )
    }else if (level == 3){
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.subdirectory_arrow_right_transp_24px),
            contentDescription = "Abrir",
            modifier = Modifier,

        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.subdirectory_arrow_right_transp_24px),
            contentDescription = "Abrir",
            modifier = Modifier
        )
    }
    if (expanded){
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.keyboard_arrow_down_24px),
            contentDescription = "Fechar",
            modifier = Modifier
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.folder_open_24px),
            contentDescription = "Fechar",
            modifier = Modifier
        )
    }else{
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.keyboard_arrow_right_24px),
            contentDescription = "Fechar",
            modifier = Modifier
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.folder_24px),
            contentDescription = "Abrir",
            modifier = Modifier
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    openDialog: MutableState<Boolean>,
    onDateSelected: (Long?) -> Unit,
    title: String
) {

    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest={},
        confirmButton={
            TextButton(onClick = {
                openDialog.value = false

                var prod = 86400000
                //var prod = 0
                onDateSelected(datePickerState.selectedDateMillis?.plus(prod))
            }) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                openDialog.value = false
            }) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(title = {}, state = datePickerState, headline = {
            Text(title)
        }, showModeToggle = false)
    }
}

@Composable
fun isDarkTheme(): Boolean {
    val context = LocalContext.current
    return context.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}

@Composable
fun getLightGreenColor(): androidx.compose.ui.graphics.Color {
    if (isDarkTheme())
        return androidx.compose.ui.graphics.Color(0xFF224D23)
    else
        return androidx.compose.ui.graphics.Color(0xFF4CAF50)
}

@Composable
fun getLightRedColor(): androidx.compose.ui.graphics.Color {
    if (isDarkTheme())
        return androidx.compose.ui.graphics.Color(0xFF480101)
    else
        return androidx.compose.ui.graphics.Color(0xFFFC6868)
}

@Composable
fun getLightBlueColor(): androidx.compose.ui.graphics.Color {
    if (isDarkTheme())
        return androidx.compose.ui.graphics.Color(0xFF092E4D)
    else
        return androidx.compose.ui.graphics.Color(0xFF6790B0)
}

@Composable
fun getPhoneColor(): androidx.compose.ui.graphics.Color {
    if (isDarkTheme())
        return androidx.compose.ui.graphics.Color(0xFF4D694E)
    else
        return androidx.compose.ui.graphics.Color(0xFF153116)
}

@Composable
fun getRedTextColor(): androidx.compose.ui.graphics.Color {
    if (isDarkTheme())
        return androidx.compose.ui.graphics.Color(0xFF654444)
    else
        return androidx.compose.ui.graphics.Color(0xFF330101)
}

fun String.screenToDouble(): Double {
    try{
        if (this.trim().isEmpty()) return 0.0
            var ret = this.replace(".", "")
        ret = ret.replace(",", ".")
        return ret.toDouble()
    }catch (e: Exception){
        return 0.0
    }
}

fun Double.toScreen(): String {
    return this.toBigDecimal().setScale(2, RoundingMode.UP).toString().replace(".", ",")
}

fun Double.toScreenParenthesis(): String {
    if (this>=0.0)
        return this.absoluteValue.toBigDecimal().setScale(2, RoundingMode.UP).toString().replace(".", ",")
    else
        return "("+this.absoluteValue.toBigDecimal().setScale(2, RoundingMode.UP).toString().replace(".", ",")+")"
}