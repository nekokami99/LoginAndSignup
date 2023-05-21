package com.example.loginandsignup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandsignup.R
import com.example.loginandsignup.models.ServiceModel

class ServiceAdapter(private val serviceList: ArrayList<ServiceModel>) :
    RecyclerView.Adapter<ServiceAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_service,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curService = serviceList[position]
        holder.tvServiceName.text = curService.serviceName
        holder.tvServiceDesc.text = curService.serviceDesc
        holder.tvServicePrice.text = curService.servicePrice
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvServiceName : TextView = itemView.findViewById(R.id.tvServiceName)
        val tvServiceDesc : TextView = itemView.findViewById(R.id.tvServiceDesc)
        val tvServicePrice : TextView = itemView.findViewById(R.id.tvServicePrice)
    }
}