package com.bestemorgul.notesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bestemorgul.notesapp.data.NotesDao

class NotesViewModelFactory(private val notesDao: NotesDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(notesDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}