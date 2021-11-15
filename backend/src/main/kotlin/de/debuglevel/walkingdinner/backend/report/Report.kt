package de.debuglevel.walkingdinner.backend.report

import de.debuglevel.walkingdinner.backend.plan.Plan
import java.util.*

interface Report {
    val id: UUID?
    val plan: Plan
}