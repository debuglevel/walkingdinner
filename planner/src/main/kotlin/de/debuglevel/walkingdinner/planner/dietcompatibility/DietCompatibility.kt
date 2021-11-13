package de.debuglevel.walkingdinner.planner.dietcompatibility

import de.debuglevel.walkingdinner.planner.Meeting

interface DietCompatibility {
    fun areCompatibleTeams(meeting: Meeting): Boolean
}