package com.example.projettdm.ui.slideshow

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projettdm.CommandeAdapter
import com.example.projettdm.ProduitAdapter
import com.example.projettdm.R
import com.example.projettdm.RetrofitService
import com.example.projettdm.entities.Commande
import com.example.projettdm.entities.Produit
import com.example.projettdm.entities.ProduitCommande
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_slideshow.*
import kotlinx.android.synthetic.main.layout_produit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    val data1= mutableListOf<Produit>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel = ViewModelProvider(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val numc = arguments?.getInt("numC")
        if (numc != null) {
            getProduit(numc)
        }
    }
    private fun getProduit(numc : Int) {
        val call = RetrofitService.endPoint.getProduitsCommande(numc)
        call.enqueue(object : Callback<List<Produit>> {
            override fun onResponse(call: Call<List<Produit>>, response: Response<List<Produit>>) {
                val list = response.body()!!
                for (item in list){
                    data1.add(item)
                }
                RecyclerViewProduits.layoutManager = LinearLayoutManager(activity)
                RecyclerViewProduits.adapter = ProduitAdapter(activity as Activity,data1,numc)
            }
            override fun onFailure(call: Call<List<Produit>>, t: Throwable) {
            }
        })
    }
}