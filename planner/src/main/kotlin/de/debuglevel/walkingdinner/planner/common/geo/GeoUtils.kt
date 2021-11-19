package de.debuglevel.walkingdinner.planner.common.geo

import de.debuglevel.walkingdinner.planner.location.Location
import mu.KotlinLogging
import java.util.concurrent.ConcurrentHashMap

object GeoUtils {
    private val logger = KotlinLogging.logger {}

    /**
     * Cache for already calculated distances
     *
     * @remarks Microbenchmark with JMH: about 4 times faster with caching.
     */
    private val distances = ConcurrentHashMap<Pair<Location, Location>, Double>()

    /**
     * Calculates the distance (in kilometers) between to [Location]s and caches the result.
     */
    fun cachedCalculateDistance(
        source: Location,
        destination: Location
    ): Double {
        logger.trace { "Calculating (or getting from cache) distance between $source and $destination..." }

        // Retrieve distance from cache if already calculated
        val distance = distances.computeIfAbsent(source to destination) { calculateDistance(source, destination) }

        logger.trace { "Calculated (or got from cache) distance between $source and $destination: $distance" }
        return distance
    }

    private fun calculateDistance(source: Location, destination: Location): Double {
        logger.trace { "Calculating distance between $source and $destination..." }

        val distance = HaversineDistanceCalculator.calculate(source, destination)

        logger.trace { "Calculated distance between $source and $destination: $distance" }
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