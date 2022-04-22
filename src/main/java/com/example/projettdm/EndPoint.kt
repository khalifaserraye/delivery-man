package com.example.projettdm

import com.example.projettdm.entities.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface EndPoint {
    @GET("getLivreurs")
    fun getLivreurs() : Call<List<Livreur>>

    @GET("getCommandes/{idLivreur}")
    fun getAllCommandes(@Path("idLivreur") idL: Int): Call<List<Commande>>

    @GET("getLivraisonByDate/{date}")
    fun getLivraison(@Path("date") dt: String): Call<List<Livraison>>

    @GET("getProduitsCommande/{numCommande}")
    fun getProduitsCommande(@Path("numCommande") nc: Int): Call<List<Produit>>

 //   @GET("getQuantite/{numCommande}/{nomProduit}")
  //  fun getQuantite(@Path("numCommande") nc: Int, np: String): Call<List<ProduitCommande>>

    @GET("/getQuantite/{numCommande}/{nomProduit}")
    fun getQuantite(
        @Path("numCommande") param1: Int?,
        @Path("nomProduit") param2: String?
    ): Call<List<ProduitCommande>>

    @POST("postLivraison")
    fun postLivr(@Body livraison: Livraison) : Call<String>

    @DELETE("delivrer/{numCommande}")
    fun validerLivraison(@Path("numCommande") numCmd: Int) : Call<Commande>





    @DELETE("/deleteLivraisonByDate/{date}")
    fun deleteLivraison(@Path("date") dt: String) : Call<Livraison>
}