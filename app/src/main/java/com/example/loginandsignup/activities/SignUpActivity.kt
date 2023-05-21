package com.example.loginandsignup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.loginandsignup.models.ProfileModel
import com.example.loginandsignup.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        dbRef = FirebaseDatabase.getInstance().getReference("Profile")

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmailSignUp.text.toString()
            val pass = binding.etPasswordSignUp.text.toString()
            val fullname = binding.etFullnameSignUp.text.toString()
            val age = binding.etAgeSignUp.text.toString()
            val gender = binding.etGenderSignUp.text.toString()
            val pn = binding.etPNSignUp.text.toString()

            if(checkAllField()){
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                    if(it.isSuccessful){
                        val newProfile = auth.currentUser

                        if(newProfile != null){
                            val profile = ProfileModel(fullname,age,gender,pn,newProfile.uid)
                            dbRef.child(newProfile.uid).setValue(profile).addOnCompleteListener{
                                Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                    else {
                        Log.e("Created failed", it.exception.toString())
                    }
                }

            }
        }

        binding.btnCancel.setOnClickListener {
            auth.signOut()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkAllField(): Boolean{
        val email = binding.etEmailSignUp.text.toString()
        val pass = binding.etPasswordSignUp.text.toString()
        val passConfirm = binding.etPasswordConfirmSignUp.text.toString()
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

        if(passConfirm == ""){
            Toast.makeText(this, "Confirm password doesn't correct", Toast.LENGTH_SHORT).show()
            return false
        }

        if(pass != passConfirm){
            Toast.makeText(this, "Confirm password doesn't correct", Toast.LENGTH_SHORT).show()
            return false
        }

        if(pass.length <=6){
            Toast.makeText(this, "Password not long enough", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}