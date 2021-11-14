package de.debuglevel.walkingdinner.planner

import de.debuglevel.walkingdinner.planner.dietcompatibility.CookingCapability
import de.debuglevel.walkingdinner.planner.dietcompatibility.Diet
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