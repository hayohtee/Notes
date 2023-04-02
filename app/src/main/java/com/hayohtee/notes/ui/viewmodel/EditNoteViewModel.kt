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
import java.util.*

class EditNoteViewModel(private val noteId: Long) : ViewModel() {
    private val repository: NoteRepository = NoteRepository.getInstance()
    private val _note: MutableStateFlow<Note> = MutableStateFlow(Note())
    val note: StateFlow<Note> = _note.asStateFlow()

    val title = MutableStateFlow("")
    val detail = MutableStateFlow("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNote(noteId).collect {
                _note.value = it
                title.value = it.title
                detail.value = it.note
            }
        }
    }


    override fun onCleared() {
        super.onCleared()

        var updatedNote = note.value.copy(title = title.value, note = detail.value)

        // If modified, update the date
        if (note.value.title != title.value || note.value.note != detail.value) {
            updatedNote = note.value.copy(title = title.value, note = detail.value, date = Date())
        }

        repository.updateNote(updatedNote)
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