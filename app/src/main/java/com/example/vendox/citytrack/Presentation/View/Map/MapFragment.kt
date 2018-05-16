package com.example.vendox.citytrack.Presentation.View.Map

import android.annotation.SuppressLint
import android.app.Fragment
import android.location.Location
import android.os.Bundle
import android.support.annotation.NonNull
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.vendox.citytrack.Data.Repository.GetRoute
import com.example.vendox.citytrack.Data.Repository.Route
import com.example.vendox.citytrack.Data.RepositoryProvider
import com.example.vendox.citytrack.Domain.UseCases.MapboxUseCase
import com.example.vendox.citytrack.Presentation.View.Main.MapBoxActivity
import com.example.vendox.citytrack.R
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerMode
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin
import com.mapbox.services.android.location.LostLocationEngine
import com.mapbox.services.android.telemetry.location.LocationEngine
import com.mapbox.services.android.telemetry.location.LocationEngineListener
import com.mapbox.services.android.telemetry.location.LocationEnginePriority
import com.mapbox.services.android.telemetry.permissions.PermissionsListener
import com.mapbox.services.android.telemetry.permissions.PermissionsManager

/**
 * Created by vehdox on 05.04.18.
 */
class MapFragment: Fragment(), com.example.vendox.citytrack.Presentation.View.Map.MapView, PermissionsListener, LocationEngineListener {
    private var mapView: MapView? = null

    lateinit var presenter: MapPresenter


    // variables for adding location layer
     lateinit var map: MapboxMap
    private var permissionsManager: PermissionsManager? = null
    private var locationPlugin: LocationLayerPlugin? = null
    private var locationEngine: LocationEngine? = null
    private var originLocation: Location? = null

    private var originPosition: Point? = null
    private var destinationPosition: Point? = null
    private var currentRoute: DirectionsRoute? = null

    companion object {

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater?.inflate(R.layout.map_box_fragment, container, false)

        Mapbox.getInstance(activity, getString(R.string.access_token))
        mapView = rootView!!.findViewById(R.id.mapView)
        mapView!!.onCreate(savedInstanceState)
        var i = 0
        val mapboxUseCase = MapboxUseCase(RepositoryProvider.getRoute())
        presenter = MapPresenter(this, mapboxUseCase)

        val startButton: Button = rootView.findViewById(R.id.startButton)
        startButton.setOnClickListener(View.OnClickListener {
            Log.i("text", ""+ startButton.text)
            if (i > 0 ){
//                stopTracking()
                startButton.text = "Start"
                i = 0
            } else {
//                startTracking()
                startButton.text = "Stop"
                i += 1

            }


        })

        mapView?.getMapAsync(object: OnMapReadyCallback {
            override fun onMapReady(mapboxMap: MapboxMap) {
                val bounds = LatLngBounds.Builder()
                        .include(LatLng(56.0431162,37.5329372)) // Northeast
                        .include(LatLng(55.4725803, 37.7006232))
                        .include(LatLng(55.6368534, 37.4429595))
                        .include(LatLng(55.7499363, 37.9918402))// Southwest
                        .build()
                    mapboxMap.setLatLngBoundsForCameraTarget(bounds)

                map = mapboxMap
                enableLocationPlugin()

                Log.e("map create", ""+ map)


            }
        })


        return rootView
            }



    @SuppressWarnings("MissingPermission")
    override fun onStart() {
        super.onStart()
        locationEngine?.requestLocationUpdates()
        locationPlugin?.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        (activity as MapBoxActivity).setActionBarTitle("Карта")
        mapView?.onResume()
    }


    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    @SuppressLint("MissingPermission")
    override fun enableLocationPlugin() {
        if (PermissionsManager.areLocationPermissionsGranted(activity)){
            initializeLocationEngine()

            locationPlugin = map.let { LocationLayerPlugin(mapView!!, it, locationEngine) }
            locationPlugin?.setLocationLayerEnabled(LocationLayerMode.TRACKING)
        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager?.requestLocationPermissions(activity)
        }
    }

    @SuppressLint("MissingPermission")
    override fun initializeLocationEngine() {
        locationEngine = LostLocationEngine(activity)
        locationEngine?.priority = LocationEnginePriority.HIGH_ACCURACY
        locationEngine?.activate()

        val lastLocation = locationEngine?.lastLocation

        if (lastLocation != null) {
            originLocation = lastLocation
            setCameraPosition(lastLocation)
        } else {
            locationEngine?.addLocationEngineListener(this)
        }
    }

    override fun setCameraPosition(location: Location) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(location.latitude, location.longitude), 13.0))

    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<out String>?,@NonNull grantResults: IntArray?) {
        permissionsManager?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {

    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            enableLocationPlugin()
        }else{
            activity.finish()
        }
    }

    @SuppressWarnings("MissingPermission")
    override fun onConnected() {
        locationEngine?.requestLocationUpdates()
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null){
            originLocation = location
            setCameraPosition(location)
            locationEngine?.removeLocationEngineListener(this)
            Log.e("Mapbox", "" +location.latitude + location.longitude )
        }
    }

}




