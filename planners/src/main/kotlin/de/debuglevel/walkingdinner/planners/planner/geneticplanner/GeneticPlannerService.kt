package de.debuglevel.walkingdinner.planners.planner.geneticplanner

import de.debuglevel.walkingdinner.planners.Team
import de.debuglevel.walkingdinner.planners.common.TimeMeasurement
import de.debuglevel.walkingdinner.planners.plan.Plan
import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics
import jakarta.inject.Singleton
import mu.KotlinLogging
import java.util.function.Consumer
import kotlin.math.roundToInt

@Singleton
class GeneticPlannerService {
    private val logger = KotlinLogging.logger {}

    // TODO: in FaaS style, there would be no need of saving the plan in memory;
    //       but maybe it's better to build it microservice style: POST things async, and GET it then.
    //       or provide interfaces for both styles
//    private val executor = Executors.newFixedThreadPool(threadCount)

//    fun startCalculation(
//        teams: List<Team>,
//        populationsSize: Int,
//        fitnessThreshold: Double,
//        steadyFitness: Int
//    ): Calculation {
//        val calculation = Calculation(
//            UUID.randomUUID(),
//            false,
//            surveyfile,
//            populationsSize,
//            fitnessThreshold,
//            steadyFitness,
//            null
//        )
//
//        val plannerTask = Callable<Plan> {
//            try {
//                calculatePlan(calculation)
//            } catch (e: Exception) {
//                logger.error(e) { "Callable threw exception" }
//                throw e
//            }
//        }
//
//        executor.submit(plannerTask)
//
//        calculations[calculation.id] = calculation
//        return calculation
//    }

    fun calculatePlan(
        options: GeneticPlannerOptions
    ): Plan {
        val evolutionStatistics = EvolutionStatistics.ofNumber<Double>()
        val consumers: Consumer<EvolutionResult<EnumGene<Team>, Double>>? = Consumer {
            evolutionStatistics.accept(it)
            printIntermediary(it)
        }

//        val database = databaseBuilder.build(calculation.surveyfile)

        options.evolutionResultConsumer = consumers

//        val options = GeneticPlannerOptions(
//            teams = database.teams,
//            fitnessThreshold = calculation.fitnessThreshold,
//            steadyFitness = calculation.steadyFitness,
//            populationsSize = calculation.populationsSize,
//            evolutionResultConsumer = consumers
//        )

        val plan = GeneticPlanner(options).plan()

//        calculation.finished = true
//        calculation.plan = plan
//        planService.add(plan)

        //processResults(result, evolutionStatistics)

        return plan
    }

//    private fun processResults(
//        result: EvolutionResult<EnumGene<Team>, Double>,
//        evolutionStatistics: EvolutionStatistics<Double, DoubleMomentStatistics>?
//    ) {
//        println()
//        println("Best in Generation: " + result.generation)
//        println("Best with Fitness: " + result.bestFitness)
//
//        println()
//        println(evolutionStatistics)
//
//        println()
//        val courses = CoursesProblem(result.bestPhenotype.genotype.gene.validAlleles)
//            .codec()
//            .decode(result.bestPhenotype.genotype)
//        val meetings = courses.toMeetings()
//
//        SummaryReporter().generateReports(meetings)
////        GmailDraftReporter().generateReports(meetings)
//    }

    private fun printIntermediary(e: EvolutionResult<EnumGene<Team>, Double>) {
        val reportInterval = 500L

        TimeMeasurement.add("evolveDuration", e.durations.evolveDuration.toNanos(), reportInterval)

        if (e.generation % reportInterval == 0L) {
            // estimate current evolution speed by considering the current evolution step; which is only an approximation to the average speed
            val generationDuration = e.durations.evolveDuration.toNanos() / 1_000_000_000.0
            val generationsPerSecond = (1 / generationDuration).roundToInt()
            println(
                "currently $generationsPerSecond generations/s\t| Generation #${e.generation}\t| Best Fitness: ${
                    e.bestFitness.toBigDecimal().toPlainString()
                }"
            )
        }
    }
}