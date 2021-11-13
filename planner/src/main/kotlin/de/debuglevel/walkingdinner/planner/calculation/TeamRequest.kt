package de.debuglevel.walkingdinner.planner.calculation

import de.debuglevel.walkingdinner.planner.Team
import de.debuglevel.walkingdinner.planner.dietcompatibility.CookingCapability
import de.debuglevel.walkingdinner.planner.dietcompatibility.Diet
import java.util.*

data class TeamRequest(
    val id: UUID,
    val diet: Diet,
    val cookingCapabilities: List<CookingCapability>,
    val location: LocationRequest
) {
    fun toTeam(): Team {
        return Team(id, diet, cookingCapabilities, location.toLocation())
    }
}