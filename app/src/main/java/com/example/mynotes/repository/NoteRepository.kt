package com.example.mynotes.repository

import com.example.mynotes.db.NoteDatabase
import com.example.mynotes.models.Note

class NoteRepository(private val db: NoteDatabase) {


    suspend fun addNote(note: Note) = db.getNoteDao().addNote(note)

    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)

    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)

    fun getAllNotes() = db.getNoteDao().getAllNotes()

    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)





}