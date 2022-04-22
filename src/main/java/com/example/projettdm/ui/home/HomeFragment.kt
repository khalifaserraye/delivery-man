package com.example.projettdm.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projettdm.CommandeAdapter
import com.example.projettdm.R
import com.example.projettdm.RetrofitService
import com.example.projettdm.ValidationActivity
import com.example.projettdm.entities.Commande
import com.example.projettdm.entities.Livraison
import com.example.projettdm.entities.ProduitCommande
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getNombreCommandesenRatard()
        nbrLiv()
    }

    private fun nbrLiv() {
       val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Date()
        val call = RetrofitService.endPoint.getLivraison(sdf.format(currentDate).toString())//sdf.format(currentDate))
        call.enqueue(object : Callback<List<Livraison>> {
            override fun onResponse(
                call: Call<List<Livraison>>,
                response: Response<List<Livraison>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()!!
                    if (list.isNotEmpty()) {
                        Encaissement.text = list[0].totalEncaissement.toString()+" DA"
                        Livrasion.text = list[0].nbrLivraison.toString()
                    } else {
                        Encaissement.text = "0 DA"
                        Livrasion.text = "0"
                    }
                } else {
                    Toast.makeText(context, "Erreur au niveau serveur", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Livraison>>, t: Throwable) {
                Toast.makeText(context, "Erreur au niveau serveur", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getNombreCommandesenRatard() {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        var nbrCmd = 0
    //    val idliv = arguments?.getInt("idL").toString().toInt()
        val call = RetrofitService.endPoint.getAllCommandes(0)
        call.enqueue(object : Callback<List<Commande>> {
            override fun onResponse(
                call: Call<List<Commande>>,
                response: Response<List<Commande>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()!!
                    if (list.isNotEmpty()) {
                        for (item in list) {
                                if (convertISOTimeToDate(item.dateRecuperation).toString().compareTo(sdf.format(getDateBefore48H()))<0) {
                                    nbrCmd++
                                }
                        }
                        Commandes.text=nbrCmd.toString()
                    } else {
                    }

                } else {
                    Toast.makeText(context, "Erreur au niveau serveur", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Commande>>, t: Throwable) {
                Toast.makeText(context, "Erreur au niveau serveur", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getDateBefore48H() : Date {
        val date = Date()
        val c = Calendar.getInstance()
        c.time = date
        c.add(Calendar.DATE, -2)
        c.add(Calendar.HOUR, -1)//modification de differance de temps
        return  c.time
    }

    fun convertISOTimeToDate(isoTime: String): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        var convertedDate: Date? = null
        var formattedDate: String? = null
        try {
            convertedDate = sdf.parse(isoTime)
            formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(convertedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formattedDate
    }
}


