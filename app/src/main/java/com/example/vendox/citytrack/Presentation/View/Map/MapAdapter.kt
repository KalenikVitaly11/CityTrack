package com.example.vendox.citytrack.Presentation.View.Map

import android.util.Log
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapboxMap
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

/**
 * Created by vehdox on 11.04.18.
 */
class MapAdapter {

         var map: MapboxMap?                   = null
        private val cmds: Queue<MapboxMap.() -> Unit> = LinkedBlockingQueue()

        fun attachMap(map: MapboxMap) {
            this.map = map

            val bounds = LatLngBounds.Builder()
                        .include(LatLng(56.0431162,37.5329372)) // Northeast
                        .include(LatLng(55.4725803, 37.7006232))
                        .include(LatLng(55.6368534, 37.4429595))
                        .include(LatLng(55.7499363, 37.9918402))// Southwest
                        .build()
                    map.setLatLngBoundsForCameraTarget(bounds)

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





}