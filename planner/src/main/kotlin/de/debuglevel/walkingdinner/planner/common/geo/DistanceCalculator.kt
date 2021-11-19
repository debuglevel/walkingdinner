package de.debuglevel.walkingdinner.planner.common.geo

import de.debuglevel.walkingdinner.planner.location.Location

interface DistanceCalculator {
    fun calculate(source: Location, destination: Location): Double
}