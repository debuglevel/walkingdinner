package de.debuglevel.walkingdinner.planner.calculation

import de.debuglevel.walkingdinner.planner.Team
import de.debuglevel.walkingdinner.planner.plan.Plan
import java.time.LocalDateTime
import java.util.*

data class Calculation(
    /**
     * UUID of the calculation
     */
    var id: UUID?,
    /**
     * Whether the calculation of the plan has finished or is still in progress
     */
    var finished: Boolean,
    /**
     * Size of the population (for calculation with Genetic Algorithm)
     */
    val populationsSize: Int,
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
    val teams: List<Team>,
    /**
     * The plan, once it is calculated
     */
    var plan: Plan?,
    /**
     * When the calculation began
     */
    var begin: LocalDateTime? = null,
    /**
     * When the calculation finished
     */
    var end: LocalDateTime? = null,
    /**
     * Courses in the event
     */
    val coursesNames: List<String>,
)
