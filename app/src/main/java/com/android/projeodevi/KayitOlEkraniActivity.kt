package com.android.projeodevi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.projeodevi.databinding.KayitolEkraniBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.kayitol_ekrani.*

class KayitOlEkraniActivity : AppCompatActivity() {

    private lateinit var binding: KayitolEkraniBinding
    private lateinit var firebaseAuth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = KayitolEkraniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnKayitOl.setOnClickListener {
            val tcNo = binding.editTxtTcNo.text.toString()
            val ad = binding.editTxtAd.text.toString()
            val soyad = binding.editTxtSoyad.text.toString()
            val telefon = binding.editTextPhone.text.toString()
            val email = binding.editTxtEmail.text.toString()
            if (email.isNotEmpty() && ad.isNotEmpty() && tcNo.isNotEmpty() && soyad.isNotEmpty() && telefon.isNotEmpty()) {
                if(tcNo.length == 11 && telefon.length == 11) {
                    if (ogretimGorevlisiCBtn.isChecked) {
                        firebaseAuth.createUserWithEmailAndPassword(email, tcNo)
                            .addOnCompleteListener { kullaniciID ->
                                if (kullaniciID.isSuccessful) {
                                    val kullaniciBilgisi = hashMapOf(
                                        "TC" to tcNo,
                                        "Adı" to ad,
                                        "Soyadı" to soyad,
                                        "Telefon Numarası" to telefon,
                                        "Email Adresi" to email,
                                        "Sifresi" to tcNo,
                                        "Onay Durumu" to "Onaylanmadı",
                                        "UID" to kullaniciID.result.user?.uid
                                    )
                                    db.collection("Öğretmenler")
                                        .document(kullaniciID.result.user!!.uid)
                                        .set(kullaniciBilgisi)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                this,
                                                "Öğretmen Kaydı Başarılı",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            finish()
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                this,
                                                "Öğretmen Kaydı Başarısız",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                } else {
                                    Toast.makeText(
                                        this,
                                        "Öğretmen Kaydı Başarısız",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                            }

                    } else {
                        firebaseAuth.createUserWithEmailAndPassword(email, tcNo)
                            .addOnCompleteListener { kullaniciID ->
                                if (kullaniciID.isSuccessful) {
                                    val kullaniciBilgisi = hashMapOf(
                                        "TC" to tcNo,
                                        "Adı" to ad,
                                        "Soyadı" to soyad,
                                        "Telefon Numarası" to telefon,
                                        "Email Adresi" to email,
                                        "Sifresi" to tcNo,
                                        "Onay Durumu" to "Onaylanmadı",
                                        "UID" to kullaniciID.result.user?.uid
                                    )
                                    db.collection("Öğrenciler")
                                        .document(kullaniciID.result.user!!.uid)
                                        .set(kullaniciBilgisi)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                this,
                                                "Öğrenci Kaydı Başarılı",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            finish()
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                this,
                                                "Öğrenci Kaydı Başarısız",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                } else {
                                    Toast.makeText(
                                        this,
                                        "Öğrenci Kaydı Başarısız",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                }
                else{
                    Toast.makeText(this, "T.C. nizi veya Telefon numaranızı yanlış girdiniz.", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Lütfen bilgilerinizi boş bırakmayınız.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}