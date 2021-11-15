package de.debuglevel.walkingdinner.backend.calculation.client

import de.debuglevel.walkingdinner.backend.team.CookingCapability
import de.debuglevel.walkingdinner.backend.team.Diet
import de.debuglevel.walkingdinner.backend.team.Team
import java.util.*

data class TeamRequest(
    val id: UUID,
    val diet: Diet,
    val cookingCapabilities: List<CookingCapability>,
    val location: LocationRequest
) {
    constructor(team: Team) : this(
        id = team.id!!,
        diet = team.diet,
        cookingCapabilities = team.cookingCapabilities,
        location = LocationRequest(team.location!!)
    )
}