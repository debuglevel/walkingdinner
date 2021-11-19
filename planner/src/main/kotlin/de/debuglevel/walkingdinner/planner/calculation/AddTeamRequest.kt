package de.debuglevel.walkingdinner.planner.calculation

import de.debuglevel.walkingdinner.planner.dietcompatibility.CookingCapability
import de.debuglevel.walkingdinner.planner.dietcompatibility.Diet
import de.debuglevel.walkingdinner.planner.team.Team
import java.util.*

data class AddTeamRequest(
    val id: UUID,
    val diet: Diet,
    val cookingCapabilities: List<CookingCapability>,
    val location: AddLocationRequest
) {
    fun toTeam(): Team {
        return Team(id, diet, cookingCapabilities, location.toLocation())
    }
}