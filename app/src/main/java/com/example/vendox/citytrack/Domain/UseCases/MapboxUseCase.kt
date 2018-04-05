package com.example.vendox.citytrack.Domain.UseCases

import android.graphics.Color
import android.util.Log
import com.example.vendox.citytrack.Data.Repository.Route
import com.example.vendox.citytrack.Presentation.View.Map.MapFragment
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.annotations.PolylineOptions
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.services.Constants

/**
 * Created by vehdox on 05.04.18.
 */
class MapboxUseCase(var route: Route){
    fun drawPolyline(currentRoute: DirectionsRoute){

        val latLngs = arrayListOf<LatLng>()
        var point = mutableListOf<Point>()
        val lineString: LineString = LineString.fromPolyline(currentRoute.geometry()!!, Constants.PRECISION_6)
        point = lineString.coordinates()
        for (p in 0..point.size-1){
            latLngs.add(LatLng(point[p].latitude(), point[p].longitude()) )
        }

        Log.e("latlngs123",""+point)

        MapFragment().map!!.addPolyline(
                PolylineOptions()
                        .addAll(latLngs)
                        .color(Color.parseColor("#3bb2d0"))
                        .width(3F))

    }
}