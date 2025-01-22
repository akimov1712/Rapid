package ru.topbun.rapid.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

@SuppressLint("MissingPermission")
fun getUserLocation(context: Context, onLocationReceived: (latitude: Double, longitude: Double) -> Unit) {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            val latitude = it.latitude
            val longitude = it.longitude
            onLocationReceived(latitude, longitude)
        }
    }.addOnFailureListener {
        it.printStackTrace()
    }
}


fun getAddressFromLocation(context: Context, lat: Double, lon: Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses: List<Address> =
            geocoder.getFromLocation(lat, lon, 1) ?: throw RuntimeException()
        if (addresses.isNotEmpty()) {
            val address = addresses[0]
            "${address.thoroughfare ?: ""} ${address.subThoroughfare ?: ""}, " +
                    "${address.locality ?: ""}, ${address.postalCode ?: ""}"
        } else {
            "Адрес не найден"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "Ошибка получения адреса"
    }
}