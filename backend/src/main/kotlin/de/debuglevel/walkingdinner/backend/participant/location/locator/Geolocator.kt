package de.debuglevel.walkingdinner.backend.participant.location.locator

import de.debuglevel.walkingdinner.backend.participant.Team
import de.debuglevel.walkingdinner.backend.participant.location.Location

interface Geolocator {
    fun initializeTeamLocation(team: Team)
    fun getLocation(address: String?, city: String): Location
}
