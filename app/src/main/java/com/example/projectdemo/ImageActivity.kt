package com.example.projectdemo

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_GALLERY = 100
    private val REQUEST_IMAGE_CAMERA = 101
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        dbHelper = DatabaseHelper(this)

        val galleryButton: Button = findViewById(R.id.button_gallery)
        val cameraButton: Button = findViewById(R.id.button_camera)

        galleryButton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY)
        }

        cameraButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_GALLERY -> {
                    val selectedImageUri: Uri? = data?.data
                    selectedImageUri?.let {
                        // Save the image path to the database
                        saveImageToDatabase(it)
                    }
                }
                REQUEST_IMAGE_CAMERA -> {
                    val photo: Bitmap? = data?.extras?.get("data") as Bitmap?
                    photo?.let {
                        // Save the photo to the device storage and get the path
                        val savedImagePath = saveImageToStorage(it)
                        // Save the image path to the database
                        saveImageToDatabase(Uri.parse(savedImagePath))
                    }
                }
            }
        }
    }

    // Save image to device storage and return the file path
    private fun saveImageToStorage(bitmap: Bitmap): String {
        val directory = File(filesDir, "images")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val imageFile = File(directory, "image_${System.currentTimeMillis()}.jpg")
        try {
            val fos = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            return imageFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    // Save image path to the database
    private fun saveImageToDatabase(imageUri: Uri) {
        val imagePath = imageUri.path // This will give you the path of the image
        val imageName = imageUri.lastPathSegment // You can use this for a name or any other naming convention
        if (imagePath != null && imageName != null) {
            val isInserted = dbHelper.insertImage(imageName, imagePath)
            if (isInserted) {
                Toast.makeText(this,"Successfullyadded",Toast.LENGTH_SHORT).show()
            } else {
                // Show error message
                Toast.makeText(this,"notadded",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
