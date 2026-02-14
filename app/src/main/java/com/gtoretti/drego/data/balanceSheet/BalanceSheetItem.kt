/*
 */

package com.gtoretti.drego.data.balanceSheet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "balance_sheet_item")
data class BalanceSheetItem(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val balanceSheetItemId: Long,
    val balanceSheetId: Long,
    val level1: String,
    val level2: String,
    val level3: String,
    val level4Description: String,
    val value: Double,
    val explanatoryNote: String,
    val deleted: Int,
    ) {
    override fun toString() = balanceSheetItemId.toString()
}
