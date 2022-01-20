package com.ceylonapz.weatherlive.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkInfo
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.utilities.CURRENT_LOCATION
import com.ceylonapz.weatherlive.utilities.GPS_LOCATION
import com.ceylonapz.weatherlive.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private val TAG = "SplashActivity"
    private val viewModel: SplashViewModel by viewModels {
        SplashViewModel.SplashViewModelFactory(application)
    }
    //private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /*fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        obtieneLocalizacion()*/

        viewModel.splashStatusInfo.observe(this, { workInfo ->
            if (workInfo != null) {
                if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                    val status = workInfo.outputData.getString(CURRENT_LOCATION)

                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra(GPS_LOCATION, status)
                    }
                    startActivity(intent)
                    finish()
                }

            }
        })
    }

    /*private fun obtieneLocalizacion() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
               *//* val currentLocation =
                    location?.latitude.toString() + "," + location?.longitude.toString()*//*

                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra(GPS_LOCATION, getAddressInfo(applicationContext, location!!.latitude, location.longitude))
                }
                startActivity(intent)
                finish()
            }
    }*/
}