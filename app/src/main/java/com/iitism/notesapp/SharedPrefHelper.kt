package com.iitism.notesapp

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("notes_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveNotes(notes: List<Note>) {
        val jsonString = gson.toJson(notes)
        sharedPreferences.edit().putString("notes_list", jsonString).apply()
    }

    fun getNotes(): List<Note> {
        val jsonString = sharedPreferences.getString("notes_list", null)
        return if (jsonString != null) {
            val type = object : TypeToken<List<Note>>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            emptyList()
        }
    }

    fun deleteNote(index: Int) {
        val notes = getNotes().toMutableList()
        if (index in notes.indices) {
            notes.removeAt(index)
            saveNotes(notes) // Save the updated list after deletion
        }
    }

}
