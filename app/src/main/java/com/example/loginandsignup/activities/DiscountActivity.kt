package com.example.loginandsignup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandsignup.adapters.DiscountAdapter
import com.example.loginandsignup.models.DiscountModel
import com.example.loginandsignup.R
import com.example.loginandsignup.databinding.ActivityDiscountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class DiscountActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var discountRecyclerView: RecyclerView
    private lateinit var binding: ActivityDiscountBinding
    private lateinit var discountList: ArrayList<DiscountModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        binding = ActivityDiscountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        discountRecyclerView = findViewById(R.id.discountRecyclerView)
        discountRecyclerView.layoutManager = LinearLayoutManager(this)
       // discountRecyclerView.layoutManager = LinearLayoutManager(this)

        discountList = arrayListOf<DiscountModel>()

        getDiscountData()

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getDiscountData() {
        discountRecyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Discount")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                discountList.clear()
                if(snapshot.exists()){
                    for(serSnap in snapshot.children){
                        val discountData = serSnap.getValue(DiscountModel::class.java)
                        discountList.add(discountData!!)
                    }
                    val mAdapter = DiscountAdapter(discountList)
                    discountRecyclerView.adapter = mAdapter

                    discountRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}