package com.example.projettdm.ui.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projettdm.CommandeAdapter
import com.example.projettdm.R
import com.example.projettdm.RetrofitService
import com.example.projettdm.entities.Commande
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_slideshow.*
import retrofit2.Call
import retrofit2.Response

class GalleryFragment : Fragment() {
    private lateinit var galleryViewModel: GalleryViewModel
    val data= mutableListOf<Commande>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        data.clear()
        getAllCommandes()
        super.onActivityCreated(savedInstanceState)
        RecyclerViewCommandes.layoutManager = LinearLayoutManager(activity)
        RecyclerViewCommandes.adapter = CommandeAdapter(activity as Activity,data)
    }

    fun getAllCommandes() {
        //data.add(Commande(12,"","","","","",5,6,""))
   //     val idliv = arguments?.getInt("idL").toString().toInt()
     //   val b= Intent.getIntExtra("idL",0).toString()
        val call = RetrofitService.endPoint.getAllCommandes(0)
        call.enqueue(object : retrofit2.Callback<List<Commande>> {
            override fun onResponse(call: Call<List<Commande>>, response: Response<List<Commande>>) {
                if (response.isSuccessful){
                    val list = response.body()!!
                    for (item in list){
                        data.add(item)
                    }
                    RecyclerViewCommandes.layoutManager = LinearLayoutManager(activity)
                    RecyclerViewCommandes.adapter = CommandeAdapter(activity as Activity,data)
                }
                else
                {
                    Toast.makeText(activity as Activity,"Error1", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Commande>>, t: Throwable) {
            }
        }
        )
    }
}