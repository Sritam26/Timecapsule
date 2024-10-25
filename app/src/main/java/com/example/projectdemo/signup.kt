package com.example.projectdemo // Replace with your actual package name

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony.Mms.Intents
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class signup : AppCompatActivity() {
    //Declare firebase auth instance
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        //Initializing firebaseauth instance
        auth=FirebaseAuth.getInstance()

        // Find the views by their IDs
        val usernameEditText = findViewById<TextInputEditText>(R.id.username)
        val emailEditText = findViewById<TextInputEditText>(R.id.email)
        val passwordEditText = findViewById<TextInputEditText>(R.id.password)
        val registerButton = findViewById<Button>(R.id.register_button)

        // Set up the click listener for the register button
        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validate the input fields
            if (username.isEmpty()) {
                usernameEditText.error = "Username is required"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                emailEditText.error = "Email is required"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordEditText.error = "Password is required"
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                task->
                if (task.isSuccessful){
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(this, "Registration not Successful", Toast.LENGTH_SHORT).show()

                }
            }

            // Here you can add code to handle the registration process
        }
        var loginbtn:Button=findViewById(R.id.button4)
        loginbtn.setOnClickListener{
            var intent=Intent(this, login::class.java)
            startActivity(intent)
        }
    }
}
