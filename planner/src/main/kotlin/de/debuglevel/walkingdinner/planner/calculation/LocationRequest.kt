package de.debuglevel.walkingdinner.planner.calculation

import de.debuglevel.walkingdinner.planner.Location

class LocationRequest(
    val lng: Double,
    val lat: Double
) {
    fun toLocation(): Location {
        return Location(lng, lat)
    }
}
