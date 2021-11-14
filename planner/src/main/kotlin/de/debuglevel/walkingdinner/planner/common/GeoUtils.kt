package de.debuglevel.walkingdinner.planner.common

import de.debuglevel.walkingdinner.planner.Location
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object GeoUtils {
    /**
     * Cache for already calculated distances
     *
     * @remarks Microbenchmark with JMH: about 4 times faster with caching.
     */
    private val distances = hashMapOf<Pair<Location, Location>, Double>()

    private const val AVERAGE_RADIUS_OF_EARTH_KM = 6371.0

    /**
     * Calculates the distance (in kilometers) between to [Location]s and caches the result.
     */
    fun cachedCalculateDistance(
        source: Location,
        destination: Location
    ): Double {
        // Retrieve distance from cache if already calculated
        val distance = distances.computeIfAbsent(source to destination) { calculateDistance(source, destination) }

        return distance
    }

    /**
     * Calculates the distance (in kilometers) between to [Location]s.
     * @implNote Uses the Haversine formula (see https://stackoverflow.com/a/27943/4764279)
     */
    fun calculateDistance(
        source: Location,
        destination: Location
    ): Double {
        val latitudeDistance = Math.toRadians(source.latitude - destination.latitude)
        val longitudeDistance = Math.toRadians(source.longitude - destination.longitude)

        // Performance: Calculating sin() only once is 1.3 times faster
        val sinLatitudeDistance = sin(latitudeDistance / 2)
        val sinLongitudeDistance = sin(longitudeDistance / 2)
        val a =
            (sinLatitudeDistance * sinLatitudeDistance) + (
                    cos(Math.toRadians(source.latitude)) *
                            cos(Math.toRadians(destination.latitude)) *
                            sinLongitudeDistance
                            * sinLongitudeDistance)

        // There Haversine formula uses asin() while the sample code at https://stackoverflow.com/a/27943/4764279 uses atan2().
        // Performance: According to my own benchmark the atan2() variant is 3-4 times faster.
        //val c = asin(a) // slower variant, but original to the Haversine formula
        val c = atan2(sqrt(a), sqrt(1 - a))

        // Performance: Multiplying by 2 does not affect speed.
        val distance = 2 * AVERAGE_RADIUS_OF_EARTH_KM * c
        return distance
    }

    /**
     * Calculates the distance (in kilometers) between to [Location]s.
     * @implNote Uses the Keerthana Gopalakrishnan's formula (see https://stackoverflow.com/a/49916544/4764279, https://stackoverflow.com/a/37870363/4764279) which might to be more precise, but 2 times slower.
     */
    fun calculateDistanceKeerthana(
        source: Location,
        destination: Location
    ): Double {
        val a = 6378.137 // equatorial radius in km
        val b = 6356.752 // polar radius in km

        val square = { x: Double -> (x * x) }
        val radius =
            { latitude: Double ->
                sqrt(
                    (square(a * a * cos(latitude)) + square(b * b * sin(latitude))) / (square(a * cos(latitude)) + square(
                        b * sin(latitude)
                    ))
                )
            }

        val latitude1 = source.latitude * Math.PI / 180
        val longitude1 = source.longitude * Math.PI / 180
        val latitude2 = destination.latitude * Math.PI / 180
        val longitude2 = destination.longitude * Math.PI / 180

        val radius1 = radius(latitude1)
        val x1 = radius1 * cos(latitude1) * cos(longitude1)
        val y1 = radius1 * cos(latitude1) * sin(longitude1)
        val z1 = radius1 * sin(latitude1)

        val radius2 = radius(latitude2)
        val x2 = radius2 * cos(latitude2) * cos(longitude2)
        val y2 = radius2 * cos(latitude2) * sin(longitude2)
        val z2 = radius2 * sin(latitude2)

        return sqrt(square(x1 - x2) + square(y1 - y2) + square(z1 - z2))
    }

    /**
     * Calculate the distance (in kilometers) between multiple [locations].
     * TODO: add a test
     */
    fun calculateLocationsDistance(locations: List<Location>): Double {
        val distance = locations
            .windowed(2, 1, false) {
                val source = it[0]
                val destination = it[1]
                cachedCalculateDistance(source, destination)
            }.sum()

        return distance
    }
}