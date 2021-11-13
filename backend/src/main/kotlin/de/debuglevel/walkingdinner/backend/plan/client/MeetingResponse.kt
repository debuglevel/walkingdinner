package de.debuglevel.walkingdinner.backend.plan.client

import de.debuglevel.walkingdinner.backend.Meeting
import de.debuglevel.walkingdinner.backend.participant.Team
import java.util.*

data class MeetingResponse(
    val id: UUID,
    val course: String,
    val teams: List<TeamResponse>
) {
    fun toMeeting(fullTeams: List<Team>): Meeting {
        return Meeting(
            teams = this.teams.map { team ->
                fullTeams.first { fullTeam ->
                    fullTeam.id == team.id
                }
            },
            course = this.course,
            id = null
        )
    }
}

