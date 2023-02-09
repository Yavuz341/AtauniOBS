package com.android.projeodevi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.projeodevi.R
import com.android.projeodevi.model.Duyurular


class DuyurularAdapter(private val duyuruList: ArrayList<Duyurular>) : RecyclerView.Adapter<DuyurularAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val DuyuruBaslikItem : TextView = view.findViewById(R.id.duyurularBaslik)
        val DuyuruDetayItem : TextView = view.findViewById(R.id.duyurularIcerik)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.duyuru_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.DuyuruBaslikItem.text = duyuruList[position].baslik
        viewHolder.DuyuruDetayItem.text = duyuruList[position].icerik
    }

    override fun getItemCount() = duyuruList.size

}