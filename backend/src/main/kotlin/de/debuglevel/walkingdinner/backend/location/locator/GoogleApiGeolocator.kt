package de.debuglevel.walkingdinner.backend.location.locator

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import de.debuglevel.walkingdinner.backend.location.Location
import jakarta.inject.Singleton
import mu.KotlinLogging

// TODO: does probably not really work anymore; should be aligned to NominatimApiGeolocator
@Singleton
class GoogleApiGeolocator(private val city: String) : Geolocator {
    private val logger = KotlinLogging.logger {}

    private val geoCodingApi = GeoApiContext.Builder()
        .apiKey("AIzaSyB9eOPva1kx-_p4R7taF42DoF2ZdRNsuZs")
        .build()

    private val cityLocation: Location

    init {
        val result = GeocodingApi
            .geocode(geoCodingApi, city)
            .await()
            .first()
        cityLocation = Location(
            city,
            result.geometry.location.lng,
            result.geometry.location.lat
        )
    }

    override fun getLocation(address: String?, city: String): Location {
        val cityAddress = if (address.isNullOrBlank()) city else "$address, $city"

        val result = GeocodingApi
            .geocode(geoCodingApi, "$address $city")
            .await()
            .first()

        return Location(
            cityAddress,
            result.geometry.location.lng,
            result.geometry.location.lat
        )
    }
}