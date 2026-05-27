/*
 */

package com.gtoretti.buffet.data.balanceSheet

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object.
 */
@Dao
interface BalanceSheetDao {

    @Query("SELECT * FROM balance_sheet where deleted = 0 ORDER BY name")
    fun getActiveBalanceSheets(): Flow<List<BalanceSheet>>

    @Upsert
    suspend fun upsert(balanceSheet: BalanceSheet)

    @Query("UPDATE balance_sheet set deleted = 1 where id = :id")
    suspend fun delete(id: Long)

}
