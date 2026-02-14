/*
 */

package com.gtoretti.drego.data.accountingAccounts

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object.
 */
@Dao
interface AccountingAccountDao {

    @Query("SELECT * FROM accounting_account where deleted = 0")
    fun getAccountingAccounts(): Flow<List<AccountingAccount>>

    @Query("SELECT * FROM accounting_account where level1 = :level1 and deleted = 0")
    fun getAccountingAccounts(level1:String): Flow<List<AccountingAccount>>

    @Query("SELECT * FROM accounting_account where level1 = :level1 and level2 = :level2 and deleted = 0")
    fun getAccountingAccounts(level1:String,level2:String): Flow<List<AccountingAccount>>

    @Query("SELECT * FROM accounting_account where level1 = :level1 and level2 = :level2 and level3 = :level3 and deleted = 0")
    fun getAccountingAccounts(level1:String,level2:String,level3:String): Flow<List<AccountingAccount>>

    @Query("SELECT * FROM accounting_account where id = :id")
    fun getAccountingAccount(id: Long): Flow<AccountingAccount>

    @Query("SELECT * FROM accounting_account where type = :type and deleted = 0")
    fun getAccountingAccountsByType(type: String): Flow<List<AccountingAccount>>


    @Upsert
    suspend fun upsert(accountingAccount: AccountingAccount)

    @Query("UPDATE accounting_account set deleted = 1 where id = :id")
    suspend fun delete(id: Long)
}
