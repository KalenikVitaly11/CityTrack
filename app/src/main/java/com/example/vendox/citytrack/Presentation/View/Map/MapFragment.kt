package com.example.vendox.citytrack.Presentation.View.Map

import android.annotation.SuppressLint
import android.app.Fragment
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.vendox.citytrack.R
import com.google.gson.Gson
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.PolylineOptions
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerMode
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin
import com.mapbox.services.Constants
import com.mapbox.services.android.location.LostLocationEngine
import com.mapbox.services.android.navigation.ui.v5.feedback.FeedbackBottomSheet
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import com.mapbox.services.android.telemetry.location.LocationEngine
import com.mapbox.services.android.telemetry.location.LocationEngineListener
import com.mapbox.services.android.telemetry.location.LocationEnginePriority
import com.mapbox.services.android.telemetry.permissions.PermissionsListener
import com.mapbox.services.android.telemetry.permissions.PermissionsManager
import com.mapbox.services.api.directions.v5.DirectionsCriteria
import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.location.config.LocationAccuracy
import io.nlopez.smartlocation.location.config.LocationParams
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@SuppressLint("ByteOrderMark")
/**
 * Created by vehdox on 05.04.18.
 */
class MapFragment: Fragment(), com.example.vendox.citytrack.Presentation.View.Map.MapView, PermissionsListener, LocationEngineListener {
    private lateinit var mapView: MapView

    lateinit var presenter: MapPresenter


    // variables for adding location layer
    private var permissionsManager: PermissionsManager? = null
    private var locationPlugin: LocationLayerPlugin? = null
    private var locationEngine: LocationEngine? = null
    private var originLocation: Location? = null

    private var originPosition: Point? = null
    private var destinationPosition: Point? = null
    val APP_PREFERENCES = "mysettings"

    var settings: SharedPreferences? = null

    var jsonArray: JSONObject = JSONObject()


    private val latLngs = arrayListOf<LatLng>()

    var gson: Gson = Gson()

    private var startPoint: String? = null
    private var destinationPoint: String? = null
    private var waypoint: String? = null
    private var waypoints = arrayListOf<String>()

    private var jsonOrigin: JSONObject = JSONObject()
    private var jsonWaypoints: JSONArray = JSONArray()
    private var jsonDestination: JSONObject = JSONObject()

    private var notificationManager: NotificationManager? = null




    private val adapter = MapAdapter()

    companion object {

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater?.inflate(R.layout.map_box_fragment, container, false)

        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)


        Mapbox.getInstance(activity, getString(R.string.access_token))
        mapView = rootView!!.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        var i = 0
        presenter = MapPresenter()

        val startButton: Button = rootView.findViewById(R.id.startButton)
        startButton.setOnClickListener({
            Log.i("text", ""+ startButton.text)
            if (i > 0 ){

                stopTracking()
                AsyncTaskEx().cancel(true)
                startButton.text = "Start"
                i = 0
            } else {
                AsyncTaskEx().execute()
//                startTracking()
                startButton.text = "Stop"
                i += 1

            }


        })

        mapView.getMapAsync (adapter::attachMap)
        enableLocationPlugin()

        try {
            jsonArray = JSONObject(settings!!.all)
            settings!!.all.values.forEach{
                val lat = arrayListOf<LatLng>()
                jsonArray = JSONObject(it.toString())
//                Log.d("&&&", "" + jsonArray)
                jsonOrigin = JSONObject(jsonArray.getString("originPosition"))
                jsonWaypoints = JSONArray(jsonArray.getString("wayPoints"))
                jsonDestination = JSONObject(jsonArray.getString("destinationPosition"))
//                Log.d("&&&", "" + jsonOrigin)
                lat.add(LatLng(jsonOrigin.getDouble("latitude"), jsonOrigin.getDouble("longitude")))
                for (o in 0..jsonWaypoints.length()-1){
                    lat.add(LatLng(jsonWaypoints.getJSONObject(o).getDouble("latitude"), jsonWaypoints.getJSONObject(o).getDouble("longitude")))
//                    Log.e("jsonArray", ""+ jsonWaypoints.getJSONObject(o).getDouble("longitude"))
                }
                lat.add(LatLng(jsonDestination.getDouble("latitude"), jsonDestination.getDouble("longitude")))
                adapter.offer {
                    addPolyline(
                            PolylineOptions()
                                    .addAll(lat)
                                    .color(Color.parseColor("#3bb2d0"))
                                    .width(8F))

                }


            }


        } catch (t: Throwable){
            t.printStackTrace()
        }

