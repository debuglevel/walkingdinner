package de.debuglevel.walkingdinner.backend.location.locator

import de.debuglevel.walkingdinner.backend.location.Location
import de.debuglevel.walkingdinner.backend.team.Team

interface Geolocator {
    fun initializeTeamLocation(team: Team)
    fun getLocation(address: String?, city: String): Location
}
