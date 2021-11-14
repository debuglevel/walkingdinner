package de.debuglevel.walkingdinner.planner.plan

import de.debuglevel.walkingdinner.planner.Team
import java.util.*

data class GetTeamResponse(val id: UUID) {
    constructor(team: Team) : this(
        id = team.id
    )
}
