package com.bestemorgul.notesapp.data

import androidx.room.*
import com.bestemorgul.notesapp.model.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * from notes")
    fun getNotes(): Flow<List<Notes>>

    @Query("SELECT * from notes WHERE id = :id")
    fun getNote(id: Int) : Flow<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notes: Notes)

    @Update
    fun update(notes: Notes)

    @Delete
    fun delete(notes: Notes)
}