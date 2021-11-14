package de.debuglevel.walkingdinner.planner

/**
 * A [Meeting] of [teams] which happens during a [course].
 */
data class Meeting(
    val course: String,
    /**
     * TODO: Maybe throw an exception somehow if count() is not 3
     */
    val teams: List<Team>
) {
    /**
     * Get the [Team] which is cooking in this [Meeting].
     * @implNote The cooking [Team] is defined as the first one in the list.
     */
    fun getCookingTeam(): Team {
        return teams.first()
    }
}
