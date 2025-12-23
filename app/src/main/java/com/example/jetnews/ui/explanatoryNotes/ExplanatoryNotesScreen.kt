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

package com.example.jetnews.ui.explanatoryNotes

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button

import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetnews.R
import com.example.jetnews.data.equityChangesStatement.EquityChangesStatement
import com.example.jetnews.data.explanatoryNotes.ExplanatoryNote
import kotlin.Boolean


/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplanatoryNotesScreen(
    explanatoryNotesViewModel: ExplanatoryNotesViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isAdding = remember { mutableStateOf(false) }
    var isEditing = remember { mutableStateOf(false) }
    var isEditingIndex = remember { mutableStateOf("") }
    var isEditingContent = remember { mutableStateOf("") }
    var isEditingId = remember { mutableLongStateOf(0) }

    when {
        isAdding.value -> {
            AssetEvolutionAddScreen(
                explanatoryNotesViewModel = explanatoryNotesViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                snackbarHostState,
                isAdding
            )
        }
        isEditing.value -> {
            AssetEvolutionEditScreen(
                explanatoryNotesViewModel = explanatoryNotesViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                snackbarHostState,
                isEditing,
                isEditingIndex,
                isEditingContent,
                isEditingId
            )
        }
        else -> {
            ExplanatoryNotesHomeScreen(
                explanatoryNotesViewModel = explanatoryNotesViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                snackbarHostState,
                isAdding,
                isEditing,
                isEditingIndex,
                isEditingContent,
                isEditingId,
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
fun ExplanatoryNotesHomeScreen(
    explanatoryNotesViewModel: ExplanatoryNotesViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isAdding: MutableState<Boolean>,
    isEditing: MutableState<Boolean>,
    isEditingIndex: MutableState<String>,
    isEditingContent: MutableState<String>,
    isEditingId: MutableState<Long>,
) {


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.explanatory_notes_title),
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
                            contentDescription = stringResource(R.string.addExplanatoryNote),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        ExplanatoryNotesHomeScreenContent(
            explanatoryNotesViewModel,
            screenModifier,
            isEditing,
            isEditingIndex,
            isEditingContent,
            isEditingId,
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
fun AssetEvolutionAddScreen(
    explanatoryNotesViewModel: ExplanatoryNotesViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isAdding: MutableState<Boolean>
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.addExplanatoryNote),
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
        ExplanatoryNotesAddScreenContent(
            explanatoryNotesViewModel,
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
fun AssetEvolutionEditScreen(
    explanatoryNotesViewModel: ExplanatoryNotesViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
    isEditing: MutableState<Boolean>,
    isEditingIndex: MutableState<String>,
    isEditingContent: MutableState<String>,
    isEditingId: MutableState<Long>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.editExplanatoryNote),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {
                        isEditing.value=false
                        isEditingIndex.value=""
                        isEditingContent.value=""
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
        ExplanatoryNoteEditScreenContent(
            explanatoryNotesViewModel,
            screenModifier,
            isEditing,
            isEditingIndex,
            isEditingContent,
            isEditingId
        )
    }
}


/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun ExplanatoryNoteEditScreenContent(
    explanatoryNotesViewModel: ExplanatoryNotesViewModel,
    modifier: Modifier = Modifier,
    isEditing: MutableState<Boolean>,
    isEditingIndex: MutableState<String>,
    isEditingContent: MutableState<String>,
    isEditingId: MutableState<Long>,
) {

    var explanatoryIndex = remember { mutableStateOf(isEditingIndex.value) }
    var explanatoryContent = remember { mutableStateOf(isEditingContent.value) }
    val context = LocalContext.current

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

            OutlinedTextField(
                value = explanatoryIndex.value,
                onValueChange = {
                    explanatoryIndex.value = it
                },
                placeholder = { Text("") },
                label = {
                    Text(
                        text = stringResource(R.string.explanatory_notes_index),
                    )
                }
            )

            OutlinedTextField(
                value = explanatoryContent.value,
                onValueChange = {
                    explanatoryContent.value = it
                },
                placeholder = { Text("") },
                modifier = Modifier.fillMaxWidth().height(250.dp),singleLine = false,
                label = {
                    Text(
                        text = stringResource(R.string.explanatory_notes_text),
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
                        isEditingIndex.value=""
                        isEditingContent.value=""
                        explanatoryIndex.value=""
                        explanatoryContent.value=""
                        isEditingId.value=0
                    },

                    ) {
                    Text(
                        text = stringResource(R.string.explanatory_notes_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
                Button(
                    onClick = {
                        explanatoryNotesViewModel.saveExplanatoryNote(ExplanatoryNote(isEditingId.value,explanatoryIndex.value,explanatoryContent.value,0))
                        Toast.makeText(
                            context,
                            "Demonstração das Mutações do Patrimônio Líquido modificada com sucesso.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        isEditing.value=false
                        isEditingIndex.value=""
                        isEditingContent.value=""
                        explanatoryIndex.value=""
                        explanatoryContent.value=""
                        isEditingId.value=0
                    },

                    ) {
                    Text(
                        text = stringResource(R.string.explanatory_notes_save),
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
private fun ExplanatoryNotesHomeScreenContent(
    explanatoryNotesViewModel: ExplanatoryNotesViewModel,
    modifier: Modifier = Modifier,
    isEditing: MutableState<Boolean>,
    isEditingIndex: MutableState<String>,
    isEditingContent: MutableState<String>,
    isEditingId: MutableState<Long>,
) {

    val context = LocalContext.current
    val isDeleting = remember { mutableStateOf(false) }
    val isDeletingId = remember { mutableLongStateOf(0) }
    val isDeletingIndex = remember { mutableStateOf("") }
    val isDeletingContent = remember { mutableStateOf("") }

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
            val explanatoryNotesFlow = explanatoryNotesViewModel.getExplanatoryNotes()
            val explanatoryNotes by explanatoryNotesFlow.collectAsStateWithLifecycle(initialValue = emptyList())

            explanatoryNotes.forEach { explanatoryNote ->

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = false,
                            onClick = {
                                isEditing.value=true
                                isEditingIndex.value=explanatoryNote.index
                                isEditingContent.value=explanatoryNote.content
                                isEditingId.value=explanatoryNote.explanatoryNoteId
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
                        Text(
                            text = explanatoryNote.index,
                            style = TextStyle(fontFamily = FontFamily.SansSerif),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }

                    TextButton(
                        modifier = Modifier.padding(1.dp),
                        onClick =
                            {
                                isEditing.value=true
                                isEditingIndex.value=explanatoryNote.index
                                isEditingContent.value=explanatoryNote.content
                                isEditingId.value=explanatoryNote.explanatoryNoteId
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
                                isDeleting.value = true
                                isDeletingId.value = explanatoryNote.explanatoryNoteId
                                isDeletingIndex.value = explanatoryNote.index
                                isDeletingContent.value = explanatoryNote.content
                            }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.delete_24px),
                            contentDescription = stringResource(R.string.explanatory_notes_delete),
                            modifier = Modifier,
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }

    when {
        isDeleting.value -> {
            ExplanatoryNoteDeleteDialog(isDeleting, isDeletingId, isDeletingIndex, isDeletingContent, explanatoryNotesViewModel,context)
        }
    }
}



/**
 * Displays AccountingAccountsHomeScreenContent
 */
@Composable
private fun ExplanatoryNotesAddScreenContent(
    explanatoryNotesViewModel: ExplanatoryNotesViewModel,
    modifier: Modifier = Modifier,
    isAdding: MutableState<Boolean>
) {

    var explanatoryNoteIndex = remember { mutableStateOf("") }
    var explanatoryNoteContent = remember { mutableStateOf("") }
    val context = LocalContext.current

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

            OutlinedTextField(
                value = explanatoryNoteIndex.value,
                onValueChange = {
                    explanatoryNoteIndex.value = it
                },
                placeholder = { Text("") },
                label = {
                    Text(
                        text = "Índice/Título:",
                    )
                }
            )

            OutlinedTextField(
                value = explanatoryNoteContent.value,
                onValueChange = {
                    explanatoryNoteContent.value = it
                },
                modifier = Modifier.fillMaxWidth().height(250.dp),singleLine = false,
                placeholder = { Text("") },
                label = {
                    Text(
                        text = "Texto:",
                    )
                }
            )

            val msgSuccess = stringResource(R.string.explanatory_notes_added_success)

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
                        text = stringResource(R.string.explanatory_notes_cancel),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
                Button(
                    onClick = {
                        explanatoryNotesViewModel.saveExplanatoryNote(ExplanatoryNote(0,explanatoryNoteIndex.value,explanatoryNoteContent.value,0))
                        Toast.makeText(
                            context,
                            msgSuccess,
                            Toast.LENGTH_SHORT,
                        ).show()
                        isAdding.value=false
                    },

                    ) {
                    Text(
                        text = stringResource(R.string.explanatory_notes_save),
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
fun ExplanatoryNoteDeleteDialog(
    isDeleting: MutableState<Boolean>,
    isDeletingId: MutableState<Long>,
    isDeletingIndex: MutableState<String>,
    isDeletingContent: MutableState<String>,
    explanatoryNotesViewModel: ExplanatoryNotesViewModel,
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
                    text =  stringResource(R.string.explanatory_notes_delete_note),
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
                            text = isDeletingIndex.value,
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
                            isDeleting.value = false
                            isDeletingId.value = 0
                            isDeletingIndex.value = ""
                            isDeletingContent.value = ""
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
                            explanatoryNotesViewModel.deleteExplanatoryNote(ExplanatoryNote(isDeletingId.value,isDeletingIndex.value,isDeletingContent.value,0))
                            isDeleting.value = false
                            isDeletingId.value = 0
                            isDeletingIndex.value = ""
                            isDeletingContent.value = ""
                            Toast.makeText(
                                context,
                                "Nota explicativa excluída com sucesso.",
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