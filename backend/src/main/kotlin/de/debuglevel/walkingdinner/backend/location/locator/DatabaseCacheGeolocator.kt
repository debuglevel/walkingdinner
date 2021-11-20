package de.debuglevel.walkingdinner.backend.location.locator

import de.debuglevel.walkingdinner.backend.location.Location
import de.debuglevel.walkingdinner.backend.location.LocationService
import jakarta.inject.Singleton
import mu.KotlinLogging

@Singleton
class DatabaseCacheGeolocator(
    private val locationService: LocationService,
    private val nominatimApiGeolocator: NominatimApiGeolocator,
) : Geolocator {
    private val logger = KotlinLogging.logger {}

    private val fallbackGeolocator: Geolocator = nominatimApiGeolocator

    override fun getLocation(address: String?, city: String): Location {
        logger.debug { "Getting location for address='$address' in city='$city' ..." }

        val cachedLocations =
            locationService.getAll() // TODO: quick hack due to migration from JsonDB; but probably a performance nightmare

        // HACK: somehow handle the "address, city" concatenation and city lookup
        val cachedLocation = if (address != null) {
            cachedLocations.firstOrNull { it.address == "$address, $city" }
        } else {
            cachedLocations.firstOrNull { it.address == city }
        }

        val persistentLocation = if (cachedLocation == null) {
            logger.debug("Location with address='$address' in city='$city' not found in caching database. Using fallback Geolocator...")
            val locatedLocation = fallbackGeolocator.getLocation(address, city)

            val savedLocation = locationService.save(locatedLocation)
            savedLocation
        } else {
            logger.debug("Got location for address='$address' in city='$city' (found in caching database): $cachedLocation")
            cachedLocation
        }

        return persistentLocation
    }
}