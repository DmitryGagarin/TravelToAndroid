package com.example.myapplication.views.attraction.features

import androidx.compose.runtime.Composable

@Composable
fun AttractionFeaturesView(attractionType: String) {
    when (attractionType) {
        "Museum" -> MuseumFeatureView()
        "Gallery" -> GalleryFeatureView() // DONE
        "Park" -> ParkFeatureView() // DONE
        "Religious" -> ReligiousFeatureView() // DONE
        "Cafe" -> CafeFeatureView() // TODO
        "Restaurant" -> RestaurantFeatureView() // TODO
        "Theater" -> TheaterFeatureView() // DONE
        "Hotel" -> HotelFeatureView() // TODO
    }
}

