/*
 */

package com.gtoretti.drego.ui.explanatoryNotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.gtoretti.drego.data.equityChangesStatement.EquityChangesStatement
import com.gtoretti.drego.data.equityChangesStatement.EquityChangesStatementRepository
import com.gtoretti.drego.data.explanatoryNotes.ExplanatoryNote
import com.gtoretti.drego.data.explanatoryNotes.ExplanatoryNoteRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExplanatoryNotesViewModel @Inject internal constructor(
    private val repository: ExplanatoryNoteRepository
) : ViewModel() {

    fun getExplanatoryNotes() = repository.getExplanatoryNotes()

    fun getExplanatoryNote(id:Long) = repository.getExplanatoryNote(id)

    fun saveExplanatoryNote(explanatoryNote: ExplanatoryNote) {
        viewModelScope.launch {
            repository.saveExplanatoryNotes(explanatoryNote)
        }
    }

    fun deleteExplanatoryNote(explanatoryNote: ExplanatoryNote) {
        viewModelScope.launch {
            repository.deleteExplanatoryNote(explanatoryNote)
        }
    }
}