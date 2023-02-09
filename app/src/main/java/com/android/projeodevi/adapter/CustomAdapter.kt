package com.android.projeodevi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.projeodevi.R
import com.android.projeodevi.model.Dersler

class CustomAdapter(private val derslerList: ArrayList<Dersler>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemDersKodu:TextView = view.findViewById(R.id.itemDersKodu)
        val itemDersAdi:TextView = view.findViewById(R.id.itemDersAdi)
        val itemKrediler:TextView = view.findViewById(R.id.itemKrediler)
        val itemOgretimElemani:TextView = view.findViewById(R.id.itemOgretimElemani)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.dersler_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemDersKodu.text = derslerList[position].DersKodu
        viewHolder.itemDersAdi.text = derslerList[position].DersAdi
        viewHolder.itemKrediler.text = derslerList[position].Krediler
        viewHolder.itemOgretimElemani.text = derslerList[position].OgretimElemani
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = derslerList.size

}