package com.example.projettdm

import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projettdm.entities.Commande
import com.example.projettdm.entities.Produit
import com.example.projettdm.entities.ProduitCommande
import kotlinx.android.synthetic.main.fragment_slideshow.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommandeAdapter(val context: Activity, var data:List<Commande>): RecyclerView.Adapter<MyViewHolder>() {
    var ptt:Int=0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_commande, parent, false))
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.NumCommande.text = data[position].numCommande.toString()
        holder.NomCommande.text = data[position].nom
        holder.AdresseClient.text = data[position].adresse
        holder.NumTelClient.text = data[position].numTelClient
        holder.EmailClient.text = data[position].emailClient
        var prixTot=0
        val call1 = RetrofitService.endPoint.getProduitsCommande(data[position].numCommande)
        call1.enqueue(object : Callback<List<Produit>> {
            override fun onResponse(call1: Call<List<Produit>>, response1: Response<List<Produit>>) {
                val list1 = response1.body()!!
                for (item in list1){
                    val call2 = RetrofitService.endPoint.getQuantite(data[position].numCommande, item.nomProduit)
                    call2.enqueue(object : Callback<List<ProduitCommande>> {
                        override fun onResponse(call2: Call<List<ProduitCommande>>, response2: Response<List<ProduitCommande>>) {
                            val list2 = response2.body()!!
                            prixTot = prixTot + list2[0].quantite * item.prix
                            holder.PrixTotal.text=prixTot.toString() + " DA"
                            ptt=prixTot
                        }
                        override fun onFailure(call1: Call<List<ProduitCommande>>, t1: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })
                }

            }
            override fun onFailure(call2: Call<List<Produit>>, t2: Throwable) {
            }
        })
        holder.det.setOnClickListener {view->
            var bundle = bundleOf("numC" to data[position].numCommande)//, "numC2" to data[position].numCommande.toString())
            view.findNavController().navigate(R.id.action_nav_gallery_to_nav_slideshow2,bundle)
        }

        holder.map.setOnClickListener {

            val intent = Intent(context,MapActivity::class.java)
            intent.putExtra("adresse", data[position].adresse)
            startActivity(context,intent,Bundle())

        }

        holder.valider.setOnClickListener {
            val intent = Intent(context,ValidationActivity::class.java)
            intent.putExtra("pT", ptt)//holder.PrixTotal.text.toString().toInt())
            startActivity(context,intent,Bundle())
        }
    }



    override fun getItemCount() = data.size
}
class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val NumCommande = view.findViewById<TextView>(R.id.textNumCommande) as TextView
    val NomCommande = view.findViewById<TextView>(R.id.textNomCommande) as TextView
    val AdresseClient = view.findViewById<TextView>(R.id.textAdresseClient) as TextView
    val NumTelClient = view.findViewById<TextView>(R.id.textNumTelClient) as TextView
    val EmailClient = view.findViewById<TextView>(R.id.textEmailClient) as TextView
    val PrixTotal = view.findViewById<TextView>(R.id.textPrixTotal) as TextView
    val map = view.findViewById<TextView>(R.id.text7) as TextView
    val valider = view.findViewById<TextView>(R.id.textValider) as TextView
    var det = view.findViewById<ImageView>(R.id.click) as ImageView
}
