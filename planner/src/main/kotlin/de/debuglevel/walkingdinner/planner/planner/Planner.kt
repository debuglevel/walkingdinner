package de.debuglevel.walkingdinner.planner.planner

import de.debuglevel.walkingdinner.planner.plan.Plan

interface Planner {
    fun plan(): Plan
}