package com.example.myapplication.views.attraction.features

import android.content.Context
import androidx.compose.runtime.Composable

@Composable
fun AttractionFeaturesView(attractionType: String, context: Context) {
    when (attractionType) {
        "Museum" -> MuseumFeatureView(context)
        "Gallery" -> GalleryFeatureView(context)
        "Park" -> ParkFeatureView()
        "Religious" -> ReligiousFeatureView()
        "Cafe" -> CafeFeatureView() // TODO
        "Restaurant" -> RestaurantFeatureView() // TODO
        "Theater" -> TheaterFeatureView(context)
        "Hotel" -> HotelFeatureView() // TODO
    }
}

