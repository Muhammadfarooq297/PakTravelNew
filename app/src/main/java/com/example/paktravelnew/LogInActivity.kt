package com.example.paktravelnew

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.paktravelnew.Models.UserModel
import com.example.paktravelnew.databinding.ActivityLogInBinding
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private var userName: String?=null
    private lateinit var database: DatabaseReference

    private val binding:ActivityLogInBinding by lazy {
        ActivityLogInBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth= Firebase.auth
        // Initialize Firebase Database
        database= Firebase.database.reference

        binding.loginButton.setOnClickListener {
            email=binding.emailOrPhone.text.toString()
            password=binding.Password.text.toString()

            if(email.isBlank()||password.isBlank())
            {
                Toast.makeText(this, "Please fill all details.", Toast.LENGTH_SHORT).show()
            }
            else
            {
                createUserAccount(email,password)
            }
        }
        binding.donthaveButton.setOnClickListener {
            val intent= Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }


    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful)
            {
                val user=auth.currentUser
                Toast.makeText(this, "Login Successfully.", Toast.LENGTH_SHORT).show()
                updateUi(user)
            }
            else
            {
//                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
//                    if(task.isSuccessful)
//                    {
//                        val user=auth.currentUser
//                        Toast.makeText(this, "Create User & Login Successfully.", Toast.LENGTH_SHORT).show()
//                        saveUserData()
//                        updateUi(user)
//                    }
//                    else
//                    {
//                        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
//                    }
//
//                }
                Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun saveUserData() {
        email=binding.emailOrPhone.text.toString()
        password=binding.Password.text.toString()

        val user= UserModel(email,password)
        val userId=FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("users").child(it).setValue(user)
        }

    }


    // launcher for google sign in


    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}