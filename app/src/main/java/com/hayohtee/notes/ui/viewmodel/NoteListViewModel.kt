package com.hayohtee.notes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hayohtee.notes.data.NoteRepository
import com.hayohtee.notes.data.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteListViewModel : ViewModel() {
    private val repository: NoteRepository = NoteRepository.getInstance()
    private val _notes: MutableStateFlow<List<Note>> = MutableStateFlow(emptyList())
    val notes: StateFlow<List<Note>>
        get() = _notes.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNotes().collect { values ->
                _notes.value = values
            }
        }
    }
}