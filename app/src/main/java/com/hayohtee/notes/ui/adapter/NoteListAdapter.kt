package com.hayohtee.notes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hayohtee.notes.R
import com.hayohtee.notes.data.model.Note
import com.hayohtee.notes.databinding.NoteListItemBinding
import com.hayohtee.notes.util.Util

class NoteListAdapter(private val onItemClick: (noteId: Long) -> Unit) :
    ListAdapter<Note, NoteViewHolder>(NoteDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteListItemBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position), position, onItemClick)
    }
}

class NoteViewHolder(private val binding: NoteListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Note, position: Int, onItemClick: (noteId: Long) -> Unit) {
        binding.apply {
            noteTitle.text = note.title
            noteDate.text = Util.dateToString(note.date)
        }
        binding.root.setOnClickListener {
            onItemClick(note.id)
        }

        setBackgroundColor(position)
        setSpan(note.title)
    }

    private fun setBackgroundColor(position: Int) {
        val context = binding.root.context
        binding.root.setCardBackgroundColor(
            ContextCompat.getColor(context, getColorId(position))
        )
    }

    private fun setSpan(title: String) {
        val layoutParams = StaggeredGridLayoutManager.LayoutParams(binding.root.layoutParams)

        if (title.length > 50) {
            layoutParams.isFullSpan = true
            binding.noteDate.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
        }

        layoutParams.setMargins(8)
        binding.root.layoutParams = layoutParams
    }

    companion object {
        private val colors = listOf(
            R.color.red_200, R.color.brown_200,
            R.color.green_200, R.color.teal_200, R.color.purple_200,
            R.color.orange_200, R.color.brown_500, R.color.gray_300
        )

        fun getColorId(position: Int): Int {
            return colors[position % colors.size]
        }
    }
}
