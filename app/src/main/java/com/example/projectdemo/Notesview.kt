package com.example.projectdemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Notesview : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you're referencing the correct layout file
        setContentView(R.layout.activity_notesview)

        // Initialize database helper and RecyclerView
        databaseHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.notesRecyclerView)

        // Fetch notes from the database
        val notes = databaseHelper.getNotes()

        // Set up RecyclerView with GridLayoutManager
        val layoutManager = GridLayoutManager(this, 2) // Display 2 items per row
        recyclerView.layoutManager = layoutManager

        // Set up the adapter
        notesAdapter = NotesAdapter(notes)
        recyclerView.adapter = notesAdapter
    }
}
