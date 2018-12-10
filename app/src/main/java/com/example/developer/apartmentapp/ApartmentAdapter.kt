package com.example.developer.apartmentapp

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.developer.apartmentapp.entity.Apartment

class ApartmentAdapter(val items: List<Apartment> = mutableListOf()): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: ClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.apartment_list_item, p0, false)
        return ApartmentViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        val vh = viewHolder as ApartmentViewHolder
        vh.itemView.setOnClickListener {
            listener?.clicked(items[pos])
        }
        vh.apartmentNumberTextView.text = "Apartment No. ${items[pos].id}"
        vh.numberOfBedsTextView.text = "Number of Beds: ${items[pos].numberOfBeds}"
    }


    inner class ApartmentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val apartmentNumberTextView = itemView.findViewById<TextView>(R.id.tvApartmentNumber)
        val numberOfBedsTextView = itemView.findViewById<TextView>(R.id.tvApartmentBedsNumber)
    }

}

interface ClickListener {
    fun clicked(apartment: Apartment)
}