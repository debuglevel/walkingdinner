package de.debuglevel.walkingdinner.planner.plan

import de.debuglevel.walkingdinner.planner.Team
import java.util.*

data class TeamResponse(val id: UUID)

fun Team.toTeamResponse(): TeamResponse {
    return TeamResponse(
        id = this.id
    )
}