package com.example.loginandsignup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandsignup.models.DoctorModel
import com.example.loginandsignup.R

class DoctorAdapter (private val doctorList: ArrayList<DoctorModel>) :
    RecyclerView.Adapter<DoctorAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curDoctor = doctorList[position]
        holder.tvDoctorName.text = curDoctor.docName
        holder.tvDoctorDegree.text = curDoctor.docDegree
        holder.tvDoctorAge.text = curDoctor.docAge
        holder.tvDocSpec.text = curDoctor.docSpe
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvDoctorName : TextView = itemView.findViewById(R.id.tvDoctorName)
        val tvDoctorAge : TextView = itemView.findViewById(R.id.tvDoctorAge)
        val tvDoctorDegree : TextView = itemView.findViewById(R.id.tvDoctorDegree)
        val tvDocSpec : TextView = itemView.findViewById(R.id.tvDoctorSpec)
    }
}