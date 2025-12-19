/*
 */

package com.example.jetnews.ui.equityChangesStatement

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnews.R

import com.example.jetnews.data.equityChangesStatement.EquityChangesStatement
import com.example.jetnews.data.equityChangesStatement.EquityChangesStatementItem
import com.example.jetnews.data.equityChangesStatement.EquityChangesStatementRepository
import com.example.jetnews.data.periodResultStatement.PeriodResultStatement
import com.example.jetnews.data.periodResultStatement.PeriodResultStatementItem

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EquityChangesStatementViewModel @Inject internal constructor(
    private val repository: EquityChangesStatementRepository
) : ViewModel() {

    fun getEquityChangesStatements() = repository.getEquityChangesStatements()

    fun getEquityChangesStatementItems(equityChangesStatementId:Long) = repository.getEquityChangesStatementItems(equityChangesStatementId)

    fun saveEquityChangesStatement(equityChangesStatement: EquityChangesStatement) {
        viewModelScope.launch {
            repository.saveEquityChangesStatement(equityChangesStatement)
        }
    }

    fun saveEquityChangesStatementItem(equityChangesStatementItem: EquityChangesStatementItem) {
        viewModelScope.launch {
            repository.saveEquityChangesStatementItem(equityChangesStatementItem)
        }
    }

    fun deleteEquityChangesStatement(equityChangesStatement: EquityChangesStatement) {
        viewModelScope.launch {
            repository.deleteEquityChangesStatement(equityChangesStatement)
        }
    }

    fun deleteEquityChangesStatementItem(equityChangesStatementItem: EquityChangesStatementItem) {
        viewModelScope.launch {
            repository.deleteEquityChangesStatementItem(equityChangesStatementItem)
        }
    }

}