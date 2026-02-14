/*
 */

package com.gtoretti.drego.ui.balanceSheet

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtoretti.drego.data.balanceSheet.BalanceSheet
import com.gtoretti.drego.data.balanceSheet.BalanceSheetItem
import com.gtoretti.drego.data.balanceSheet.BalanceSheetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanceSheetViewModel @Inject internal constructor(
    private val repository: BalanceSheetRepository
) : ViewModel() {

    fun getBalanceSheets() = repository.getBalanceSheets()

    fun getBalanceSheetItems(balanceSheetId:Long) = repository.getBalanceSheetItems(balanceSheetId)

    fun saveBalanceSheet(balanceSheet: BalanceSheet) {

        viewModelScope.launch {
            repository.saveBalanceSheet(balanceSheet)
        }
    }

    fun saveBalanceSheetItem(balanceSheetItem: BalanceSheetItem) {

        viewModelScope.launch {
            repository.saveBalanceSheetItem(balanceSheetItem)
        }
    }

    fun deleteBalanceSheet(cashFlowStatement: BalanceSheet) {
        viewModelScope.launch {
            repository.deleteBalanceSheet(cashFlowStatement)
        }
    }

    fun deleteBalanceSheetItem(balanceSheetIdItem: BalanceSheetItem) {
        viewModelScope.launch {
            repository.deleteBalanceSheetItem(balanceSheetIdItem)
        }
    }
}