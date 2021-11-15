package de.debuglevel.walkingdinner.backend.plan.client

import de.debuglevel.walkingdinner.backend.meeting.Meeting
import de.debuglevel.walkingdinner.backend.team.Team
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
            courseName = this.course,
            id = null
        )
    }
}

