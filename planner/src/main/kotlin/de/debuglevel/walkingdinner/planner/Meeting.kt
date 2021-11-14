package de.debuglevel.walkingdinner.planner

import java.util.*

/**
 * A [Meeting] of [teams] which happens during a [course].
 */
data class Meeting(
    val course: String,
    /**
     * TODO: Maybe throw an exception somehow if count() is not 3
     */
    val teams: List<Team>,
    /**
     * @implNote: In case of [GeneticPlanner], this is only set after the final [Plan] is ready.
     */
    var id: UUID? = null
) {
    /**
     * Get the [Team] which is cooking in this [Meeting].
     * @implNote The cooking [Team] is defined as the first one in the list.
     */
    fun getCookingTeam(): Team {
        return teams.first()
    }

    /**
     * Get the [Location] where this [Meeting] happens.
     * @implNote This is always the [Location] of the cooking [Team].
     */
    fun getLocation(): Location {
        return getCookingTeam().location
    }
}
