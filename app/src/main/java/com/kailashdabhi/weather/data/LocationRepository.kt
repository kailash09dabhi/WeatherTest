package com.kailashdabhi.weather.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import io.reactivex.Single
import java.io.IOException
import java.util.Locale

/**
 * @author kailash09dabhi@gmail.com
 * @date 22, Jan 2020 (21:13)
 */
class LocationRepository {
  fun getLastLocation(context: Context): Single<String> {
    return Single.create { emitter ->
      if (ActivityCompat.checkSelfPermission(
          context,
          Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
      ) {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
          if (it != null) {
            var lastKnownLocation = it
            val geocoder: Geocoder = Geocoder(context, Locale.getDefault())
            try {
              val address: List<Address> =
                geocoder.getFromLocation(it.getLatitude(), it.getLongitude(), 1)
              val cityName: String = address.get(0).getLocality();
              emitter.onSuccess(cityName)

            } catch (e1: IOException) {
              emitter.onError(e1)
            }

          } else
            emitter.onError(Throwable("Your location settings is turned off"));
        }
      } else
        emitter.onError(Throwable("You haven't given the permissions"));
    };
  }
}