package com.hayohtee.notes.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hayohtee.notes.data.model.Note

class NoteDiffItemCallback: DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return (oldItem == newItem)
    }

}