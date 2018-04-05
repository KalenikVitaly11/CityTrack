package com.example.vendox.citytrack.Data.Repository

import com.mapbox.geojson.Point

/**
 * Created by vehdox on 05.04.18.
 */
interface Route {
    fun getRoute(origin: Point, destination: Point)
}