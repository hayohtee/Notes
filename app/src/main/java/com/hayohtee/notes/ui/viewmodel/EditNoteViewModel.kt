package com.hayohtee.notes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hayohtee.notes.data.NoteRepository
import com.hayohtee.notes.data.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditNoteViewModel(private val noteId: Long) : ViewModel() {
    private val repository: NoteRepository = NoteRepository.getInstance()
    private val _note: MutableStateFlow<Note> = MutableStateFlow(Note())
    val note: StateFlow<Note> = _note.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNote(noteId).collect {
                _note.value = it
            }
        }
    }

    fun updateNote(onUpdateNote: (Note) -> Note) {
        _note.update { oldNote ->
            onUpdateNote(oldNote)
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.updateNote(_note.value)
    }
}

class EditNoteViewModelFactory(private val noteId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditNoteViewModel(noteId) as T
        }

        throw IllegalArgumentException("Invalid ViewModel")
    }
}