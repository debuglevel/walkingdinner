package de.debuglevel.walkingdinner.backend.team.location.locator

import de.debuglevel.walkingdinner.backend.team.Team
import de.debuglevel.walkingdinner.backend.team.location.Location

interface Geolocator {
    fun initializeTeamLocation(team: Team)
    fun getLocation(address: String?, city: String): Location
}
