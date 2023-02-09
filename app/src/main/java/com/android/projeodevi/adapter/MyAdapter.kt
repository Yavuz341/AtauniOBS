package com.android.projeodevi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.projeodevi.OnaylanmisOgrenciVerileriGirActivity
import com.android.projeodevi.R
import com.android.projeodevi.model.User


class MyAdapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {


        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent,false)
        return MyViewHolder(itemView)




    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]
        val context = holder.itemView.context
        val verilerIntent = Intent(context,OnaylanmisOgrenciVerileriGirActivity::class.java)
        holder.firstName.text = currentitem.firstName




        holder.onayButonu.setOnClickListener {

            val item = userList[position]
            val tcNo = holder.firstName.text.toString()


            verilerIntent.putExtra("gonderTC",tcNo)
            context.startActivity(verilerIntent)

        }


    }

    override fun getItemCount(): Int {
        return userList.size

    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val firstName : TextView = itemView.findViewById(R.id.tvfirstName)
        val onayButonu : Button = itemView.findViewById(R.id.onaylamaButonu)



    }
}