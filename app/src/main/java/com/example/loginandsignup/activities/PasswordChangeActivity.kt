package com.example.loginandsignup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.loginandsignup.databinding.ActivityPasswordChangeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PasswordChangeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityPasswordChangeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityPasswordChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnCancelChangePass.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnConfirmChangePass.setOnClickListener {
            val user = auth.currentUser
            val pass = binding.etNewPass.text.toString()
            if(checkPassFiled()){
                user?.updatePassword(pass)?.addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this,"Change password success",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Log.e("error: ",it.exception.toString())
                    }
                }
            }
        }
    }
    private fun checkPassFiled(): Boolean{
        val pass = binding.etNewPass.text.toString()
        val passConfirm = binding.etConfirmPass.text.toString()
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