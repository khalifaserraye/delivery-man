package com.example.projettdm.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.projettdm.*

import com.example.projettdm.entities.Livreur
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    val listLivreurs = mutableListOf<Livreur>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)

        login.setOnClickListener {
            getLivreurs()
            for (item in listLivreurs) {
                if (item.username.equals(username.text.toString()) && item.pswd.equals(password.text.toString())) {
                    intent = Intent(this,DashboardActivity::class.java)
                    intent.putExtra("fn", item.firstName + " " + item.lastName);
                    intent.putExtra("em", item.username+"@gmail.com")
                    intent.putExtra("idL", item.idLivreur);
                    startActivity(intent)
                    break
                } else {
                    Toast.makeText(this@LoginActivity, "Informations d'authentification incorrectes", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getLivreurs() {
        val call = RetrofitService.endPoint.getLivreurs()
        call.enqueue(object : Callback<List<Livreur>> {
            override fun onResponse(call: Call<List<Livreur>>, response: Response<List<Livreur>>) {
                if (response.isSuccessful){
                    val list = response.body()!!
                    for (item in list){
                        listLivreurs.add(item)
                    }
                }
                else
                {
                    Toast.makeText(this@LoginActivity,"Erreur au niveau serveur", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Livreur>>, t: Throwable) {
                Toast.makeText(this@LoginActivity,"Erreur au niveau serveur", Toast.LENGTH_SHORT).show()
            }
        })
    }

}