package com.example.projectdemo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NoteActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        dbHelper = DatabaseHelper(this)
        val noteEditText: EditText = findViewById(R.id.edit_text_note)
        val saveButton: Button = findViewById(R.id.button_save_note)

        saveButton.setOnClickListener {
            val noteText = noteEditText.text.toString()
            if (noteText.isNotEmpty()) {
                // Save the note to the database
                val result = dbHelper.insertNote(noteText)
                if (result) {
                    // Optionally show a success message
                    Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle the error if saving the note fails
                    Toast.makeText(this, "Failed to save the note", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Note cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
