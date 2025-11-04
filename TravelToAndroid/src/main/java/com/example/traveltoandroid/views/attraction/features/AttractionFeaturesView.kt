package com.example.traveltoandroid.views.attraction.features

import android.content.Context
import androidx.compose.runtime.Composable
import com.example.traveltoandroid.views.attraction.features.models.ParkFacilityModel

@Composable
fun AttractionFeaturesView(
    attractionType: String,
    context: Context,
    parkFacilities: MutableList<ParkFacilityModel>
) {
    when (attractionType) {
        "Museum" -> MuseumFeatureView(context)
        "Gallery" -> GalleryFeatureView(context)
        "Park" -> ParkFeatureView(parkFacilities)
        "Religious" -> ReligiousFeatureView()
        "Cafe" -> CafeFeatureView() // TODO
        "Restaurant" -> RestaurantFeatureView() // TODO
        "Theater" -> TheaterFeatureView(context)
        "Hotel" -> HotelFeatureView() // TODO
    }
}

