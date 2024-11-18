package com.example.projectdemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VideoActivity : AppCompatActivity() {
    private val REQUEST_VIDEO_GALLERY = 200
    private val REQUEST_VIDEO_CAMERA = 201
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        dbHelper = DatabaseHelper(this)

        val galleryButton: Button = findViewById(R.id.button_gallery)
        val cameraButton: Button = findViewById(R.id.button_camera)

        galleryButton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, REQUEST_VIDEO_GALLERY)
        }

        cameraButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(cameraIntent, REQUEST_VIDEO_CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_VIDEO_GALLERY -> {
                    val selectedVideoUri: Uri? = data?.data
                    selectedVideoUri?.let {
                        // Save the selected video URI to the database
                        val result = dbHelper.insertVideo("Video", it.toString())
                        if (result) {
                            Toast.makeText(this, "Video saved successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Failed to save video", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                REQUEST_VIDEO_CAMERA -> {
                    val videoUri: Uri? = data?.data
                    videoUri?.let {
                        // Save the captured video URI to the database
                        val result = dbHelper.insertVideo("Camera Video", it.toString())
                        if (result) {
                            Toast.makeText(this, "Video saved successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Failed to save video", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
