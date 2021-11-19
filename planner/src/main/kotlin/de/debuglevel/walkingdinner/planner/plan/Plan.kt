package de.debuglevel.walkingdinner.planner.plan

import de.debuglevel.walkingdinner.planner.meeting.Meeting
import java.util.*

data class Plan(
    /**
     * UUID of the calculation
     */
    val id: UUID,
    val meetings: Set<Meeting>,
    val additionalInformation: String,
    val fitness: Double,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Plan

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Plan(" +
                "id=$id, " +
                "additionalInformation='$additionalInformation', " +
                "fitness=$fitness" +
                ")"
    }

}