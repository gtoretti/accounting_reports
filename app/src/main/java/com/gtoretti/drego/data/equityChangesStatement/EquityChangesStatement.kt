/*
 */

package com.gtoretti.drego.data.equityChangesStatement

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "equity_changes_statement")
data class EquityChangesStatement(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val equityChangesStatementId: Long,
    val name: String,
    val startDate: Date,
    val endDate: Date,
    val deleted: Int,
    ) {

    override fun toString() = name
}
