package com.android.projeodevi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.projeodevi.adapter.CustomAdapter
import com.android.projeodevi.model.Dersler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_ders_ekle.*
import kotlinx.android.synthetic.main.fragment_dersler.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DerslerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DerslerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var firestore:FirebaseFirestore
    private lateinit var derslerRecyclerView: RecyclerView
    private lateinit var derslerArrayList: ArrayList<Dersler>
    private lateinit var adapter : CustomAdapter
    private lateinit var firebaseAuth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        derslerArrayList = arrayListOf<Dersler>()
        getData()

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    private fun getData() {
        FirebaseFirestore.getInstance().collection("Dersler")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                else{
                    if (value != null){
                        derslerArrayList.clear()
                        if (!value.isEmpty){
                            val documents = value.documents
                            for (document in documents) {
                                val dersAdi =  document.get("Ders Adı") as String
                                val dersKodu = document.get("Ders Kodu") as String
                                val krediler = document.get("Ders Kredisi") as String
                                val ogretimElemani = document.get("Ögretim Elemanı") as String
                                val DersEkle = Dersler(dersKodu, dersAdi, krediler, ogretimElemani)

                                derslerArrayList.add(DersEkle)

                            }
                                derslerRecyclerView.adapter = CustomAdapter(derslerArrayList)

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
        return inflater.inflate(R.layout.fragment_dersler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        val layoutManager = LinearLayoutManager(context)
        derslerRecyclerView = view.findViewById(R.id.recyclerViewDersler)
        derslerRecyclerView.layoutManager = layoutManager
        derslerRecyclerView.setHasFixedSize(true)
        adapter = CustomAdapter(derslerArrayList)
        derslerRecyclerView.adapter = adapter

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DerslerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DerslerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}