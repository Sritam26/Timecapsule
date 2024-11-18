package com.example.projectdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val imageButton: Button = findViewById(R.id.button_add_image)
        val videoButton: Button = findViewById(R.id.button_add_video)
        val noteButton: Button = findViewById(R.id.button_add_note)
        val gallerybtn: Button = findViewById(R.id.gallery)
        val notesviewButton: Button = findViewById(R.id.btn_notesview) // New button

        imageButton.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            startActivity(intent)
        }

        videoButton.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }

        noteButton.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }

        gallerybtn.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
        }

        // Launch Notesview activity when the new button is clicked
        notesviewButton.setOnClickListener {
            val intent = Intent(this, Notesview::class.java)
            startActivity(intent)
        }
    }
}
