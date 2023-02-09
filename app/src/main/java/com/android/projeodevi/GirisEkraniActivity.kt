package com.android.projeodevi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.projeodevi.databinding.GirisEkraniBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.giris_ekrani.*


class GirisEkraniActivity : AppCompatActivity() {


    private lateinit var binding: GirisEkraniBinding
    private lateinit var firebaseAuth: FirebaseAuth




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.giris_ekrani)

        val db = FirebaseFirestore.getInstance()

        binding = GirisEkraniBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        println(firebaseAuth.currentUser?.uid.toString())

        binding.girisButonu.setOnClickListener {
            val girisTc=binding.girisTcNo.text.toString()
            val girisPassword=binding.girisSifreTxt.text.toString()

            if(girisTc == "111" && girisPassword == "admin"){
                val yoneticiAnasayfaIntent = Intent(applicationContext,YoneticiAnaSayfa::class.java)
                startActivity(yoneticiAnasayfaIntent)
            }

            db.collection("Öğrenciler")
                .get()
                .addOnCompleteListener{

                    if (it.isSuccessful){
                        for (document in it.result){
                            val fireBaseTc = document.get("TC") as String
                            val onayliMi = document.get("Onay Durumu") as String
                            if(girisTc.isNotEmpty() && girisPassword.isNotEmpty()) {
                                if (fireBaseTc == girisTc) {
                                    val fireBaseEmail = document.get("Email Adresi") as String
                                    val fireBasePassword = document.get("Sifresi") as String

                                    firebaseAuth.signInWithEmailAndPassword(fireBaseEmail, girisPassword)
                                        .addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                if (girisPassword == girisTc && girisPassword == fireBasePassword) {
                                                    if (onayliMi == "Onaylanmadı") { Toast.makeText(this, "Yöneticiden Onay Bekleyiniz.", Toast.LENGTH_SHORT).show()
                                                    } else { Toast.makeText(this, "İlk Girişde Şifrenizi Yenileyiniz", Toast.LENGTH_SHORT).show()
                                                        intent = Intent(applicationContext, SifreDegistirActivity::class.java)
                                                        startActivity(intent)
                                                    }
                                                }

                                                if (fireBasePassword == girisPassword && fireBaseTc != fireBasePassword) {
                                                    val intentGiris = Intent(applicationContext, AnasayfaActivity::class.java)
                                                    startActivity(intentGiris)
                                                }
                                            }
                                            else {
                                                Toast.makeText(this, "T.C. niz veya şifreniz yanlış", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    break
                                }
                            }
                            else{
                                Toast.makeText(this, "Giriş bilgilerinizi boş bırakmayınız.", Toast.LENGTH_SHORT).show()
                                break
                            }
                        }
                    }
                }
            }

        kayitOlBtn.setOnClickListener {
            val kayitOlIntent = Intent(this, KayitOlEkraniActivity::class.java)
            startActivity(kayitOlIntent)
        }

        sifremiUnuttum.setOnClickListener {
            val sifremiUnuttumIntent = Intent(this, SifremiUnuttumActivity::class.java)
            startActivity(sifremiUnuttumIntent)
        }
    }
}