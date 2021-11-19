package de.debuglevel.walkingdinner.planner.common.geo

import de.debuglevel.walkingdinner.planner.location.Location

/**
 * https://github.com/sandip-jadhav/aphoteg/blob/21ee7b0f78bde4f8b3c40a2eec1532bcd9a07c0c/src/photoassociation/qizx/VincentyDistanceCalculator.java
 */
object OopVincentyDistanceCalculator : DistanceCalculator {
    override fun calculate(
        source: Location,
        destination: Location
    ): Double {
        return VincentyDistanceCalculator.getDistance(
            source.latitude,
            source.longitude,
            destination.latitude,
            destination.longitude
        )
    }

}