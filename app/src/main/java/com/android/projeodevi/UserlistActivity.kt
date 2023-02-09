package com.android.projeodevi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.projeodevi.adapter.MyAdapter
import com.android.projeodevi.model.User
import com.google.firebase.firestore.FirebaseFirestore

class UserlistActivity : AppCompatActivity() {

    private lateinit var dbref : FirebaseFirestore
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlist)

        userRecyclerView = findViewById(R.id.userList)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<User>()
        getUserData()

    }

    private fun getUserData() {

        dbref = FirebaseFirestore.getInstance()

        dbref.collection("Öğrenciler").get().addOnSuccessListener { result ->

            for(document in result){
                val onayDurumu = document.get("Onay Durumu") as String
                if(onayDurumu == "Onaylanmadı") {
                    val user = document.get("TC") as String
                    val users = User(user)
                    userArrayList.add(users)
                }
            }
            userRecyclerView.adapter = MyAdapter(userArrayList)
        }

    }

}