package com.example.projectdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DatabaseHelper(this)

        emailEditText = findViewById(R.id.aa)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.button2)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                val validUser = dbHelper.checkUser(email, password)
                if (validUser) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    // Start a new activity or navigate to the main screen
                    val intent=Intent(this,loadingspalsh::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
