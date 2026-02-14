/*
 */

package com.gtoretti.drego.data.accountingAccounts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "accounting_account")
data class AccountingAccount(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val accountingAccountId: Long,
    val type: String,
    val level1: String,
    val level2: String,
    val level3: String,
    val level4: String,
    val description: String,
    val deleted: Int,
    ) {
    override fun toString() = accountingAccountId.toString()
}

//https://www.planalto.gov.br/ccivil_03/leis/l6404compilada.htm