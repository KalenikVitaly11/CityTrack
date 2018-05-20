package com.example.vendox.citytrack.Presentation.View.Map

import android.graphics.PointF
import android.icu.text.CollationKey
import android.preference.PreferenceScreen
import android.util.Log
import com.mapbox.geojson.BoundingBox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdate
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.geometry.VisibleRegion
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Projection
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

/**
 * Created by vehdox on 11.04.18.
 */
class MapAdapter {

         var map: MapboxMap?                   = null
        private val cmds: Queue<MapboxMap.() -> Unit> = LinkedBlockingQueue()
        val NE_LIMIT = LatLng(56.094233, 37.530489)
        val SW_LIMIT = LatLng(55.329547, 37.664157)

        fun attachMap(map: MapboxMap) {
            this.map = map
            map.setMaxZoomPreference(21.0)
            map.setMinZoomPreference(10.0)



            val exbound = LatLngBounds.Builder()
                    .include(NE_LIMIT)
                    .include(SW_LIMIT)
                    .build()

//            map.a ({
//                map.easeCamera(CameraUpdateFactory.newLatLngBounds(exbound, 10))
//                map.animateCamera(CameraUpdateFactory.newLatLngBounds(exbound,10))
//            })





            val bounds = LatLngBounds.Builder()
                        .include(LatLng(56.0431162,37.5329372)) // Northeast
                        .include(LatLng(55.4725803, 37.7006232))
                        .include(LatLng(55.6368534, 37.4429595))
                        .include(LatLng(55.7499363, 37.9918402))// Southwest
                        .build()


            val maxLat = NE_LIMIT.latitude
            val maxLng = NE_LIMIT.longitude
            val minLat = SW_LIMIT.latitude
            val minLng = SW_LIMIT.longitude


//                val visibleRegion: VisibleRegion = map.projection.visibleRegion
//                val topScreen = map.projection.fromScreenLocation(PointF(0F, 0F))
//                if (topScreen.latitude > NE_LIMIT)
//                if (topScreen.longitude > NE_LIMIT.longitude || topScreen.latitude > NE_LIMIT.latitude){
//                    map.easeCamera(CameraUpdateFactory.newLatLngBounds(exbound, 10 ), 500)
//                }
//                if (projection.longitude > SW_LIMIT.longitude || projection.latitude > SW_LIMIT.latitude){
//                    map.easeCamera(CameraUpdateFactory.newLatLngBounds(exbound, 10 ), 500)
//                }
//                Log.e("vis", ""+ topScreen)









//            map.addOnScrollListener({if( !(visibleRegion.farLeft.getLatitude() >= minLat && visibleRegion.farLeft.getLatitude() <= maxLat
//                            && visibleRegion.farLeft.getLongitude() >= minLng && visibleRegion.farLeft.getLongitude() <= maxLng) ) {
//                easeCameraBackToBoundingBox();
//            }
//                if( !(visibleRegion.farRight.getLatitude() >= minLat && visibleRegion.farRight.getLatitude() <= maxLat
//                                && visibleRegion.farRight.getLongitude() >= minLng && visibleRegion.farRight.getLongitude() <= maxLng) ) {
//                    easeCameraBackToBoundingBox();
//                }
//                if( !(visibleRegion.nearLeft.getLatitude() >= minLat && visibleRegion.nearLeft.getLatitude() <= maxLat
//                                && visibleRegion.nearLeft.getLongitude() >= minLng && visibleRegion.nearLeft.getLongitude() <= maxLng) ) {
//                    easeCameraBackToBoundingBox();
//                }
//                if( !(visibleRegion.nearRight.getLatitude() >= minLat && visibleRegion.nearRight.getLatitude() <= maxLat
//                                && visibleRegion.nearRight.getLongitude() >= minLng && visibleRegion.nearRight.getLongitude() <= maxLng) ) {
//                    easeCameraBackToBoundingBox();
//                }
//            map.setMaxZoomPreference(25.0)
//            map.setMinZoomPreference(6.0)})
//            if (VisibleRegion(LatLng(56.0431162,37.5329372), LatLng(55.4725803, 37.7006232),
//                            LatLng(55.6368534, 37.4429595), LatLng(55.7499363, 37.9918402), bounds ).latLngBounds.)
//                                                 map.setLatLngBoundsForCameraTarget(bounds)})
//                    map.setLatLngBoundsForCameraTarget(bounds)
            map.easeCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0))

            Log.e("map", ""+map)
            while (cmds.isNotEmpty()) {
                map.let(cmds.poll())
            }

        }

        fun offer(cmd: MapboxMap.() -> Unit) {
            val map = map
            if (map == null) {
                cmds.offer(cmd)
                return
            }

            map.let(cmd)
        }

        fun detachMap() {
            this.map = null
            this.cmds.clear()
        }

    fun easeCameraBackToBoundingBox() {
        val latLngBounds = LatLngBounds.Builder()
                .include(NE_LIMIT) // Northeast
                .include(SW_LIMIT) // Southwest
                .build()

        map!!.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 10 ), 500)
    }





}