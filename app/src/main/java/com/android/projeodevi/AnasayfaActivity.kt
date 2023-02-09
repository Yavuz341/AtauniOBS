package com.android.projeodevi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.projeodevi.adapter.DuyurularAdapter
import com.android.projeodevi.databinding.AnasayfaEkraniBinding
import com.android.projeodevi.model.Dersler
import com.android.projeodevi.model.Duyurular
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.anasayfa_ekrani.*
import kotlinx.android.synthetic.main.navigation_drawer.*


class AnasayfaActivity : AppCompatActivity() {

    lateinit var binding: AnasayfaEkraniBinding
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var firebaseAuth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var db : FirebaseFirestore
    private lateinit var duyuruRecyclerView: RecyclerView
    private lateinit var duyuruArrayList: ArrayList<Duyurular>
    private lateinit var mDuyuruAdapter: DuyurularAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = AnasayfaEkraniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore
        duyuruArrayList = ArrayList<Duyurular>()
        duyuruRecyclerView = binding.duyurularRV
        duyuruRecyclerView.layoutManager = LinearLayoutManager(this)
        duyuruRecyclerView.setHasFixedSize(true)
        mDuyuruAdapter = DuyurularAdapter(duyuruArrayList)
        binding.duyurularRV.adapter = mDuyuruAdapter
        getData()
        getDataYemek()



        firebaseAuth = FirebaseAuth.getInstance()


        val anasayfaIntent = Intent(this, AnasayfaActivity::class.java)
        val girisEkraniIntent = Intent(this,GirisEkraniActivity::class.java)


        binding.apply {
            toggle = ActionBarDrawerToggle(this@AnasayfaActivity,drawerLayout,anasayfaToolBar,R.string.open,R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            toggle.drawerArrowDrawable.color = ContextCompat.getColor(this@AnasayfaActivity, R.color.white)
            setSupportActionBar(anasayfaToolBar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)



            nav_menu.setNavigationItemSelectedListener {

                when(it.itemId){

                    R.id.anasayfa ->{
                        startActivity(anasayfaIntent)
                    }
                    R.id.kisiselBilgiler -> {
                        replaceFragment(BilgilerFragment(), it.title.toString())
                        yemekAna.visibility = View.GONE
                        yemekBaklagil.visibility = View.GONE
                        yemekCorba.visibility = View.GONE
                        yemekTatli.visibility = View.GONE
                        gununYemegiBaslik.visibility = View.GONE
                        duyurularRV.visibility = View.GONE
                    }
                    R.id.sinavlar -> {
                        Toast.makeText(applicationContext,"Tıklandı",Toast.LENGTH_SHORT).show()
                    }
                    R.id.dersler -> {
                        replaceFragment(DerslerFragment(),it.title.toString())
                        duyurularRV.visibility = View.GONE
                        yemekAna.visibility = View.GONE
                        yemekBaklagil.visibility = View.GONE
                        yemekCorba.visibility = View.GONE
                        yemekTatli.visibility = View.GONE
                        gununYemegiBaslik.visibility = View.GONE
                    }
                    R.id.cikisYap ->{
                        val cikisYapIntent = Intent(applicationContext,GirisEkraniActivity::class.java)
                        startActivity(cikisYapIntent)
                    }




                }
                true

            }
        }

    }

    private fun getDataYemek() {
        FirebaseFirestore.getInstance().collection("Günün Yemeği")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (value != null) {
                        if (!value.isEmpty) {
                            val documents = value.documents
                            for (document in documents) {
                                yemekAna.text = document.get("Ana Yemek") as String
                                yemekBaklagil.text = document.get("Pilav") as String
                                yemekTatli.text = document.get("Tatlı") as String
                                yemekCorba.text = document.get("Çorba") as String


                            }
                        }
                    }
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        db.collection("Duyurular").addSnapshotListener{value,error ->
            if(error!=null){
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            else{
                if(value != null){
                    duyuruArrayList.clear()
                    if(!value.isEmpty){
                        val documents = value.documents
                        for(document in documents){
                            val baslik = document.get("Duyuru Başlık") as String
                            val detay = document.get("Duyuru İçerik") as String

                            val mDuyurular = Duyurular(baslik, detay)
                            duyuruArrayList.add(mDuyurular)
                        }
                        mDuyuruAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun replaceFragment( fragment: Fragment,title: String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }




}


