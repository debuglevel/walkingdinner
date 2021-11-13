package de.debuglevel.walkingdinner.backend.plan.client

import java.util.*

data class PlanResponse(
    val id: UUID,
    val meetings: Set<MeetingResponse>
)
