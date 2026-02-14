/*
 */

package com.gtoretti.drego.ui.equityChangesStatement

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtoretti.drego.R

import com.gtoretti.drego.data.equityChangesStatement.EquityChangesStatement
import com.gtoretti.drego.data.equityChangesStatement.EquityChangesStatementItem
import com.gtoretti.drego.data.equityChangesStatement.EquityChangesStatementRepository
import com.gtoretti.drego.data.periodResultStatement.PeriodResultStatement
import com.gtoretti.drego.data.periodResultStatement.PeriodResultStatementItem

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