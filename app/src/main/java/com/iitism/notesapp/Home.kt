package com.iitism.notesapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(context: Context) {

    val sharedPrefHelper = remember { SharedPrefHelper(context) }
    val notes = remember { mutableStateListOf<Note>() }
    val showDialog = remember { mutableStateOf(false) }
    val newNoteText = remember { mutableStateOf("") }
    val titleText = remember { mutableStateOf("") }
    val descriptionText = remember { mutableStateOf("") }
    val context = LocalContext.current

    val showDeleteDialog = remember { mutableStateOf(false) }
    val noteToDelete = remember { mutableStateOf<Pair<Int, Note>?>(null) }


    LaunchedEffect(Unit) {
        notes.clear()
        notes.addAll(sharedPrefHelper.getNotes())
    }

    Scaffold(
        topBar = { MyTitleBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog.value = true }, // Open dialog
                containerColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (notes.isEmpty()) {
                Text("No notes yet!", modifier = Modifier.padding(16.dp), fontSize = 18.sp)
            } else {
                LazyColumn {
                    items(notes.size) { index ->  // Use 'notes.size' correctly
                        val note = notes[index]  // Get note from list
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {

                                },
                            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                        ) {
                            Row {
                                Column (modifier = Modifier.weight(1f)){
                                    Text(
                                        note.title,
                                        modifier = Modifier.padding(10.dp),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        note.description,
                                        Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                        fontSize = 14.sp
                                    )

                                }
                                IconButton(
                                    onClick = {

                                        noteToDelete.value = Pair(index, note) // Store which note to delete
                                        showDeleteDialog.value = true // Show delete confirmation
//
//                                        sharedPrefHelper.deleteNote(index) // Delete from SharedPreferences
//                                        notes.removeAt(index) // Delete note
                                    }
                                ){
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Add Note") },
            text = {
                Column {
                    TextField(
                        value = titleText.value,
                        onValueChange = { titleText.value = it },
                        placeholder = { Text("Enter title") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = descriptionText.value,
                        onValueChange = { descriptionText.value = it },
                        placeholder = { Text("Enter description") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (titleText.value.isBlank() || descriptionText.value.isBlank()) {
                            Toast.makeText(context, "Title and description cannot be empty!", Toast.LENGTH_SHORT).show()
                        } else {
                            val newNote = Note(titleText.value, descriptionText.value)
                            notes.add(newNote)
                            sharedPrefHelper.saveNotes(notes) // Save after adding
                            titleText.value = ""
                            descriptionText.value = ""
                            showDialog.value = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = false },
            title = { Text("Delete Note") },
            text = { Text("Are you sure you want to delete this note?") },
            confirmButton = {
                Button(
                    onClick = {
                        noteToDelete.value?.let { (index, note) ->
                            sharedPrefHelper.deleteNote(index) // Delete from SharedPreferences
                            notes.removeAt(index)
                            Toast.makeText(context, "Deleted: ${note.title}", Toast.LENGTH_SHORT).show()
                        }
                        showDeleteDialog.value = false
                    }
                ) {

                    Text("Delete", color = Color.Red)

                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTitleBar() {
    TopAppBar(
        title = { Text("Notes APP", color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue, // Set background color to blue
            titleContentColor = Color.White // Ensure title text is visible
        )
    )
}
