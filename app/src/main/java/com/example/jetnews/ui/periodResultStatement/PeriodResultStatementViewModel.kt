/*
 */

package com.example.jetnews.ui.periodResultStatement

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnews.R
import com.example.jetnews.data.cashFlowsStatement.CashFlowStatement
import com.example.jetnews.data.cashFlowsStatement.CashFlowStatementItem

import com.example.jetnews.data.explanatoryNotes.ExplanatoryNote
import com.example.jetnews.data.explanatoryNotes.ExplanatoryNoteRepository
import com.example.jetnews.data.periodResultStatement.PeriodResultStatement
import com.example.jetnews.data.periodResultStatement.PeriodResultStatementItem
import com.example.jetnews.data.periodResultStatement.PeriodResultStatementRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeriodResultStatementViewModel @Inject internal constructor(
    private val repository: PeriodResultStatementRepository
) : ViewModel() {

    fun getPeriodResultStatements() = repository.getPeriodResultStatements()

    fun getPeriodResultStatementItems(periodResultStatementId:Long) = repository.getPeriodResultStatementItems(periodResultStatementId)

    @Throws(Exception::class)
    fun savePeriodResultStatement(context: Context, periodResultStatement: PeriodResultStatement) {

        if (periodResultStatement.endDate.before(periodResultStatement.startDate)){
            throw Exception(context.getString(R.string.cash_flows_statement_invalid_dates))
        }

        viewModelScope.launch {
            repository.savePeriodResultStatement(periodResultStatement)
        }
    }

    @Throws(Exception::class)
    fun savePeriodResultStatementItem(context: Context, periodResultStatementItem: PeriodResultStatementItem) {
        viewModelScope.launch {
            repository.savePeriodResultStatementItem(periodResultStatementItem)
        }
    }

    fun deletePeriodResultStatement(periodResultStatement: PeriodResultStatement) {
        viewModelScope.launch {
            repository.deletePeriodResultStatement(periodResultStatement)
        }
    }

    fun deletePeriodResultStatementItem(periodResultStatementItem: PeriodResultStatementItem) {
        viewModelScope.launch {
            repository.deletePeriodResultStatementItem(periodResultStatementItem)
        }
    }
}