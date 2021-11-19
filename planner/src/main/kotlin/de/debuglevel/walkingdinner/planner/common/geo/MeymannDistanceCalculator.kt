package de.debuglevel.walkingdinner.planner.common.geo

import de.debuglevel.walkingdinner.planner.location.Location
import kotlin.math.cos
import kotlin.math.sqrt

object MeymannDistanceCalculator : DistanceCalculator {
    const val EQUATOR_PERIMETER = 40000000.0
    const val GREENWICH_PERIMETER = 39940651.0

    /**
     * Calculates the distance (in kilometers) between to [Location]s according to Meymann's formula on https://stackoverflow.com/a/35309170/4764279
     */
    override fun calculate(
        source: Location,
        destination: Location
    ): Double {
        val square = { x: Double -> x * x }
        val cosDeg = { x: Double -> cos(x * Math.PI / 180.0) }

        val earthCyclePerimeter = EQUATOR_PERIMETER * cosDeg((source.latitude + destination.latitude) / 2.0)
        val dx = (source.longitude - destination.longitude) * earthCyclePerimeter / 360.0
        val dy = GREENWICH_PERIMETER * (source.latitude - destination.latitude) / 360.0

        return sqrt(square(dx) + square(dy)) / 1000.0
    }
}