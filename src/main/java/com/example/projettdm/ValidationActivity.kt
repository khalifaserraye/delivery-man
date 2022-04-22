package com.example.projettdm


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.SparseArray
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import com.example.projettdm.entities.Commande
import com.example.projettdm.entities.Livraison
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_validation.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class ValidationActivity : AppCompatActivity() {
    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector
    private var pTotal:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validation)
        pTotal = intent.getIntExtra("pT",0)
        if (ContextCompat.checkSelfPermission(this@ValidationActivity, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            askForCameraPermission()
        } else {
            setupControls()
        }
        buttonValider.setOnClickListener {
            updateLivraison()
            valider(TextViewProductInfo.text.toString().toInt())
            Toast.makeText(this, "Livraison validée", Toast.LENGTH_SHORT).show()
            intent = Intent(applicationContext,DashboardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupControls() {
        detector = BarcodeDetector.Builder(this@ValidationActivity).build()
        cameraSource = CameraSource.Builder(this@ValidationActivity,detector)
                .setAutoFocusEnabled(true)
                .build()
        cameraSurfaceView.holder.addCallback(surgaceCallBack)
        detector.setProcessor(processor)
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
                this@ValidationActivity,
                arrayOf(android.Manifest.permission.CAMERA),requestCodeCameraPermission
        )
    }
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                Toast.makeText(applicationContext, "Permoission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val surgaceCallBack = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder?) {
            try {
                if (ActivityCompat.checkSelfPermission(
                                this@ValidationActivity,
                                Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                cameraSource.start(holder)
            }catch (e: Exception) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            cameraSource.stop()
        }
    }
    private val processor = object : Detector.Processor<Barcode>{
        override fun release() {
            TODO("Not yet implemented")
        }
        override fun receiveDetections(p0: Detector.Detections<Barcode>?) {
            if (p0 != null && p0.detectedItems.isNotEmpty()) {
                val qrCode: SparseArray<Barcode> = p0.detectedItems
                val code = qrCode.valueAt(0)
                textScanResult.text = code.displayValue
                TextViewProductInfo.text = code.displayValue
            } else {
            }
        }
    }

    fun valider(numCmd : Int) {
        val call = RetrofitService.endPoint.validerLivraison(numCmd)
        call.enqueue(object : Callback<Commande> {
            override fun onResponse(call: Call<Commande>, response: Response<Commande>) {
                Toast.makeText( applicationContext,"succes", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<Commande>, t: Throwable) {
                Toast.makeText( applicationContext,"Erreur serveur", Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun updateLivraison()  {
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
                        delLivraison(sdf.format(currentDate).toString())
                        postLivraison(Livraison(
                                list[0].totalEncaissement+pTotal,
                                list[0].nbrLivraison+1,
                                sdf.format(currentDate).toString()))
                    } else {
                        //test
                        postLivraison(Livraison(pTotal,1,sdf.format(currentDate).toString()))
                    }
                } else {
                    Toast.makeText(this@ValidationActivity, "Erreur serveur", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Livraison>>, t: Throwable) {
                Toast.makeText(this@ValidationActivity, "Erreur serveur", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun postLivraison(livraison: Livraison) {
        val call = RetrofitService.endPoint.postLivr(livraison)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                }
                else
                {
                    Toast.makeText(this@ValidationActivity,"Erreur serveur", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@ValidationActivity,"Erreur serveur", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun delLivraison(date : String) {
        val call = RetrofitService.endPoint.deleteLivraison(date)
        call.enqueue(object : Callback<Livraison> {
            override fun onResponse(call: Call<Livraison>, response: Response<Livraison>) {
            }
            override fun onFailure(call: Call<Livraison>, t: Throwable) {
            }
        })
    }

}