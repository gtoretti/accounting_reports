/*
 */

package com.gtoretti.drego.data.balanceSheet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "balance_sheet")
data class BalanceSheet(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val balanceSheetId: Long,
    val name: String,
    val date: Date,
    val deleted: Int,
    ) {

    override fun toString() = name
}
