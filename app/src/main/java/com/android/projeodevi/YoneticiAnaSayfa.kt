package com.android.projeodevi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import com.android.projeodevi.databinding.ActivityYoneticiAnaSayfaBinding
import com.android.projeodevi.databinding.AnasayfaEkraniBinding
import kotlinx.android.synthetic.main.activity_yonetici_ana_sayfa.*
import kotlinx.android.synthetic.main.anasayfa_ekrani.*
import kotlinx.android.synthetic.main.navigation_drawer.*

class YoneticiAnaSayfa : AppCompatActivity() {

    lateinit var binding: ActivityYoneticiAnaSayfaBinding
    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yonetici_ana_sayfa)

        binding = ActivityYoneticiAnaSayfaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            toggle = ActionBarDrawerToggle(this@YoneticiAnaSayfa, drawerLayoutYonetici, anasayfaToolBar, R.string.open, R.string.close)
            drawerLayoutYonetici.addDrawerListener(toggle)
            toggle.syncState()
            toggle.drawerArrowDrawable.color = ContextCompat.getColor(this@YoneticiAnaSayfa, R.color.white)
            setSupportActionBar(anasayfaToolBar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        yntDersEkleButonu.setOnClickListener {
            val dersEkleIntent = Intent(applicationContext,DersEkleActivity::class.java)
            startActivity(dersEkleIntent)
        }
        onaylacakOgrenciler.setOnClickListener {
            val onaylamaSayfasiIntent = Intent(applicationContext,UserlistActivity::class.java)
            startActivity(onaylamaSayfasiIntent)
        }
        duyuruEklemeSayfasi.setOnClickListener {
            val duyuruSayfasiIntent = Intent(applicationContext,DuyuruEkleActivity::class.java)
            startActivity(duyuruSayfasiIntent)
        }
        gununYemeginiGir.setOnClickListener {
            val ActivityYoneticiYemekGirIntent = Intent(applicationContext,ActivityYoneticiYemekGir::class.java)
            startActivity(ActivityYoneticiYemekGirIntent)
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }
}