/*
 */

package com.example.jetnews.data.equityChangesStatement

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.jetnews.data.periodResultStatement.PeriodResultStatement
import com.example.jetnews.data.periodResultStatement.PeriodResultStatementItem
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object.
 */
@Dao
interface EquityChangesStatementDao {

    @Query("SELECT * FROM equity_changes_statement where deleted = 0 ORDER BY name")
    fun getActiveEquityChangesStatements(): Flow<List<EquityChangesStatement>>

    @Upsert
    suspend fun upsert(equityChangesStatement: EquityChangesStatement)

    @Query("UPDATE equity_changes_statement set deleted = 1 where id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM equity_changes_statement_item where equityChangesStatementId = :id and deleted = 0")
    fun getEquityChangesStatementItems(id: Long): Flow<List<EquityChangesStatementItem>>

    @Upsert
    suspend fun upsert(equityChangesStatementItem: EquityChangesStatementItem)

    @Query("UPDATE equity_changes_statement_item set deleted = 1 where id = :id")
    suspend fun deleteItem(id: Long)

}
