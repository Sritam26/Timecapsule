package com.example.projectdemo// Replace with your actual package name

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class login : AppCompatActivity() {

    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        usernameInput = findViewById(R.id.aa)
        passwordInput = findViewById(R.id.password)
        loginButton = findViewById(R.id.button2)
        resetButton = findViewById(R.id.button3)

        // Set up login button click listener
        loginButton.setOnClickListener {
            performLogin()
        }

        // Set up reset button click listener
        resetButton.setOnClickListener {
            resetFields()
        }
    }

    private fun performLogin() {
        val username = usernameInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (username.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (username == "admin" && password == "admin") {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "username or password is incorrect ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetFields() {
        usernameInput.text?.clear()
        passwordInput.text?.clear()
    }
}
