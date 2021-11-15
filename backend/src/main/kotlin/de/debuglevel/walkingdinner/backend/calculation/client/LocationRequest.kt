package de.debuglevel.walkingdinner.backend.calculation.client

import de.debuglevel.walkingdinner.backend.team.location.Location

class LocationRequest(
    val lng: Double,
    val lat: Double
) {
    constructor(location: Location) : this(
        lng = location.longitude,
        lat = location.latitude
    )
}