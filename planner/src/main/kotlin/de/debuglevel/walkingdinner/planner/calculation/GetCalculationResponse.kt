package de.debuglevel.walkingdinner.planner.calculation

import de.debuglevel.walkingdinner.planner.plan.GetTeamResponse
import java.time.LocalDateTime
import java.util.*

data class GetCalculationResponse(
    /**
     * UUID of the calculation
     */
    val id: UUID,
    /**
     * Whether the calculation of the plan has finished or is still in progress
     */
    val finished: Boolean,
    /**
     * Size of the population (for calculation with Genetic Algorithm)
     */
    val populationSize: Int,
    /**
     * Fitness level to beat (minimization problem, i.e. fitness must be less than this threshold) (for calculation with Genetic Algorithm)
     */
    val fitnessThreshold: Double,
    /**
     * Number of generations with constant fitness level to stop further calculations (for calculation with Genetic Algorithm)
     */
    val steadyFitness: Int,
    /**
     * Teams to calculate into the plan
     */
    val teams: List<GetTeamResponse>,
    /**
     * UUID of the plan, once it is calculated
     */
    val planId: UUID?,
    /**
     * When the calculation began
     */
    var begin: LocalDateTime? = null,
    /**
     * When the calculation finished
     */
    var end: LocalDateTime? = null
) {
    constructor(calculation: Calculation) : this(
        id = calculation.id!!,
        finished = calculation.finished,
        fitnessThreshold = calculation.fitnessThreshold,
        populationSize = calculation.populationSize,
        steadyFitness = calculation.steadyFitness,
        teams = calculation.teams.map { GetTeamResponse(it) },
        planId = calculation.plan?.id,
        begin = calculation.begin,
        end = calculation.end
    )
}
