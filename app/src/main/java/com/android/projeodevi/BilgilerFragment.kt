package com.android.projeodevi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.android.projeodevi.databinding.FragmentBilgilerBinding
import com.android.projeodevi.databinding.GirisEkraniBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_onaylanmis_ogrenci_verileri_gir.*
import kotlinx.android.synthetic.main.fragment_bilgiler.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BilgilerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BilgilerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding:FragmentBilgilerBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        binding = FragmentBilgilerBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        val kullaniciID = firebaseAuth.currentUser?.uid.toString()

        val db = FirebaseFirestore.getInstance()
        db.collection("Öğrenciler").get().addOnCompleteListener{
            if(it.isSuccessful){

                for(document in it.result){

                    val fireBaseID = document.get("UID") as String

                    if(kullaniciID == fireBaseID){
                        val fireBaseAdi = document.get("Adı") as String
                        val fireBaseSoyadi = document.get("Soyadı") as String
                        val fireBaseEmail = document.get("Email Adresi") as String
                        val fireBaseTC = document.get("TC") as String
                        val fireBaseTel = document.get("Telefon Numarası") as String
                        val fireBaseBolum = document.get("Okuduğu Bölüm") as String
                        val fireBaseStatu = document.get("Statü") as String
                        val fireBaseOgrNo = document.get("Öğrenci Numarası") as String
                        val fireBaseDanisman = document.get("Danışman Bilgisi") as String
                        val fireBaseKayıtTarihi = document.get("Kayıt Tarihi") as String
                        val fireBaseDonem = document.get("Akademik Dönem") as String
                        val fireBaseOrtalama = document.get("Mezuniyet Ortalaması") as String

                        bilgilerAdKutu.text = fireBaseAdi
                        bilgilerSoyadKutu.text = fireBaseSoyadi
                        tcNo.text = fireBaseTC
                        telNo.text = fireBaseTel
                        mailAdresi.text = fireBaseEmail
                        bilgilerBolumKutu.text = fireBaseBolum
                        Statu.text = fireBaseStatu
                        OgrenciNo.text = fireBaseOgrNo
                        bilgilerDanismanKutu.text = fireBaseDanisman
                        bilgilerKayitKutu.text = fireBaseKayıtTarihi
                        bilgilerAkademikKutu.text = fireBaseDonem
                        bilgilerOrtalamaKutu.text = fireBaseOrtalama
                        break;
                    }

                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bilgiler, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BilgilerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BilgilerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}