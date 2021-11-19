package de.debuglevel.walkingdinner.planner.planner.geneticplanner

import de.debuglevel.walkingdinner.planner.team.Team
import de.debuglevel.walkingdinner.planner.common.TimeMeasurement
import de.debuglevel.walkingdinner.planner.plan.Plan
import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics
import jakarta.inject.Singleton
import mu.KotlinLogging
import java.util.*
import java.util.function.Consumer
import kotlin.math.roundToInt

@Singleton
class GeneticPlannerService {
    private val logger = KotlinLogging.logger {}

    fun calculate(
        options: GeneticPlannerOptions
    ): Plan {
        val evolutionStatistics = EvolutionStatistics.ofNumber<Double>()
        options.evolutionResultConsumer = Consumer<EvolutionResult<EnumGene<Team>, Double>> {
            evolutionStatistics.accept(it)
            printIntermediary(it)
            logTimeMeasurement(it)
        }

        val plan = GeneticPlanner(options).plan()
        // Set an ID for each meeting; we do not generate an ID during evolution, as this might be time-consuming (but untested; at least unnecessary).
        plan.meetings.forEach { it.id = UUID.randomUUID() }

        return plan
    }

    private fun logTimeMeasurement(e: EvolutionResult<EnumGene<Team>, Double>) {
        val reportInterval = 500L
        TimeMeasurement.add("evolveDuration", e.durations().evolveDuration().toNanos(), reportInterval)
    }

    /**
     * Print some statistics about the state of the evolution process.
     */
    private fun printIntermediary(e: EvolutionResult<EnumGene<Team>, Double>) {
        val reportInterval = 500L

        if (e.generation().rem(reportInterval) == 0L) {
            // Estimate current evolution speed by considering the current evolution step; which is only an approximation to the average speed
            val generationDuration = e.durations().evolveDuration().toNanos() / 1_000_000_000.0
            val generationsPerSecond = (1 / generationDuration).roundToInt()
            println(
                "currently $generationsPerSecond generations/s\t| Generation #${e.generation()}\t| Best Fitness: ${
                    e.bestFitness().toBigDecimal().toPlainString()
                }"
            )
        }
    }
}