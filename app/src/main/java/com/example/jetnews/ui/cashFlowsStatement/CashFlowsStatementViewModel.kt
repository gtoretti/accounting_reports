/*
 */

package com.example.jetnews.ui.cashFlowsStatement

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnews.R
import com.example.jetnews.data.cashFlowsStatement.CashFlowStatement
import com.example.jetnews.data.cashFlowsStatement.CashFlowStatementItem
import com.example.jetnews.data.cashFlowsStatement.CashFlowsStatementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CashFlowsStatementViewModel @Inject internal constructor(
    private val repository: CashFlowsStatementRepository
) : ViewModel() {

    fun getCashFlowStatements() = repository.getCashFlowStatements()

    fun getCashFlowStatementItems(cashFlowStatementId:Long) = repository.getCashFlowStatementItems(cashFlowStatementId)

    @Throws(Exception::class)
    fun saveCashFlowStatement(context: Context, cashFlowStatement: CashFlowStatement) {

        if (cashFlowStatement.endDate.before(cashFlowStatement.startDate)){
            throw Exception(context.getString(R.string.cash_flows_statement_invalid_dates))
        }

        viewModelScope.launch {
            repository.saveCashFlowStatement(cashFlowStatement)
        }
    }

    @Throws(Exception::class)
    fun saveCashFlowStatementItem(context: Context, cashFlowStatementItem: CashFlowStatementItem, cashFlowStatement: CashFlowStatement) {

        if (cashFlowStatementItem.date.before(cashFlowStatement.startDate) ||
            cashFlowStatementItem.date.after(cashFlowStatement.endDate))
            throw Exception(context.getString(R.string.cash_flows_statement_Item_invalid_date))

        viewModelScope.launch {
                repository.saveCashFlowStatementItem(cashFlowStatementItem)
        }
    }

    fun deleteCashFlowStatement(cashFlowStatement: CashFlowStatement) {
        viewModelScope.launch {
            repository.deleteCashFlowStatement(cashFlowStatement)
        }
    }

    fun deleteCashFlowStatementItem(cashFlowStatementItem: CashFlowStatementItem) {
        viewModelScope.launch {
            repository.deleteCashFlowStatementItem(cashFlowStatementItem)
        }
    }


}