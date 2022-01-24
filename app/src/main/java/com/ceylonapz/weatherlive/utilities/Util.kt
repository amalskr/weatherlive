package com.ceylonapz.weatherlive.utilities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import java.util.*


fun getLastKnownLocation(context: Context): String {
    val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val providers: List<String> = locationManager.getProviders(true)
    var location: Location? = null
    for (i in providers.size - 1 downTo 0) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
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
            return NO_LOCATION
        }
        location = locationManager.getLastKnownLocation(providers[i])
        if (location != null)
            break
    }

    if (location != null) {
        return getAddressInfo(context, location.getLatitude(), location.getLongitude())
    } else {
        return NO_LOCATION
    }
}

fun getAddressInfo(context: Context, latitude: Double, longitude: Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
    val city: String = addresses[0].subAdminArea
    val local: String = addresses[0].locality
    val country: String = addresses[0].countryName
    return "$city $local $country"
}

fun convertTemperature(degree: Double, type: String): String {
    return when (type) {
        "Celsius" -> {
            val tempeCelsius = (degree - 32) * 5 / 9
            roundTemp(tempeCelsius) + "°C"
        }
        "fahrenheit" -> {
            val tempeFahren: Double = degree * 9 / 5 + 32
            roundTemp(tempeFahren) + "°F"
        }
        else -> {
            "$degree"
        }
    }
}

private fun roundTemp(temph: Double): String {
    return String.format("%.1f", temph)
}
