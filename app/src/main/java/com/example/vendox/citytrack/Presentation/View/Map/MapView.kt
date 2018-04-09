package com.example.vendox.citytrack.Presentation.View.Map

import android.location.Location
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng

/**
 * Created by vehdox on 05.04.18.
 */
interface MapView {
    fun enableLocationPlugin()
    fun initializeLocationEngine()
    fun setCameraPosition(location: Location)
}