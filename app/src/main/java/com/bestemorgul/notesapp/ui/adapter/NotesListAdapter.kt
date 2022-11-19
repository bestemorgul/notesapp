package com.bestemorgul.notesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bestemorgul.notesapp.databinding.NotesListBinding
import com.bestemorgul.notesapp.model.Notes

class NotesListAdapter (private val clickListener: (Notes) -> Unit
) : ListAdapter<Notes, NotesListAdapter.NotesViewHolder>(DiffCallback) {

    class NotesViewHolder(
        private var binding: NotesListBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(notes: Notes) {
            binding.notesTitle.text = notes.noteTitle
            binding.notesBody.text = notes.noteBody
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Notes>() {
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NotesViewHolder(
            NotesListBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val notes = getItem(position)
        holder.itemView.setOnClickListener{
            clickListener(notes)
        }
        holder.bind(notes)
    }
}

