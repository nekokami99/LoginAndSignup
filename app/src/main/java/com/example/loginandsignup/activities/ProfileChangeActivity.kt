package com.example.loginandsignup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginandsignup.models.ProfileModel
import com.example.loginandsignup.databinding.ActivityProfileChangeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileChangeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var binding: ActivityProfileChangeBinding
    private var profile: ProfileModel?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        dbRef = Firebase.database.reference

        val curUser = auth.currentUser
        if(curUser != null) {
            dbRef.child("Profile").child(curUser.uid).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    profile = snapshot.getValue(ProfileModel::class.java)
                    profile?.let {
                        binding.etFullname.setText(profile!!.fullname.toString())
                        binding.etAge.setText(profile!!.age.toString())
                        binding.etGender.setText(profile!!.gender.toString())
                        binding.etPN.setText(profile!!.pn.toString())
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        binding.btnCancelChangeProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnConfirmChangeProfile.setOnClickListener {
            updateProfile()
            Toast.makeText(this, "Update successfully",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateProfile() {
        val curUser = auth.currentUser
        if (curUser != null) {
            profile = ProfileModel(binding.etFullname.text.toString(),binding.etAge.text.toString(),binding.etGender.text.toString(),binding.etPN.text.toString(),curUser.uid)
        }
        auth.currentUser?.let { it1 ->
            dbRef.child("Profile").child(it1.uid).setValue(profile)

        }
    }
}