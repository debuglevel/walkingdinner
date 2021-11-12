package de.debuglevel.walkingdinner.rest.participant.location.locator

import de.debuglevel.walkingdinner.rest.common.GeoUtils
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.participant.location.Location
import de.debuglevel.walkingdinner.rest.participant.location.LocationService
import mu.KotlinLogging
import java.text.DecimalFormat
import javax.inject.Singleton

@Singleton
class DatabasecacheGeolocator(
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

    override fun initializeTeamLocation(team: Team) {
        logger.debug("Geo-locating $team (city '${team.city}', address '${team.address}') by caching database...")
        val location = getLocation(team.address, team.city)
        team.location = location

        val cityLocation = getLocation(null, team.city)

        val centerDistance = GeoUtils.calculateDistanceInKilometer(cityLocation, location)
        logger.debug("Geo-located $team ${DecimalFormat("#.##").format(centerDistance)}km from town center by caching database")
    }
}