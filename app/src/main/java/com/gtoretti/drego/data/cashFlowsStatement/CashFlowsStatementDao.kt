/*
 */

package com.gtoretti.drego.data.cashFlowsStatement

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * The Data Access Object.
 */
@Dao
interface CashFlowsStatementDao {

    @Query("SELECT * FROM cash_flow_statement where deleted = 0 ORDER BY name")
    fun getActiveCashFlowStatements(): Flow<List<CashFlowStatement>>

    @Query("SELECT * FROM cash_flow_statement_item where cashFlowStatementId = :id and deleted = 0 order by date")
    fun getCashFlowStatementItems(id: Long): Flow<List<CashFlowStatementItem>>

    @Upsert
    suspend fun upsert(cashFlowStatement: CashFlowStatement)

    @Upsert
    suspend fun upsert(cashFlowStatementItem: CashFlowStatementItem)

    @Query("UPDATE cash_flow_statement set deleted = 1 where id = :id")
    suspend fun delete(id: Long)

    @Query("UPDATE cash_flow_statement_item set deleted = 1 where id = :id")
    suspend fun deleteItem(id: Long)
}
