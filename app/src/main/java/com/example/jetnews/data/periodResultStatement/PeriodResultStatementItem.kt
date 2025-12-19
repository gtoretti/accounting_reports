/*
 */

package com.example.jetnews.data.periodResultStatement

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "period_result_statement_item")
data class PeriodResultStatementItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val periodResultStatementItemId: Long,
    val periodResultStatementId: Long,
    val description: String,
    val explanatoryNoteIndex: String,
    val explanatoryNoteId: Long,
    val type: String,
    val value: Double,
    val deleted: Int,
    ) {

    override fun toString() = description
}
