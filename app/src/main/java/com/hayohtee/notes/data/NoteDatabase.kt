package com.hayohtee.notes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hayohtee.notes.data.dao.NoteDao
import com.hayohtee.notes.data.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(NoteTypeConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java, "note-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }

}