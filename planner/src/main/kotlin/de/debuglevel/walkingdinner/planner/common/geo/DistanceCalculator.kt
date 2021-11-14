package de.debuglevel.walkingdinner.planner.common.geo

import de.debuglevel.walkingdinner.planner.Location

interface DistanceCalculator {
    fun calculate(source: Location, destination: Location): Double
}