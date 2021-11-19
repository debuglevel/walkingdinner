package de.debuglevel.walkingdinner.planner.plan

import java.util.*

data class GetPlanResponse(
    val id: UUID,
    val meetings: Set<GetMeetingResponse>,
    val additionalInformation: String,
    val fitness: Double,
) {
    constructor(plan: Plan) : this(
        id = plan.id,
        meetings = plan.meetings.map { GetMeetingResponse(it) }.toSet(),
        additionalInformation = plan.additionalInformation,
        fitness = plan.fitness,
    )
}

