package com.example.vendox.citytrack.Presentation.View.Map


import android.graphics.Color
import android.location.Location
import android.util.Log
import com.example.vendox.citytrack.Data.Repository.GetRoute
import com.example.vendox.citytrack.Domain.UseCases.MapboxUseCase
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.annotations.PolylineOptions
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.services.Constants
import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.location.config.LocationAccuracy
import io.nlopez.smartlocation.location.config.LocationParams


/**
 * Created by vehdox on 05.04.18.
 */
class MapPresenter {

   private val adapter = MapAdapter()

   fun drawPolyline(currentRoute: DirectionsRoute) {

      val latLngs = arrayListOf<LatLng>()
      val point: MutableList<Point>
      val lineString: LineString = LineString.fromPolyline(currentRoute.geometry()!!, Constants.PRECISION_6)
      point = lineString.coordinates()
      for (p in 0..point.size - 1) {
         latLngs.add(LatLng(point[p].latitude(), point[p].longitude()))
      }

      Log.e("latlngs123", "" + point)


      adapter.offer {
         addPolyline(
                 PolylineOptions()
                         .addAll(latLngs)
                         .color(Color.parseColor("#3bb2d0"))
                         .width(8F))

      }

      fun startTracking() {

         var originLocation: Location
         var originPosition: Point?
         var destinationPosition: Point?

         var way = arrayListOf<Point>()


         var i = 0

         val mLocTrackingInterval: Long = 1000 * 5 // 5 sec
         val trackingDistance: Float = 0F;
         val trackingAccuracy: LocationAccuracy = LocationAccuracy.HIGH;

         val builder = LocationParams.Builder()
                 .setAccuracy(trackingAccuracy)
                 .setDistance(trackingDistance)
                 .setInterval(mLocTrackingInterval)


         SmartLocation.with(MapFragment().activity)
                 .location()
                 .continuous()
                 .config(builder.build())
                 .start(object : OnLocationUpdatedListener {
                    override fun onLocationUpdated(location: Location) {

                       originLocation = location

                       way.add(Point.fromLngLat(originLocation.longitude, originLocation.latitude))


                       if (way.size > 1) {
                          val ind: Int = way.lastIndex

                          originPosition = way[ind - 1]
                          destinationPosition = way[ind]
                          GetRoute().getRoute(originPosition!!, destinationPosition!!)

                       }
                    }


                 })
      }

      fun stopTracking() {
         SmartLocation.with(MapFragment().activity)
                 .location()
                 .stop()
         SmartLocation.with(MapFragment().activity)
                 .activity()
                 .stop()
      }
   }
}