/*
 */

package com.gtoretti.drego.data.balanceSheet

import com.gtoretti.drego.data.cashFlowsStatement.CashFlowStatement
import com.gtoretti.drego.data.cashFlowsStatement.CashFlowStatementItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BalanceSheetRepository @Inject constructor(private val balanceSheetDao: BalanceSheetDao) {

    fun getBalanceSheets() = balanceSheetDao.getActiveBalanceSheets()

    fun getBalanceSheetItems(id: Long) = balanceSheetDao.getBalanceSheetItems(id)

    suspend fun saveBalanceSheet(balanceSheet: BalanceSheet){
        balanceSheetDao.upsert(balanceSheet)
    }

    suspend fun saveBalanceSheetItem(balanceSheetItem: BalanceSheetItem){
        balanceSheetDao.upsert(balanceSheetItem)
    }

    suspend fun deleteBalanceSheet(balanceSheet: BalanceSheet){
        balanceSheetDao.delete(balanceSheet.balanceSheetId)
    }

    suspend fun deleteBalanceSheetItem(balanceSheetItem: BalanceSheetItem){
        balanceSheetDao.deleteItem(balanceSheetItem.balanceSheetItemId)
    }

}
