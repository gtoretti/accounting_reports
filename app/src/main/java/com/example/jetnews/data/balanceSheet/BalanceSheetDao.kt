/*
 */

package com.example.jetnews.data.balanceSheet

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.jetnews.data.cashFlowsStatement.CashFlowStatement
import com.example.jetnews.data.cashFlowsStatement.CashFlowStatementItem
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object.
 */
@Dao
interface BalanceSheetDao {

    @Query("SELECT * FROM balance_sheet where deleted = 0 ORDER BY name")
    fun getActiveBalanceSheets(): Flow<List<BalanceSheet>>

    @Query("SELECT * FROM balance_sheet_item where balanceSheetId = :id and deleted = 0 ")
    fun getBalanceSheetItems(id: Long): Flow<List<BalanceSheetItem>>

    @Upsert
    suspend fun upsert(balanceSheet: BalanceSheet)

    @Upsert
    suspend fun upsert(balanceSheetItem: BalanceSheetItem)

    @Query("UPDATE balance_sheet set deleted = 1 where id = :id")
    suspend fun delete(id: Long)

    @Query("UPDATE balance_sheet_item set deleted = 1 where id = :id")
    suspend fun deleteItem(id: Long)
}
