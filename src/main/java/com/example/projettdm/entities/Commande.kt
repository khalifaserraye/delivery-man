package com.example.projettdm.entities

import java.sql.Date
import java.text.DateFormat
import java.util.*

data class Commande (
        var numCommande : Int,
        var nom : String,
        var adresse : String,
        var numTelClient : String,
        var emailClient : String,
        var iconeURL : String,
        var idLivreur : Int,
        var delivred : Int,
        var dateRecuperation : String
)