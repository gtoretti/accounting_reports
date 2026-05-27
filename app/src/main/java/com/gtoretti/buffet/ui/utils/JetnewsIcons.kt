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

package com.gtoretti.buffet.ui.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale


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
fun getLightGreenColor(): Color {
    if (isDarkTheme())
        return Color(0xFF317032)
    else
        return Color(0xFF4CAF50)
}

@Composable
fun getLightRedColor(): Color {
    if (isDarkTheme())
        return Color(0xFFA67272)
    else
        return Color(0xFFA91010)
}

@Composable
fun getLightBlueColor(): Color {
    if (isDarkTheme())
        return Color(0xFF092E4D)
    else
        return Color(0xFF6790B0)
}

@Composable
fun getPhoneColor(): Color {
    if (isDarkTheme())
        return Color(0xFF4D694E)
    else
        return Color(0xFF153116)
}

@Composable
fun getRedTextColor(): Color {
    if (isDarkTheme())
        return Color(0xFF654444)
    else
        return Color(0xFF330101)
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

fun Double.toDisplay(): String {
    val ret = this.toScreen()
    val intPart= ret.substring(0,ret.length-3)
    val brFormatter = NumberFormat.getInstance(Locale("pt", "BR"))
    return brFormatter.format(intPart.toLong()) + ret.substring(ret.length-3,ret.length)
}

fun Double.toPerc(): String {
    val ret = this.toDisplay() + " %"
    return ret
}

fun Double.toScreenParenthesis(): String {
    if (this>=0.0)
        return this.toDisplay()
    else
        return "("+this.toDisplay()+")"
}

fun hasWritePermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED;
}

fun requestWritePermission(context: Context, activity: Activity) {
    if (!hasWritePermission(context)) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
    }
}

fun Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }
}

@Composable
fun getColor(value: Double): Color{
    if (value>=0.0) return MaterialTheme.colorScheme.primary
    return getLightRedColor()
}