/*
 */

package com.example.jetnews.ui.accountingAccounts


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnews.data.accountingAccounts.AccountingAccount
import com.example.jetnews.data.accountingAccounts.AccountingAccountRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountingAccountsViewModel @Inject internal constructor(
    private val repository: AccountingAccountRepository
) : ViewModel() {

    fun getAccountingAccounts() = repository.getAccountingAccounts()

    fun getAccountingAccounts(level1: String) = repository.getAccountingAccounts(level1)

    fun getAccountingAccounts(level1: String,level2: String) = repository.getAccountingAccounts(level1,level2)

    fun getAccountingAccounts(level1: String,level2: String,level3: String) = repository.getAccountingAccounts(level1,level2,level3)

    fun getAccountingAccount(id:Long) = repository.getAccountingAccount(id)

    fun getTypeResultAccountingAccounts() = repository.getTypeResultAccountingAccounts()

    fun saveAccountingAccount(accountingAccount: AccountingAccount) {
        viewModelScope.launch {
            repository.saveAccountingAccount(accountingAccount)
        }
    }

    fun deleteAccountingAccount(accountingAccount: AccountingAccount) {
        viewModelScope.launch {
            repository.deleteAccountingAccount(accountingAccount)
        }
    }
}