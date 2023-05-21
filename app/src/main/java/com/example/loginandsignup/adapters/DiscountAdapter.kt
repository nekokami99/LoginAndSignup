package com.example.loginandsignup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandsignup.models.DiscountModel
import com.example.loginandsignup.R

class DiscountAdapter (private val discountList: ArrayList<DiscountModel>) :
    RecyclerView.Adapter<DiscountAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_discount,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curDiscount = discountList[position]
        holder.tvDiscountName.text = curDiscount.discountName
        holder.tvDiscountPercent.text = curDiscount.discountPercent
        holder.tvDiscountPeriod.text = curDiscount.discountPeriod
    }

    override fun getItemCount(): Int {
        return discountList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvDiscountName : TextView = itemView.findViewById(R.id.tvDiscountName)
        val tvDiscountPercent : TextView = itemView.findViewById(R.id.tvDiscountPercent)
        val tvDiscountPeriod : TextView = itemView.findViewById(R.id.tvDiscountPeriod)
    }
}