package com.hayohtee.notes.data.dao

import androidx.room.*
import com.hayohtee.notes.data.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note_table WHERE id = (:noteId)")
    fun getNote(noteId: Long): Flow<Note>

    @Query("SELECT * FROM note_table")
    fun getNotes(): Flow<List<Note>>
}