package com.android.projeodevi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_duyuru_ekle.*

class DuyuruEkleActivity : AppCompatActivity() {

    val db = Firebase.firestore
    val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_duyuru_ekle)

        duyuruyuEkleBtn.setOnClickListener {
            val duyuruBaslik = yoneticiBaslikGir.text.toString()
            val duyuruIcerik = yoneticiIcerikGir.text.toString()

            val duyuruEkle = hashMapOf(
                "Duyuru Başlık" to duyuruBaslik,
                "Duyuru İçerik" to duyuruIcerik
            )
            db.collection("Duyurular").add(duyuruEkle).addOnSuccessListener {
                Toast.makeText(this, "Duyuru başarıyla eklendi.", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener{
                    Toast.makeText(this, "Duyuru eklenemedi.", Toast.LENGTH_SHORT).show()
                }
        }

    }
}