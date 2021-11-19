package de.debuglevel.walkingdinner.planner.plan

import de.debuglevel.walkingdinner.planner.meeting.Meeting
import java.util.*

data class GetMeetingResponse(
    val id: UUID,
    val course: String,
    val teams: List<GetTeamResponse>
) {
    constructor(meeting: Meeting) : this(
        id = meeting.id!!,
        teams = meeting.teams.map { GetTeamResponse(it) },
        course = meeting.courseName
    )
}
