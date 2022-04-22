package com.example.projettdm

import android.app.Activity
import android.content.Intent
import android.content.Intent.getIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projettdm.entities.Produit
import com.example.projettdm.entities.ProduitCommande
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.absoluteValue


class ProduitAdapter(val context: Activity, var data1: List<Produit>, var numc : Int): RecyclerView.Adapter<MyViewHolder2>() {
    var name:Int=0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        return MyViewHolder2(LayoutInflater.from(context).inflate(R.layout.layout_produit, parent, false))
    }


    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
        holder.nomProduit.text = data1[position].nomProduit
        holder.prixUnit.text = data1[position].prix.toString()+" DA"
        val call = RetrofitService.endPoint.getQuantite(numc, data1[position].nomProduit)
        call.enqueue(object : Callback<List<ProduitCommande>> {
            override fun onResponse(call: Call<List<ProduitCommande>>, response: Response<List<ProduitCommande>>) {
                val list = response.body()!!
                holder.quantite.text = list[0].quantite.toString()
            }
            override fun onFailure(call: Call<List<ProduitCommande>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun getItemCount() = data1.size

}
class MyViewHolder2(view: View) : RecyclerView.ViewHolder(view) {
    val nomProduit = view.findViewById<TextView>(R.id.nomProduit) as TextView
    val prixUnit = view.findViewById<TextView>(R.id.prixUnit) as TextView
    val quantite = view.findViewById<TextView>(R.id.quantite) as TextView
  //  var editTextNumber = view.findViewById<TextView>(R.id.editTextNumber) as TextView

}
