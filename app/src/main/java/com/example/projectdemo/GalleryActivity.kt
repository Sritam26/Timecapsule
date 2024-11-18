package com.example.projectdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GalleryActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var galleryAdapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recycler_view)

        // Set up RecyclerView with GridLayoutManager
        recyclerView.layoutManager = GridLayoutManager(this, 3) // 3 columns for the gallery
        recyclerView.setHasFixedSize(true)

        // Fetch images and set the adapter
        val imagePaths = dbHelper.getImages()
        galleryAdapter = GalleryAdapter(imagePaths)
        recyclerView.adapter = galleryAdapter
    }
}
