package de.debuglevel.walkingdinner.planner.dietcompatibility

import de.debuglevel.walkingdinner.planner.Meeting

interface DietCompatibility {
    /**
     * Check if this [Meeting] is (according to their diet) compatible.
     * Implementations might take different approaches to check this.
     */
    fun isCompatible(meeting: Meeting): Boolean
}