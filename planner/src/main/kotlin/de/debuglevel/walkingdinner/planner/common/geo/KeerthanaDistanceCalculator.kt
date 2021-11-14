package de.debuglevel.walkingdinner.planner.common.geo

import de.debuglevel.walkingdinner.planner.Location
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object KeerthanaDistanceCalculator : DistanceCalculator {
    const val EQUATRIAL_RADIUS = 6378.137
    const val POLAR_RADIUS = 6356.752

    /**
     * Calculates the distance (in kilometers) between to [Location]s according to Keerthana Gopalakrishnan's formula.
     * See https://stackoverflow.com/a/49916544/4764279 and https://stackoverflow.com/a/37870363/4764279
     * @implNote Might to be more precise, but 2 times slower than [HaversineDistanceCalculator].
     */
    override fun calculate(
        source: Location,
        destination: Location
    ): Double {
        val square = { x: Double -> x * x }

        val radius =
            { latitude: Double ->
                sqrt(
                    (square(EQUATRIAL_RADIUS * EQUATRIAL_RADIUS * cos(latitude)) +
                            square(
                                POLAR_RADIUS * POLAR_RADIUS * sin(latitude)
                            )) / (square(EQUATRIAL_RADIUS * cos(latitude)) + square(POLAR_RADIUS * sin(latitude)))
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
}