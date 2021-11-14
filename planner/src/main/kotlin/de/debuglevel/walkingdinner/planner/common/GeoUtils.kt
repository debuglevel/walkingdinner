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
     * @implNote Uses the Haversine formula
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