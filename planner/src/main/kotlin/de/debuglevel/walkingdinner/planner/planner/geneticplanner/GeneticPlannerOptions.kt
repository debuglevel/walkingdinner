package de.debuglevel.walkingdinner.planner.planner.geneticplanner

import de.debuglevel.walkingdinner.planner.Team
import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import java.util.function.Consumer

data class GeneticPlannerOptions(
    val teams: List<Team>,
    /**
     * Fitness level to beat (minimization problem, i.e. fitness must be less than this threshold)
     */
    val fitnessThreshold: Double,
    /**
     * Number of generations with constant fitness level to stop further calculations
     */
    val steadyFitness: Int,
    /**
     * Size of the population
     */
    val populationsSize: Int,
    var evolutionResultConsumer: Consumer<EvolutionResult<EnumGene<Team>, Double>>? = null
)