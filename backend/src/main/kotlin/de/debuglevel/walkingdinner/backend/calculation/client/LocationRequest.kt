package de.debuglevel.walkingdinner.backend.calculation.client

import de.debuglevel.walkingdinner.backend.location.Location

class LocationRequest(
    val lng: Double,
    val lat: Double
) {
    constructor(location: Location) : this(
        lng = location.longitude,
        lat = location.latitude
    )
}