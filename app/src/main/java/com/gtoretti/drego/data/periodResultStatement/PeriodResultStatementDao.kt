/*
 */

package com.gtoretti.drego.data.periodResultStatement

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.gtoretti.drego.data.cashFlowsStatement.CashFlowStatement
import com.gtoretti.drego.data.cashFlowsStatement.CashFlowStatementItem
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object.
 */
@Dao
interface PeriodResultStatementDao {

    @Query("SELECT * FROM period_result_statement where deleted = 0 ORDER BY name")
    fun getActivePeriodResultStatements(): Flow<List<PeriodResultStatement>>

    @Upsert
    suspend fun upsert(periodResultStatement: PeriodResultStatement)

    @Query("UPDATE period_result_statement set deleted = 1 where id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM period_result_statement_item where periodResultStatementId = :id and deleted = 0")
    fun getPeriodResultStatementItems(id: Long): Flow<List<PeriodResultStatementItem>>

    @Upsert
    suspend fun upsert(periodResultStatementItem: PeriodResultStatementItem)

    @Query("UPDATE period_result_statement_item set deleted = 1 where id = :id")
    suspend fun deleteItem(id: Long)
}
