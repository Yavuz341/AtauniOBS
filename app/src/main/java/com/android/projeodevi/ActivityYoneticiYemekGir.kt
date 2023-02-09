package com.android.projeodevi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_ders_ekle.*
import kotlinx.android.synthetic.main.activity_yonetici_yemek_gir.*

class ActivityYoneticiYemekGir : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yonetici_yemek_gir)

        val db = Firebase.firestore

        yemegiOnayla.setOnClickListener {
            val corba = corbaGir.text.toString()
            val anaYemek = anaYemekGir.text.toString()
            val pilav = pilavCesidiGir.text.toString()
            val tatli = tatliGir.text.toString()

            val dersEkle = hashMapOf(
                "Çorba" to corba,
                "Ana Yemek" to anaYemek,
                "Pilav" to pilav,
                "Tatlı" to tatli,

                )
            db.collection("Günün Yemeği").add(dersEkle)
                .addOnSuccessListener {
                    Toast.makeText(this, "Günün Yemeği Eklendi.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                        e -> Toast.makeText(this, "Günün Yemeği Eklenemedi.", Toast.LENGTH_SHORT).show()
                }
        }

    }
}