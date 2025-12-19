/*
 */

package com.example.jetnews.data.periodResultStatement

import com.example.jetnews.data.cashFlowsStatement.CashFlowStatement
import com.example.jetnews.data.cashFlowsStatement.CashFlowStatementItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeriodResultStatementRepository @Inject constructor(private val periodResultStatementDao: PeriodResultStatementDao) {

    fun getPeriodResultStatements() = periodResultStatementDao.getActivePeriodResultStatements()

    fun getPeriodResultStatementItems(id: Long) = periodResultStatementDao.getPeriodResultStatementItems(id)

    suspend fun savePeriodResultStatement(periodResultStatement: PeriodResultStatement){
        periodResultStatementDao.upsert(periodResultStatement)
    }

    suspend fun deletePeriodResultStatement(periodResultStatement: PeriodResultStatement){
        periodResultStatementDao.delete(periodResultStatement.periodResultStatementId)
    }

    suspend fun savePeriodResultStatementItem(periodResultStatementItem: PeriodResultStatementItem){
        periodResultStatementDao.upsert(periodResultStatementItem)
    }

    suspend fun deletePeriodResultStatement(cashFlowStatement: CashFlowStatement){
        periodResultStatementDao.delete(cashFlowStatement.cashFlowStatementId)
    }

    suspend fun deletePeriodResultStatementItem(periodResultStatementItem: PeriodResultStatementItem){
        periodResultStatementDao.deleteItem(periodResultStatementItem.periodResultStatementItemId)
    }

}
