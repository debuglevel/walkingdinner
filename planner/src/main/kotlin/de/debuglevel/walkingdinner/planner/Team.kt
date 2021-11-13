package de.debuglevel.walkingdinner.planner

import de.debuglevel.walkingdinner.planner.dietcompatibility.CookingCapability
import de.debuglevel.walkingdinner.planner.dietcompatibility.Diet
import java.util.*

data class Team(
    val id: UUID,
    val diet: Diet,
    val cookingCapabilities: List<CookingCapability>,
    val location: Location
)