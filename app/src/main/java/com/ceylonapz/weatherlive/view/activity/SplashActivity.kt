package com.ceylonapz.weatherlive.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkInfo
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.utilities.CURRENT_LOCATION
import com.ceylonapz.weatherlive.utilities.GPS_LOCATION
import com.ceylonapz.weatherlive.utilities.NO_LOCATION
import com.ceylonapz.weatherlive.viewmodel.SplashViewModel
import com.infinity.movieapp.extensions.navigateActivity
import kotlinx.coroutines.FlowPreview

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
                    navigateMainScreen(status)
                }

            }
        })

        getLocationPermission()
    }

    @FlowPreview
    private fun navigateMainScreen(status: String?) {
        val mainBundle = Bundle()
        mainBundle.putString(GPS_LOCATION, status)
        navigateActivity(MainActivity::class.java, mainBundle, finish())
    }

    private fun getLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        viewModel.callLocationService(true)
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        viewModel.callLocationService(true)
                    }
                    else -> {
                        viewModel.callLocationService(false)
                        navigateMainScreen(NO_LOCATION)
                    }
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
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