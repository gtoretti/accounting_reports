/*
 */

package com.example.jetnews.data.cashFlowsStatement

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "cash_flow_statement")
data class CashFlowStatement(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val cashFlowStatementId: Long,
    val name: String,
    val startDate: Date,
    val endDate: Date,
    val initialCashBalance: Double,
    val initialCashBalanceIsCredit: Boolean,
    val deleted: Int,
    ) {

    override fun toString() = name
}
