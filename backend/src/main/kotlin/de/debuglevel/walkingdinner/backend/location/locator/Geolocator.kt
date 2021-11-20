package de.debuglevel.walkingdinner.backend.location.locator

import de.debuglevel.walkingdinner.backend.location.Location

interface Geolocator {
    fun getLocation(address: String?, city: String): Location
}
