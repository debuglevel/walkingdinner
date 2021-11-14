package de.debuglevel.walkingdinner.planner.common.geo

import de.debuglevel.walkingdinner.planner.Location

object GeoUtils {
    /**
     * Cache for already calculated distances
     *
     * @remarks Microbenchmark with JMH: about 4 times faster with caching.
     */
    private val distances = hashMapOf<Pair<Location, Location>, Double>()

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

    private fun calculateDistance(source: Location, destination: Location): Double {
        return HaversineDistanceCalculator.calculate(source, destination)
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