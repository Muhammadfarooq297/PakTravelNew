package com.example.paktravelnew

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.paktravelnew.Models.UserModel
import com.example.paktravelnew.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var userName:String
    private lateinit var database: DatabaseReference
    private val binding:ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth= Firebase.auth
        // Initialize Firebase Database
        database= Firebase.database.reference



        binding.createButton.setOnClickListener {

            // get text from edittext
            userName=binding.name.text.toString().trim()
            email=binding.emailOrPhone.text.toString().trim()
            password=binding.Password.text.toString()

            if(userName.isBlank()||email.isBlank()||password.isBlank())
            {
                Toast.makeText(this, "Please fill all details.", Toast.LENGTH_SHORT).show()
            }
            else
            {
                createAccount(email,password)
            }

        }
        binding.alreadyaccountButton.setOnClickListener {
            val intent= Intent(this,LogInActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createAccount(email: String, password: String) {
        // Add checks for name and password
        userName = binding.name.text.toString().trim()
        if (userName.contains(Regex("\\d"))) {
            Toast.makeText(this, "Name should not include digits.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6 || !password.contains(Regex("[!@#\$%^&*(),.?\":{}|<>]"))) {
            Toast.makeText(this, "Password must be 6+ characters and include special characters.", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account created successfully.", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Account creation failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // saving data in to firebase
    private fun saveUserData() {
        // get text from edittext
        userName=binding.name.text.toString().trim()
        email=binding.emailOrPhone.text.toString().trim()
        password=binding.Password.text.toString()
        val user= UserModel(userName,email,password)
        val userId= FirebaseAuth.getInstance().currentUser!!.uid
        database.child("users").child(userId).setValue(user)
    }
}
