package com.bestemorgul.notesapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bestemorgul.notesapp.data.NotesDao
import com.bestemorgul.notesapp.model.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotesViewModel (private val notesDao: NotesDao) : ViewModel() {

    val allNotes: LiveData<List<Notes>> = notesDao.getNotes().asLiveData()


    fun retrieveNotes(id: Int): LiveData<Notes> {
        return notesDao.getNote(id).asLiveData()
    }

    fun isValidEntry(noteTitle: String, noteBody: String): Boolean {
        return noteTitle.isNotBlank() && noteBody.isNotBlank()
    }

    fun addNotes(
        noteTitle: String,
        noteBody: String
    ) {
        val notes = Notes(
            noteBody = noteBody,
            noteTitle = noteTitle
        )

        viewModelScope.launch(Dispatchers.IO) {
            notesDao.insert(notes)

        }
    }

    fun updateNotes(id: Int,
                    noteTitle: String,
                    noteBody: String) {
        val notes = Notes(
            id= id,
            noteBody = noteBody,
            noteTitle = noteTitle
        )

        viewModelScope.launch(Dispatchers.IO) {
            notesDao.update(notes)
        }

    }

    fun deleteNotes(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            notesDao.delete(notes)
        }
    }
}