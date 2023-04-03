package com.hayohtee.notes.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.hayohtee.notes.data.NoteRepository
import com.hayohtee.notes.data.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class AddNoteViewModel : ViewModel() {
    private val repository: NoteRepository = NoteRepository.getInstance()

    val title = MutableStateFlow("")
    val detail = MutableStateFlow("")

    override fun onCleared() {
        super.onCleared()
        addNote()
    }

    private fun addNote() {
        if (title.value.isNotEmpty() || detail.value.isNotEmpty()) {
            val note = Note(
                title = title.value,
                note = detail.value, date = Date()
            )
            repository.addNote(note)
        }
    }
}