package de.debuglevel.walkingdinner.backend.plan.calculation.client

import de.debuglevel.walkingdinner.backend.participant.location.Location

class LocationRequest(
    val lng: Double,
    val lat: Double
) {
    constructor(location: Location) : this(
        lng = location.longitude,
        lat = location.latitude
    )
}