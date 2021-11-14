package de.debuglevel.walkingdinner.planner.common.geo

import de.debuglevel.walkingdinner.planner.Location
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object HaversineDistanceCalculator : DistanceCalculator {
    private val AVERAGE_RADIUS = 6371.0

    /**
     * Calculates the distance (in kilometers) between to [Location]s according to the Haversine formula.
     * @implNote based on https://stackoverflow.com/a/27943/4764279
     */
    override fun calculate(
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
        val distance = 2 * AVERAGE_RADIUS * c
        return distance
    }
}