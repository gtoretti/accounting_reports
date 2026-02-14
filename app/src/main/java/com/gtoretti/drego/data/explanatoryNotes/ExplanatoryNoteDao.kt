/*
 */

package com.gtoretti.drego.data.explanatoryNotes

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object.
 */
@Dao
interface ExplanatoryNoteDao {

    @Query("SELECT * FROM explanatory_note where deleted = 0 ORDER BY id")
    fun getActiveExplanatoryNotes(): Flow<List<ExplanatoryNote>>

    @Query("SELECT * FROM explanatory_note where id = :id")
    fun getExplanatoryNote(id: Long): Flow<ExplanatoryNote>

    @Upsert
    suspend fun upsert(explanatoryNote: ExplanatoryNote)

    @Query("UPDATE explanatory_note set deleted = 1 where id = :id")
    suspend fun delete(id: Long)
}
