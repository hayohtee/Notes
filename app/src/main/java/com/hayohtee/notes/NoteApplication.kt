package com.hayohtee.notes

import android.app.Application
import com.hayohtee.notes.data.NoteRepository

class NoteApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
    }
}