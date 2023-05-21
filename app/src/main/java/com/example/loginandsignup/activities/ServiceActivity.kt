package com.example.loginandsignup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandsignup.R
import com.example.loginandsignup.adapters.ServiceAdapter
import com.example.loginandsignup.models.ServiceModel
import com.example.loginandsignup.databinding.ActivityServiceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ServiceActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var serviceRecyclerView: RecyclerView
    private lateinit var binding: ActivityServiceBinding
    private lateinit var serviceList: ArrayList<ServiceModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serviceRecyclerView = findViewById(R.id.serviceRecyclerView)
        serviceRecyclerView.layoutManager = LinearLayoutManager(this)
       // serviceRecyclerView.layoutManager = LinearLayoutManager(this)

        serviceList = arrayListOf<ServiceModel>()

        getServiceData()

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getServiceData() {
        serviceRecyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Service")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                serviceList.clear()
                if(snapshot.exists()){
                    for(serSnap in snapshot.children){
                        val serviceData = serSnap.getValue(ServiceModel::class.java)
                        serviceList.add(serviceData!!)
                    }
                    val mAdapter = ServiceAdapter(serviceList)
                    serviceRecyclerView.adapter = mAdapter

                    serviceRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}