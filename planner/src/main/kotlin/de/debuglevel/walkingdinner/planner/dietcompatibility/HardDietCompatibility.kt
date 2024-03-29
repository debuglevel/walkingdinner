package de.debuglevel.walkingdinner.planner.dietcompatibility

import de.debuglevel.walkingdinner.planner.meeting.Meeting
import de.debuglevel.walkingdinner.planner.team.Team
import mu.KotlinLogging

/**
 * This diet compatibility is defined by a simple "if all teams have the same diet, they are compatible".
 * With 4 diets, this will result in 4 virtual dinners running in parallel.
 */
object HardDietCompatibility : DietCompatibility {
    private val logger = KotlinLogging.logger {}

    override fun isCompatible(meeting: Meeting): Boolean {
        return meeting.teams.all {
            isCompatibleDiet(
                meeting.teams.first(),
                it
            )
        }
    }

    private fun isCompatibleDiet(a: Team, b: Team): Boolean {
        return a.diet == b.diet
    }
}