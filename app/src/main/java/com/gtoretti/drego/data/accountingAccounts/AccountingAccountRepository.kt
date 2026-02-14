/*
 */

package com.gtoretti.drego.data.accountingAccounts

import com.gtoretti.drego.utils.ACCOUNTING_ACCOUNTS_TYPE_RESULT
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountingAccountRepository @Inject constructor(private val accountingAccountDao: AccountingAccountDao) {

    fun getAccountingAccounts() = accountingAccountDao.getAccountingAccounts()

    fun getAccountingAccounts(level1: String) = accountingAccountDao.getAccountingAccounts(level1)

    fun getAccountingAccounts(level1: String,level2: String) = accountingAccountDao.getAccountingAccounts(level1,level2)

    fun getAccountingAccounts(level1: String,level2: String,level3: String) = accountingAccountDao.getAccountingAccounts(level1,level2,level3)

    fun getAccountingAccount(id: Long) = accountingAccountDao.getAccountingAccount(id)

    fun getTypeResultAccountingAccounts() = accountingAccountDao.getAccountingAccountsByType(ACCOUNTING_ACCOUNTS_TYPE_RESULT)

    suspend fun saveAccountingAccount(accountingAccount: AccountingAccount){
        accountingAccountDao.upsert(accountingAccount)
    }

    suspend fun deleteAccountingAccount(accountingAccount: AccountingAccount){
        accountingAccountDao.delete(accountingAccount.accountingAccountId)
    }

}
