package de.debuglevel.walkingdinner.planner.planner

import de.debuglevel.walkingdinner.planner.plan.Plan

/**
 * A [Planner] is initialized with all [Team]s, from which it generates an adequate [Plan].
 */
interface Planner {
    fun plan(): Plan
}