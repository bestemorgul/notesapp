package com.bestemorgul.notesapp

import android.app.Application
import com.bestemorgul.notesapp.database.NotesDatabase

class NotesApplication : Application() {
    val database: NotesDatabase by lazy { NotesDatabase.getDatabase(this) }

}
