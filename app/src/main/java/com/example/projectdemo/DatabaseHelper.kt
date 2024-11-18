package com.example.projectdemo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val DATABASE_VERSION = 1

        // Existing user table
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"

        // New tables for images, videos, and notes
        const val TABLE_IMAGES = "images"
        const val TABLE_VIDEOS = "videos"
        const val TABLE_NOTES = "notes"

        // Columns for images, videos, and notes
        const val COLUMN_NAME = "name"
        const val COLUMN_PATH = "path"  // Path for images and videos
        const val COLUMN_NOTE = "note"  // For text notes
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create table for users
        val createUsersTable = "CREATE TABLE $TABLE_USERS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_USERNAME TEXT, $COLUMN_EMAIL TEXT, $COLUMN_PASSWORD TEXT)"
        db?.execSQL(createUsersTable)

        // Create table for images
        val createImagesTable = "CREATE TABLE $TABLE_IMAGES ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_PATH TEXT)"
        db?.execSQL(createImagesTable)

        // Create table for videos
        val createVideosTable = "CREATE TABLE $TABLE_VIDEOS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_PATH TEXT)"
        db?.execSQL(createVideosTable)

        // Create table for notes
        val createNotesTable = "CREATE TABLE $TABLE_NOTES ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NOTE TEXT)"
        db?.execSQL(createNotesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Drop existing tables if they exist
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_IMAGES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_VIDEOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NOTES")
        // Recreate the tables after upgrading
        onCreate(db)
    }

    // User registration function with email check
    fun registerUser(username: String, email: String, password: String): Boolean {
        val db = writableDatabase
        // Check if the user with the same email already exists
        val cursor = db.query(TABLE_USERS, arrayOf(COLUMN_ID), "$COLUMN_EMAIL = ?", arrayOf(email), null, null, null)
        if (cursor.count > 0) {
            cursor.close()
            db.close()
            return false  // User already exists
        }

        // Register the new user
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
        }

        val result = db.insert(TABLE_USERS, null, values)
        cursor.close()
        db.close()
        return result != -1L
    }

    // Check if the user credentials are valid for login
    fun checkUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    // Insert image data into the database
    fun insertImage(name: String, path: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PATH, path)
        }

        val result = db.insert(TABLE_IMAGES, null, values)
        db.close()
        return result != -1L
    }

    // Insert video data into the database
    fun insertVideo(name: String, path: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PATH, path)
        }

        val result = db.insert(TABLE_VIDEOS, null, values)
        db.close()
        return result != -1L
    }

    // Insert note data into the database
    fun insertNote(note: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOTE, note)
        }

        val result = db.insert(TABLE_NOTES, null, values)
        db.close()
        return result != -1L
    }

    // Fetch all images from the database
    fun getImages(): List<String> {
        val images = mutableListOf<String>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_IMAGES"
        val cursor = db.rawQuery(query, null)

        val columnIndex = cursor.getColumnIndex(COLUMN_PATH)
        if (cursor.moveToFirst() && columnIndex != -1) {
            do {
                val path = cursor.getString(columnIndex)
                images.add(path)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return images
    }

    // Fetch all videos from the database
    fun getVideos(): List<String> {
        val videos = mutableListOf<String>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_VIDEOS"
        val cursor = db.rawQuery(query, null)

        val columnIndex = cursor.getColumnIndex(COLUMN_PATH)
        if (cursor.moveToFirst() && columnIndex != -1) {
            do {
                val path = cursor.getString(columnIndex)
                videos.add(path)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return videos
    }

    // Fetch all notes from the database
    fun getNotes(): List<String> {
        val notes = mutableListOf<String>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NOTES"
        val cursor = db.rawQuery(query, null)

        val columnIndex = cursor.getColumnIndex(COLUMN_NOTE)
        if (cursor.moveToFirst() && columnIndex != -1) {
            do {
                val note = cursor.getString(columnIndex)
                notes.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return notes
    }
}
