package com.hayohtee.notes.data

import android.content.Context
import com.hayohtee.notes.data.model.Note
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class NoteRepository @OptIn(DelicateCoroutinesApi::class)
private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {
    private val database: NoteDatabase = NoteDatabase.getInstance(context)

    fun addNote(note: Note) {
        coroutineScope.launch(Dispatchers.IO) {
            database.noteDao().insert(note)
        }
    }

    fun getNotes(): Flow<List<Note>> {
        return database.noteDao().getNotes()
    }

    fun getNote(noteId: Long): Flow<Note> {
        return database.noteDao().getNote(noteId)
    }

    suspend fun deleteNote(note: Note) {
        database.noteDao().delete(note)
    }

    fun updateNote(note: Note) {
        coroutineScope.launch(Dispatchers.IO) {
            database.noteDao().update(note)
        }
    }

    companion object {
        private var INSTANCE: NoteRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = NoteRepository(context)
            }
        }

        fun getInstance(): NoteRepository {
            return INSTANCE ?: throw IllegalStateException("NoteRepository must be initialized")
        }
    }
}