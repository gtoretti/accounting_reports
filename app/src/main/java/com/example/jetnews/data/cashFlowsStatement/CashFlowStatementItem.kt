/*
 */

package com.example.jetnews.data.cashFlowsStatement

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "cash_flow_statement_item")
data class CashFlowStatementItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val cashFlowStatementItemId: Long,
    val cashFlowStatementId: Long,
    val description: String,
    val explanatoryNoteIndex: String,
    val explanatoryNoteId: Long,
    val type: String,
    val value: Double,
    val isCredit: Boolean,
    val date: Date,
    val deleted: Int,
    ) {

    override fun toString() = description
}
