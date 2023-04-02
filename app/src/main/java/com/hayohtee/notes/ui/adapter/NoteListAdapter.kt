package com.hayohtee.notes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hayohtee.notes.data.model.Note
import com.hayohtee.notes.databinding.NoteListItemBinding

class NoteListAdapter(private val onItemClick: (noteId: Long) -> Unit) :
    ListAdapter<Note, NoteViewHolder>(NoteDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteListItemBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}

class NoteViewHolder(private val binding: NoteListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Note, onItemClick: (noteId: Long) -> Unit) {
        binding.apply {
            noteTitle.text = note.title
            noteDate.text = note.date.toString()
        }
        binding.root.setOnClickListener {
            onItemClick(note.id)
        }
    }
}
