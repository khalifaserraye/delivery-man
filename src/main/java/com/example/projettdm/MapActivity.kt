package com.example.projettdm

import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.provider.Settings.System.getString
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.TypedArrayUtils.getString
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {

    private var locationManager : LocationManager? = null
    private lateinit var x: String
    private lateinit var adr:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        adr = intent.getStringExtra("adresse")
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
            try {
                locationManager?.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0f,
                    locationListener
                )
                intent = Intent(applicationContext,DashboardActivity::class.java)
                startActivity(intent)
            } catch (ex: SecurityException) {
            }
    }

    var test = true
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            x=("" + location.longitude + ":" + location.latitude)
            if (test)
                displayTrack("" + location.longitude + ":" + location.latitude,adr);
            test = false
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun displayTrack(eSource:String, eDestination:String) {
        val uri=  Uri.parse("https://www.google.co.in/maps/dir/"+eSource+"/"+eDestination);
        intent =  Intent(Intent.ACTION_VIEW,uri)
        intent.setPackage("com.google.android.apps.maps")
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}


