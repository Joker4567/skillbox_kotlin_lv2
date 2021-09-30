package com.simplemobiletools.clock.helpers

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import com.google.android.gms.location.LocationServices
import androidx.core.app.ActivityCompat
import com.google.android.gms.tasks.Task
import java.lang.IllegalStateException
import java.util.NoSuchElementException

class LastLocationProvider constructor(private val context: Context) {

    fun getLocation(callback: (Result<Location>) -> Unit) {
        if (!hasLocationPermission()) {
            callback.invoke(Result.failure(IllegalStateException("Missing location permission")))
            return
        }
        val lastLocationTask = getLocationTask()
        lastLocationTask.addOnCompleteListener { task ->
            val location = task.getLocation()
            if (location != null) {
                callback.invoke(Result.success(location))
            } else {
                callback.invoke(Result.failure(NoSuchElementException("No location found")))
            }
        }
    }

    @SuppressLint("MissingPermission") // permission is checked
    private fun getLocationTask(): Task<Location> {
        return LocationServices
            .getFusedLocationProviderClient(context)
            .lastLocation
    }

    private fun Task<Location>.getLocation(): Location? {
        return if (isSuccessful && result != null) {
            result!!
        } else {
            null
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    }
}
