package com.android.projeodevi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_ders_ekle.*

class DersEkleActivity : AppCompatActivity() {

    val db = Firebase.firestore
    val firestore = FirebaseFirestore.getInstance()
    private lateinit var firebaseAuth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_ders_ekle)

        dersEkleBtn.setOnClickListener {

                val dersKodu = dersKodu.text.toString()
                val dersAdi = dersAdi.text.toString()
                val kredi = kredi.text.toString()
                val ogretimElemani = ogretimElemani.text.toString()

                val dersEkle = hashMapOf(
                    "Ders Kodu" to dersKodu,
                    "Ders Adı" to dersAdi,
                    "Ders Kredisi" to kredi,
                    "Ögretim Elemanı" to ogretimElemani,

                )
                db.collection("Dersler").add(dersEkle)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Ders Eklendi", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener{
                        e -> Toast.makeText(this, "Ders Eklenemedi", Toast.LENGTH_SHORT).show()
                    }
        }
    }

}