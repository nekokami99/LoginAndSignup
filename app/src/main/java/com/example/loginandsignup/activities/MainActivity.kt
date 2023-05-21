package com.example.loginandsignup.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import com.example.loginandsignup.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth=Firebase.auth

        Handler(Looper.getMainLooper()).postDelayed({

            val user = auth.currentUser
            if(user != null){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                //destroy current activity
                finish()
            }else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                //destroy current activity
                finish()
            }
        },3000)
    }



}