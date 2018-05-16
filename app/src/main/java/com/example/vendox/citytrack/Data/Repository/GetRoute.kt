package com.example.vendox.citytrack.Data.Repository

import android.util.Log
import com.example.vendox.citytrack.Domain.UseCases.MapboxUseCase
import com.example.vendox.citytrack.Presentation.View.Map.MapFragment
import com.example.vendox.citytrack.Presentation.View.Map.MapPresenter
import com.example.vendox.citytrack.Presentation.View.Map.MapView
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.services.android.navigation.ui.v5.feedback.FeedbackBottomSheet
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import com.mapbox.services.api.directions.v5.DirectionsCriteria
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by vehdox on 05.04.18.
 */
class GetRoute{
    fun getRoute(origin: Point, destination: Point) {
        val builder = NavigationRoute.builder()
                .accessToken(Mapbox.getAccessToken())
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .origin(origin)
                .destination(destination)

        builder.build()
                .getRoute(object : Callback<DirectionsResponse> {
                    override fun onFailure(call: Call<DirectionsResponse>?, t: Throwable) {
                        Log.e(FeedbackBottomSheet.TAG, "Error: " + t.printStackTrace());
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

                        val currentRoute: DirectionsRoute = response.body()!!.routes().get(0)
                        MapFragment().drawPolyline(currentRoute)

                    }

                })
    }
}