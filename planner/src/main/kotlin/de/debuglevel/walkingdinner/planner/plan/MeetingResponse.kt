package de.debuglevel.walkingdinner.planner.plan

import de.debuglevel.walkingdinner.planner.Meeting
import java.util.*

data class MeetingResponse(
    val id: UUID,
    val course: String,
    val teams: List<TeamResponse>
)

fun Meeting.toMeetingResponse(): MeetingResponse {
    return MeetingResponse(
        id = UUID.randomUUID(), // TODO: stupid idea, because this is not idempotent
        teams = this.teams.map { it.toTeamResponse() },
        course = this.course
    )
}
