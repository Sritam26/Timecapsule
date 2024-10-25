package com.example.projectdemo// Replace with your actual package name

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth

    private lateinit var emailinput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth=FirebaseAuth.getInstance()

        // Initialize views
        emailinput = findViewById(R.id.aa)
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
        val email = emailinput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

       auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
           task->
           if (task.isSuccessful){
               Toast.makeText(this,"login successfull",Toast.LENGTH_SHORT).show()
               var intent= Intent(this, loadingspalsh::class.java)
               startActivity(intent)
           }

           else{
               Toast.makeText(this,"login successfull",Toast.LENGTH_SHORT).show()

           }

       }
    }

    private fun resetFields() {
        emailinput.text?.clear()
        passwordInput.text?.clear()
    }



}
