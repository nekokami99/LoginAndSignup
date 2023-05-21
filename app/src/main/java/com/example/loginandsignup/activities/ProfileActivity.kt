package com.example.loginandsignup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginandsignup.models.ProfileModel
import com.example.loginandsignup.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivityProfileBinding
    private lateinit var dbRef: DatabaseReference
    private var profileList: ProfileModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        getProfileData()

        binding.btnChangeProfile.setOnClickListener {
            val intent = Intent(this, ProfileChangeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnPasswordChange.setOnClickListener{
            val intent = Intent(this, PasswordChangeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnBack.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun getProfileData() {
        val curUser = auth.currentUser
        dbRef = Firebase.database.reference
        if(curUser != null){
            dbRef.child("Profile").child(curUser.uid).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    profileList = snapshot.getValue(ProfileModel::class.java)
                    profileList?.let {
                        binding.tvFullname.text = profileList!!.fullname.toString()
                        binding.tvAge.text = profileList!!.age.toString()
                        binding.tvGender.text = profileList!!.gender.toString()
                        binding.tvPN.text = profileList!!.pn.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

    }
}