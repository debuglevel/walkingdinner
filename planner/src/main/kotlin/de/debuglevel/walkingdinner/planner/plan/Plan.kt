package de.debuglevel.walkingdinner.planner.plan

import de.debuglevel.walkingdinner.planner.Meeting
import java.util.*

data class Plan(
    /**
     * UUID of the calculation
     */
    val id: UUID,
    val meetings: Set<Meeting>,
    val additionalInformation: String
)