package de.debuglevel.walkingdinner.planner.meeting

import de.debuglevel.walkingdinner.planner.location.Location
import de.debuglevel.walkingdinner.planner.team.Team
import java.util.*

/**
 * A [Meeting] of [teams] which happens during a [courseName].
 */
data class Meeting(
    val courseName: String,
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

    override fun toString(): String {
        return "Meeting(" +
                "courseName='$courseName', " +
                "id=$id" +
                ")"
    }

    /**
     * @implNote: Delegated to superclass as [id] might not be set
     */
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    /**
     * @implNote: Delegated to superclass as [id] might not be set
     */
    override fun hashCode(): Int {
        return super.hashCode()
    }
}
