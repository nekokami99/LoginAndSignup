package com.example.loginandsignup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.loginandsignup.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailLogin.text.toString()
            val pass = binding.etPasswordLogin.text.toString()
            if(checkAllField()){
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if(it.isSuccessful){


                        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else{
                        Log.e("error", it.exception.toString())
                    }
                }
            }
        }

        binding.btnCreateAcc.setOnClickListener {

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnForgotPass.setOnClickListener {


            val intent = Intent(this, ForgotPassActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkAllField(): Boolean{
        val email = binding.etEmailLogin.text.toString()
        val pass = binding.etPasswordLogin.text.toString()

        if(email == ""){
            Toast.makeText(this, "Fill the email field", Toast.LENGTH_SHORT).show()
            return false
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Wrong email input", Toast.LENGTH_SHORT).show()
            return false
        }

        if(pass == ""){
            Toast.makeText(this, "Fill the password field", Toast.LENGTH_SHORT).show()
            return false
        }

        if(pass.length <=6){
            Toast.makeText(this, "Password not long enough", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}