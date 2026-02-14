/*
 */

package com.gtoretti.drego.data.explanatoryNotes

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExplanatoryNoteRepository @Inject constructor(private val explanatoryNoteDao: ExplanatoryNoteDao) {

    fun getExplanatoryNotes() = explanatoryNoteDao.getActiveExplanatoryNotes()

    fun getExplanatoryNote(id: Long) = explanatoryNoteDao.getExplanatoryNote(id)

    suspend fun saveExplanatoryNotes(explanatoryNote: ExplanatoryNote){
        explanatoryNoteDao.upsert(explanatoryNote)
    }

    suspend fun deleteExplanatoryNote(explanatoryNote: ExplanatoryNote){
        explanatoryNoteDao.delete(explanatoryNote.explanatoryNoteId)
    }

}
