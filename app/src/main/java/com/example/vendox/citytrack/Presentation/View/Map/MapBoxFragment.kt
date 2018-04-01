package com.example.vendox.citytrack.Presentation.View.Map

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView

import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import android.location.Location
import com.mapbox.mapboxsdk.geometry.LatLng

import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.vendox.citytrack.R
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerMode
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin
import com.mapbox.services.android.location.LostLocationEngine
import com.mapbox.services.android.navigation.ui.v5.feedback.FeedbackBottomSheet
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import com.mapbox.services.android.telemetry.location.LocationEngine
import com.mapbox.services.android.telemetry.location.LocationEngineListener
import com.mapbox.services.android.telemetry.location.LocationEnginePriority
import com.mapbox.services.android.telemetry.permissions.PermissionsListener
import com.mapbox.services.android.telemetry.permissions.PermissionsManager

import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.location.config.LocationAccuracy
import io.nlopez.smartlocation.location.config.LocationParams
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapBoxFragment: Fragment(), LocationEngineListener, PermissionsListener {
    lateinit private var mapView: MapView


    private var map: MapboxMap? = null
    private var permissionsManager: PermissionsManager? = null
    private var locationPlugin: LocationLayerPlugin? = null
    private var locationEngine: LocationEngine? = null
    private var originLocation: Location? = null


    //Построение пути
    private var originPosition: Point? = null
    private var destinationPosition: Point? = null
    private var currentRoute: DirectionsRoute? = null
    private var navigationMapRoute: NavigationMapRoute? = null


    var waypoints = arrayListOf<Point>()
    var way = arrayListOf<Point>()

    companion object {

        fun newInstance(): MapBoxFragment {
            return MapBoxFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.map_box_fragment, container, false)

        Mapbox.getInstance(activity, getString(R.string.access_token))
        mapView = rootView!!.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)


        super.onCreate(savedInstanceState)
        var i = 0

        val startButton: Button = rootView.findViewById(R.id.startButton)
        startButton.setOnClickListener(View.OnClickListener {
            Log.i("text", ""+ startButton.text)
            if (i > 0 ){
                stopTracking()
                startButton.text = "Start"
                i = 0
            } else {
                startTracking()
                startButton.text = "Stop"
                i += 1
            }

        })

        mapView.getMapAsync(object: OnMapReadyCallback{
            override fun onMapReady(mapboxMap: MapboxMap) {
                map = mapboxMap
                enableLocationPlugin()
            }
        })
        return rootView
    }

    @SuppressWarnings( "MissingPermission")
    fun enableLocationPlugin(){

        if (PermissionsManager.areLocationPermissionsGranted(activity)){
            initializeLocationEngine()

            locationPlugin = map?.let { LocationLayerPlugin(mapView, it, locationEngine) }
            locationPlugin!!.setLocationLayerEnabled(LocationLayerMode.TRACKING)
        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager!!.requestLocationPermissions(activity)
        }

    }

    @SuppressWarnings( "MissingPermission")
    fun initializeLocationEngine() {
        locationEngine = LostLocationEngine(activity)
        locationEngine!!.priority = LocationEnginePriority.HIGH_ACCURACY
        locationEngine!!.activate()

        val lastLocation = locationEngine!!.lastLocation

        if (lastLocation != null) {
            originLocation = lastLocation
            setCameraPosition(lastLocation)
        } else {
            locationEngine!!.addLocationEngineListener(this)
        }
    }

    fun setCameraPosition(location: Location){
        map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(location.latitude, location.longitude), 13.0))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        permissionsManager!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {

    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            enableLocationPlugin()
        }
    }

    @SuppressWarnings("MissingPermission")
    override fun onConnected() {
        locationEngine!!.requestLocationUpdates()
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null){
            originLocation = location
            setCameraPosition(location)
            locationEngine!!.removeLocationEngineListener(this)
            Log.e("Mapbox", "" +location.latitude + location.longitude )
        }
    }

    @SuppressWarnings("MissingPermission")
    override fun onStart() {
        super.onStart()
        locationEngine?.requestLocationUpdates()
        locationPlugin?.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }


    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    fun startTracking(){


        var i = 0

        val mLocTrackingInterval: Long = 1000 * 15; // 5 sec
        val trackingDistance: Float = 0F;
        val trackingAccuracy: LocationAccuracy = LocationAccuracy.MEDIUM;

        val builder= LocationParams.Builder()
                .setAccuracy(trackingAccuracy)
                .setDistance(trackingDistance)
                .setInterval(mLocTrackingInterval)


        SmartLocation.with(activity)
                .location()
                .continuous()
                .config(builder.build())
                .start(object : OnLocationUpdatedListener {
                    override fun onLocationUpdated(location: Location) {
                        originLocation = location
                        Log.i("smartLocation", "" +originLocation!!.longitude +", "+ originLocation!!.latitude)
                        if (i >27) {
                            stopTracking()
                            Toast.makeText(activity, "Ooops", Toast.LENGTH_SHORT).show()
                        }
                        if (i == 0){
                            originPosition = Point.fromLngLat(originLocation!!.longitude, originLocation!!.latitude)
                            originPosition?.let { way.add(it) }
                        }
                        if (i > 0){
                            if (i > 26){
                                destinationPosition = Point.fromLngLat(originLocation!!.longitude, originLocation!!.latitude)
                                destinationPosition?.let { way.add(it) }
                                getRoute(originPosition!!, destinationPosition!!)
                            }
                            destinationPosition = Point.fromLngLat(originLocation!!.longitude, originLocation!!.latitude)
                            destinationPosition?.let { way.add(it) }
                            destinationPosition?.let { waypoints.add(it) }
                            getRoute(originPosition!!, destinationPosition!!)


                        }
                        i ++
                    }


                })
    }
    fun stopTracking(){
        SmartLocation.with(activity)
                .location()
                .stop()
        SmartLocation.with(activity)
                .activity()
                .stop()
    }

    fun getRoute(origin: Point , destination: Point){

        val builder = NavigationRoute.builder()
                .accessToken(Mapbox.getAccessToken())
                .profile("walking")
                .origin(origin)
                .destination(destination)

        for (p in waypoints){
            builder.addWaypoint(p)
        }
        builder.build()
                .getRoute(object : Callback<DirectionsResponse> {
                    override fun onFailure(call: Call<DirectionsResponse>?, t: Throwable) {
                        Log.e(FeedbackBottomSheet.TAG, "Error: "+ t.printStackTrace() );
                    }

                    override fun onResponse(call: Call<DirectionsResponse>?, response: Response<DirectionsResponse>?) {
                        Log.d(FeedbackBottomSheet.TAG, "Response code: " + response!!.code());
                        if (response.body() == null) {
                            Log.e(FeedbackBottomSheet.TAG, "No routes found, make sure you set the right user and access token.")
                            return
                        } else if (response.body()!!.routes().size < 1) {
                            Log.e(FeedbackBottomSheet.TAG, "No routes found")
                            return
                        }

                        currentRoute = response.body()!!.routes().get(0);
                        navigationMapRoute?.removeRoute()

                        navigationMapRoute = map?.let { NavigationMapRoute(null, mapView, it, R.style.NavigationMapRoute) }


                        navigationMapRoute?.addRoute(currentRoute)

                    }

                })


    }



}