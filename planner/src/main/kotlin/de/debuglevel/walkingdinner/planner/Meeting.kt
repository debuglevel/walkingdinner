package de.debuglevel.walkingdinner.planner

data class Meeting(
    val course: String,
    /**
     * TODO: Maybe throw an exception somehow if count() is not 3
     */
    val teams: List<Team>
) {
    /**
     * Get the team which is cooking in this meeting
     * @implNote The cooking team is defined as the first one in the list
     */
    fun getCookingTeam(): Team {
        return teams.first()
    }
}
