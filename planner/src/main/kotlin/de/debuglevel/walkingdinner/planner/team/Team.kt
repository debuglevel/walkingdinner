package de.debuglevel.walkingdinner.planner.team

import de.debuglevel.walkingdinner.planner.dietcompatibility.CookingCapability
import de.debuglevel.walkingdinner.planner.dietcompatibility.Diet
import de.debuglevel.walkingdinner.planner.location.Location
import java.util.*

/**
 * A [Team] which has a certain [diet], some [cookingCapabilities] and cooks at a [location].
 */
data class Team(
    val id: UUID,
    val diet: Diet,
    val cookingCapabilities: List<CookingCapability>,
    val location: Location
)