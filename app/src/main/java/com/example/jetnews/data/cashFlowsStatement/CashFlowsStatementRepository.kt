/*
 */

package com.example.jetnews.data.cashFlowsStatement

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CashFlowsStatementRepository @Inject constructor(private val cashFlowsStatementDao: CashFlowsStatementDao) {

    fun getCashFlowStatements() = cashFlowsStatementDao.getActiveCashFlowStatements()

    fun getCashFlowStatementItems(id: Long) = cashFlowsStatementDao.getCashFlowStatementItems(id)

    suspend fun saveCashFlowStatement(cashFlowStatement: CashFlowStatement){
        cashFlowsStatementDao.upsert(cashFlowStatement)
    }

    suspend fun saveCashFlowStatementItem(cashFlowStatementItem: CashFlowStatementItem){
        cashFlowsStatementDao.upsert(cashFlowStatementItem)
    }

    suspend fun deleteCashFlowStatement(cashFlowStatement: CashFlowStatement){
        cashFlowsStatementDao.delete(cashFlowStatement.cashFlowStatementId)
    }

    suspend fun deleteCashFlowStatementItem(cashFlowStatementItem: CashFlowStatementItem){
        cashFlowsStatementDao.deleteItem(cashFlowStatementItem.cashFlowStatementItemId)
    }

}
