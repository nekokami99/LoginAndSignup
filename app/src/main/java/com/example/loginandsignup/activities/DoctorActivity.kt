package com.example.loginandsignup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandsignup.adapters.DoctorAdapter
import com.example.loginandsignup.models.DoctorModel
import com.example.loginandsignup.R
import com.example.loginandsignup.databinding.ActivityDoctorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class DoctorActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var doctorRecyclerView: RecyclerView
    private lateinit var binding: ActivityDoctorBinding
    private lateinit var doctorList: ArrayList<DoctorModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        binding = ActivityDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doctorRecyclerView = findViewById(R.id.doctorRecyclerView)
        doctorRecyclerView.layoutManager = LinearLayoutManager(this)
        // doctorRecyclerView.layoutManager = LinearLayoutManager(this)

        doctorList = arrayListOf<DoctorModel>()

        getDoctorData()

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getDoctorData() {
        doctorRecyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Doctor")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                doctorList.clear()
                if(snapshot.exists()){
                    for(serSnap in snapshot.children){
                        val doctorData = serSnap.getValue(DoctorModel::class.java)
                        doctorList.add(doctorData!!)
                    }
                    val mAdapter = DoctorAdapter(doctorList)
                    doctorRecyclerView.adapter = mAdapter

                    doctorRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}