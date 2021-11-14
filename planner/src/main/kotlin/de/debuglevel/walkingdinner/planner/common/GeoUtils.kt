package de.debuglevel.walkingdinner.planner.common

import de.debuglevel.walkingdinner.planner.Location
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin

object GeoUtils {
    /**
     * Cache for already calculated distances
     *
     * @remarks Microbenchmark: w/ cache 5000ms for 100.000.000 requests, w/o cache 8000ms.
     */
    private val distances = hashMapOf<Pair<Location, Location>, Double>()

    private const val AVERAGE_RADIUS_OF_EARTH_KM = 6371.0

    /**
     * Calculates te distance (in kilometers) between to [Location]s and caches the result.
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
     * Calculates te distance (in kilometers) between to [Location]s.
     * @implNote Uses the Haversine formula
     */
    fun calculateDistance(
        source: Location,
        destination: Location
    ): Double {
        val latitudeDistance = Math.toRadians(source.latitude - destination.latitude)
        val longitudeDistance = Math.toRadians(source.longitude - destination.longitude)

        val a =
            (sin(latitudeDistance / 2)
                    * sin(latitudeDistance / 2)
                    ) + (
                    cos(Math.toRadians(source.latitude)) *
                            cos(Math.toRadians(destination.latitude)) *
                            sin(longitudeDistance / 2)
                            * sin(longitudeDistance / 2))

        // TODO: Microbenchmark which one is faster
        //val c = atan2(sqrt(a), sqrt(1 - a))
        val c = asin(a)

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