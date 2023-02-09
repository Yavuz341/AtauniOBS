package com.android.projeodevi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.projeodevi.databinding.ActivitySifreDegistirBinding
import com.android.projeodevi.databinding.GirisEkraniBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sifre_degistir.*

class SifreDegistirActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySifreDegistirBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sifre_degistir)

        val db = FirebaseFirestore.getInstance()

        binding = ActivitySifreDegistirBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        sifreYenile.setOnClickListener {

            val yeniSifre = yeniSifreGir.text.toString()
            val yeniSifreTekrar = yeniSifreTekrarGir.text.toString()

            if((yeniSifre.isNotEmpty() && yeniSifreTekrar.isNotEmpty()) && yeniSifre == yeniSifreTekrar) {

                db.collection("Öğrenciler").get().addOnCompleteListener{
                    if(it.isSuccessful){
                        for(document in it.result){
                            val kullaniciID = document.get("UID") as String
                            val currentUser = FirebaseAuth.getInstance().currentUser
                            if(currentUser?.uid == kullaniciID){
                                println("İçerdeyim!!")
                                currentUser.updatePassword(yeniSifre).addOnCompleteListener{ task ->
                                    if(task.isSuccessful){
                                        println("Update Success")
                                    } else {
                                        println("Error Update")
                                    }

                                }
                                db.collection("Öğrenciler").document(kullaniciID).update("Sifresi",yeniSifre)
                                Toast.makeText(this, "Şifre Değiştirildi.", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this,GirisEkraniActivity::class.java)
                                startActivity(intent)
                                break

                            }
                        }
                    }
                    else{
                        Toast.makeText(this, "Şifre değiştirilemedi.", Toast.LENGTH_SHORT).show()
                    }
                }


            }
            else{
                Toast.makeText(this, "Şifreler aynı değil veya boş.", Toast.LENGTH_SHORT).show()
            }

        }

    }
}