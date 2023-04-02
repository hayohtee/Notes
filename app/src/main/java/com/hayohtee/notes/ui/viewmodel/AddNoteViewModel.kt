package com.hayohtee.notes.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.hayohtee.notes.data.NoteRepository
import com.hayohtee.notes.data.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddNoteViewModel : ViewModel() {
    private val repository: NoteRepository = NoteRepository.getInstance()
    private val _note: MutableStateFlow<Note> = MutableStateFlow(Note())

    val note: StateFlow<Note> = _note.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        note.value.let {
            if (it.title.isNotEmpty()) {
                repository.addNote(it)
            }
        }
    }

    fun updateNote(onUpdate: (Note) -> Note) {
        _note.update { oldNote ->
            onUpdate(oldNote)
        }
    }

}