        notificationManager =
                context.getSystemService(
                        Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(
                "com.example.vendox.citytrack.Presentation.View.Map",
                "NotifyDemo News",
                "Example News Channel")





        return rootView
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

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.detachMap()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    @SuppressLint("MissingPermission")
    override fun enableLocationPlugin() {
        if (PermissionsManager.areLocationPermissionsGranted(activity)){
            initializeLocationEngine()
            adapter.offer {
                locationPlugin = adapter.map!!.let { LocationLayerPlugin(mapView, it, locationEngine) }
                adapter.offer {
                    locationPlugin?.setLocationLayerEnabled(LocationLayerMode.TRACKING)
                }
            }


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
        adapter.offer {
        adapter.map?.animateCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(location.latitude, location.longitude), 13.0))}

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

    private fun startTracking(){


        val way = arrayListOf<Point>()



        val mLocTrackingInterval: Long = 1000 // 1 sec
        val trackingDistance: Float = 0F
        val trackingAccuracy: LocationAccuracy = LocationAccuracy.MEDIUM;

        val builder= LocationParams.Builder()
                .setAccuracy(trackingAccuracy)
                .setDistance(trackingDistance)
                .setInterval(mLocTrackingInterval)


        SmartLocation.with(activity)
                .location()
                .continuous()
                .config(builder.build())
                .start { location ->
                    originLocation = location

                    way.add(Point.fromLngLat(originLocation!!.longitude, originLocation!!.latitude))


                    if (way.size > 1){
                        val ind: Int = way.lastIndex

                        originPosition = way[ind -1]
                        destinationPosition = way[ind]


                        getRoute(originPosition!!, destinationPosition!!)


                    }
                }
    }
    fun getRoute(origin: Point, destination: Point) {
        val builder = NavigationRoute.builder()
                .accessToken(Mapbox.getAccessToken())
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .origin(origin)
                .destination(destination)

        builder.build()
                .getRoute(object : Callback<DirectionsResponse> {
                    override fun onFailure(call: Call<DirectionsResponse>?, t: Throwable) {
                        Log.e(FeedbackBottomSheet.TAG, "Error: " + t.printStackTrace())
                    }


                    override fun onResponse(call: Call<DirectionsResponse>?, response: Response<DirectionsResponse>?) {
                        Log.d(FeedbackBottomSheet.TAG, "Response code: " + response!!.code())
                        if (response.body() == null) {
                            Log.e(FeedbackBottomSheet.TAG, "No routes found, make sure you set the right user and access token.")
                            return
                        } else if (response.body()!!.routes().size < 1) {
                            Log.e(FeedbackBottomSheet.TAG, "No routes found")
                            return
                        }

                        val currentRoute: DirectionsRoute = response.body()!!.routes()[0]
                        drawPolyline(currentRoute)

                    }

                })
    }

    override fun drawPolyline(currentRoute: DirectionsRoute){


        val point: MutableList<Point>
        val lineString: LineString = LineString.fromPolyline(currentRoute.geometry()!!, Constants.PRECISION_6)
        point = lineString.coordinates()
        for (p in 0..point.size-1){
            latLngs.add(LatLng(point[p].latitude(), point[p].longitude()) )
        }

        Log.e("latlngs123",""+point)

            adapter.offer {
                addPolyline(
                        PolylineOptions()
                                .addAll(latLngs)
                                .color(Color.parseColor("#3bb2d0"))
                                .width(8F))

            }



    }

    @SuppressLint("CommitPrefEdits")
    fun stopTracking() {
        val jsonRoad: JSONObject = JSONObject()
        SmartLocation.with(activity)
                .location()
                .stop()
        SmartLocation.with(activity)
                .activity()
                .stop()
        if (!latLngs.isEmpty()) {
            startPoint = gson.toJson(latLngs[0])
            jsonRoad.put("originPosition", startPoint)


            for (p in 1..latLngs.size - 2) {
                waypoint = gson.toJson(latLngs[p])
                waypoints.add(waypoint!!)
            }
            jsonRoad.put("wayPoints", waypoints)

            destinationPoint = gson.toJson(latLngs.last())

            val way = java.util.UUID.randomUUID().toString()

            jsonRoad.put("destinationPosition", destinationPoint)

            val editor: SharedPreferences.Editor = settings!!.edit()
            editor.putString(way, jsonRoad.toString())
            editor.apply()


            Log.e("edit", "" + settings.toString())


            Log.e("Json", "" + jsonRoad)


            latLngs.clear()
        }
    }
    @SuppressLint("StaticFieldLeak")
    inner class AsyncTaskEx: AsyncTask<Void, Void,String>() {

        override fun doInBackground(vararg params: Void?): String? {
                startTracking()


            return null
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onPreExecute() {
            super.onPreExecute()
            sendNotification()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            // ...
        }
    }






    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(id: String, name: String,
                                          description: String) {

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(false)
        notificationManager?.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification() {

        val notificationID = 101

        val channelID = "com.example.vendox.citytrack.Presentation.View.Map"

        val notification = Notification.Builder(this.context,
                channelID)
                .setContentTitle("Example Notification")
                .setContentText("This is an  example notification.")
                .setSmallIcon(R.drawable.ic_arrow_up)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.logo))
                .setChannelId(channelID)
                .build()

        notificationManager?.notify(notificationID, notification)
    }


}




