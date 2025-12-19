/*
 */

package com.example.jetnews.data.explanatoryNotes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "explanatory_note")
data class ExplanatoryNote(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val explanatoryNoteId: Long,
    val index: String,
    val content: String,
    val deleted: Int,
    ) {

    override fun toString() = index
}
