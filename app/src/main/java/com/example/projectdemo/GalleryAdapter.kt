package com.example.projectdemo

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.InputStream

class GalleryAdapter(private val imagePaths: List<String>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery_image, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val imagePath = imagePaths[position]

        // Create the URI from the image path (it might be a content URI or file URI)
        val uri = Uri.parse(imagePath)

        try {
            // Use the ContentResolver to open the input stream for the image
            val contentResolver: ContentResolver = holder.imageView.context.contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(uri)

            // Check if inputStream is null (image not found)
            if (inputStream != null) {
                // Decode the input stream into a Bitmap
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

                // Set the Bitmap into the ImageView
                holder.imageView.setImageBitmap(bitmap)

                // Close the input stream
                inputStream.close()
            } else {
                // If the image is not found, set the error image
                holder.imageView.setImageResource(R.drawable.myimg)
                Toast.makeText(holder.imageView.context, "Image not found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // If there is any error while fetching the image, set the error image
            holder.imageView.setImageResource(R.drawable.myimg)
            Toast.makeText(holder.imageView.context, "Error loading image", Toast.LENGTH_SHORT).show()
        }
    }



    override fun getItemCount(): Int {
        return imagePaths.size
    }
}
