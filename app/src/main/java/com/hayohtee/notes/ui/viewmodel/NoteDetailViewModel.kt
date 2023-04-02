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
import kotlinx.coroutines.launch

class NoteDetailViewModel(private val noteId: Long): ViewModel() {
    private val repository: NoteRepository = NoteRepository.getInstance()
    private val _note: MutableStateFlow<Note> = MutableStateFlow(Note())
    val note: StateFlow<Note> = _note.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNote(noteId).collect{
                _note.value = it
            }
        }
    }
}

class NoteDetailViewModelFactory(private val noteId: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteDetailViewModel(noteId) as T
        }

        throw IllegalArgumentException("Invalid ViewModel")
    }
}