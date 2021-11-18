package de.debuglevel.walkingdinner.backend.calculation.client

import de.debuglevel.walkingdinner.backend.calculation.Calculation

data class CalculationRequest(
    /**
     * Size of the population
     */
    val populationSize: Int,
    /**
     * Fitness level to beat (minimization problem, i.e. fitness must be less than this threshold)
     */
    val fitnessThreshold: Double,
    /**
     * Number of generations with constant fitness level to stop further calculations
     */
    val steadyFitness: Int,
    /**
     * Teams to calculate into the plan
     */
    val teams: List<TeamRequest>,
    /**
     * Courses in the event
     */
    val coursesNames: List<String>,
) {
    constructor(calculation: Calculation) : this(
        populationSize = calculation.populationSize,
        steadyFitness = calculation.steadyFitness,
        fitnessThreshold = calculation.fitnessThreshold,
        teams = calculation.teams.map { TeamRequest(it) },
        coursesNames = calculation.coursesNames,
    )
}