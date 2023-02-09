package com.android.projeodevi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.projeodevi.databinding.ActivityOnaylanmisOgrenciVerileriGirBinding
import com.android.projeodevi.databinding.FragmentBilgilerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_onaylanmis_ogrenci_verileri_gir.*
import kotlinx.android.synthetic.main.fragment_bilgiler.*

class OnaylanmisOgrenciVerileriGirActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnaylanmisOgrenciVerileriGirBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onaylanmis_ogrenci_verileri_gir)

        val intent = intent
        val alTC = intent.getStringExtra("gonderTC")

        binding = ActivityOnaylanmisOgrenciVerileriGirBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        val kullaniciID = firebaseAuth.currentUser?.uid.toString()


        val db = FirebaseFirestore.getInstance()
        db.collection("Öğrenciler").get().addOnCompleteListener{
            if(it.isSuccessful){

                for(document in it.result){

                    val firebaseTC = document.get("TC") as String
                    if(alTC == firebaseTC){
                        val fireBaseAdi = document.get("Adı") as String
                        val fireBaseSoyadi = document.get("Soyadı") as String
                        val fireBaseEmail = document.get("Email Adresi") as String
                        val fireBaseTC = document.get("TC") as String
                        val fireBaseTel = document.get("Telefon Numarası") as String

                        ogrenciVerileriAdKutu.text = fireBaseAdi
                        ogrenciVerileriSoyadıKutu.text = fireBaseSoyadi
                        ogrenciVerileriTcNoKutu.text = fireBaseTC
                        ogrenciVerileriTelNoKutu.text = fireBaseTel
                        ogrenciVerileriMailKutu.text = fireBaseEmail
                        break;
                    }

                }
            }
        }
        kaydiTamamla.setOnClickListener {
            db.collection("Öğrenciler").get().addOnCompleteListener{
                if(it.isSuccessful){
                    for(document in it.result){
                        val firebaseTC = document.get("TC") as String
                        if(alTC == firebaseTC){
                            val fireBaseAdi = document.get("Adı") as String
                            val fireBaseSoyadi = document.get("Soyadı") as String
                            val fireBaseEmail = document.get("Email Adresi") as String
                            val fireBaseTC = document.get("TC") as String
                            val fireBaseTel = document.get("Telefon Numarası") as String
                            val fireBaseID = document.get("UID") as String

                            val kullaniciBilgisi = hashMapOf(
                                "TC" to alTC,
                                "Adı" to fireBaseAdi,
                                "Soyadı" to fireBaseSoyadi,
                                "Telefon Numarası" to fireBaseTel,
                                "Email Adresi" to fireBaseEmail,
                                "Sifresi" to alTC,
                                "Onay Durumu" to "Onaylandı",
                                "UID" to fireBaseID,
                                "Okuduğu Bölüm" to yoneticiBilgilerBolumKutu.text.toString(),
                                "Statü" to yoneticiBilgilerStatuKutu.text.toString(),
                                "Öğrenci Numarası" to yoneticiBilgilerOgrenciKutu.text.toString(),
                                "Danışman Bilgisi" to yoneticiBilgilerDanismanKutu.text.toString(),
                                "Kayıt Tarihi" to yoneticiBilgilerKayitKutu.text.toString(),
                                "Akademik Dönem" to yoneticiBilgilerAkademikKutu.text.toString(),
                                "Mezuniyet Ortalaması" to yoneticiBilgilerMezuniyetOrtalamasıKutu.text.toString(),

                            )

                            db.collection("Öğrenciler").document(fireBaseID).set(kullaniciBilgisi).addOnSuccessListener {
                                Toast.makeText(this, "Bilgiler Güncellendi Ve Öğrenci Onayı Tamamlandı.", Toast.LENGTH_SHORT).show()
                                val yoneticiAnaSayfayaGonderIntent = Intent(applicationContext,YoneticiAnaSayfa::class.java)
                                startActivity(yoneticiAnaSayfayaGonderIntent)
                            }
                            break;
                        }

                    }
                }
            }

        }

    }
}