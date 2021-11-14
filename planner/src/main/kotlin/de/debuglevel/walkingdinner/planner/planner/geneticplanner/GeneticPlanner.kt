package de.debuglevel.walkingdinner.planner.planner.geneticplanner

import de.debuglevel.walkingdinner.planner.Team
import de.debuglevel.walkingdinner.planner.plan.Plan
import de.debuglevel.walkingdinner.planner.planner.Planner
import io.jenetics.EnumGene
import io.jenetics.Optimize
import io.jenetics.PartiallyMatchedCrossover
import io.jenetics.SwapMutator
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.Limits
import io.jenetics.util.ISeq
import mu.KotlinLogging
import java.util.*
import java.util.function.Consumer

/**
 * A [Planner] which generates a [Plan] with a genetic algorithm.
 */
class GeneticPlanner(private val options: GeneticPlannerOptions) : Planner {
    private val logger = KotlinLogging.logger {}

    private val evolutionResultConsumer: Consumer<EvolutionResult<EnumGene<Team>, Double>>

    init {
        logger.debug { "Initializing GeneticPlanner with options: $options..." }

        // Add a dummy Consumer if none was provided
        this.evolutionResultConsumer = options.evolutionResultConsumer ?: Consumer {}

        logger.debug { "Initialized GeneticPlanner" }
    }

    override fun plan(): Plan {
        logger.debug("Planning...")

        val evolutionResult = compute()

        val courses = CoursesProblem(evolutionResult.bestPhenotype().genotype().gene().validAlleles())
            .codec()
            .decode(evolutionResult.bestPhenotype().genotype())
        val meetings = courses.toMeetings()

        val additionalInformation = """
            best generation: ${evolutionResult.generation()}
            total generations: ${evolutionResult.totalGenerations()}
            best fitness: ${evolutionResult.bestFitness().toBigDecimal().toPlainString()}
            meetings: $meetings
        """.trimIndent()

        val plan = Plan(UUID.randomUUID(), meetings.toSet(), additionalInformation)
        logger.debug("Planned: $plan")
        return plan
    }

    private fun compute(): EvolutionResult<EnumGene<Team>, Double> {
        logger.debug("Computing plan...")

        val problem = CoursesProblem(ISeq.of(options.teams))

        val engine = Engine
            .builder(problem)
            .populationSize(options.populationsSize)
            .optimize(Optimize.MINIMUM)
            .alterers(
                SwapMutator(0.15),
                PartiallyMatchedCrossover(0.15)
            )
            // .executor(Executors.newSingleThreadExecutor()) // Note: Use single threading when optimizing performance
            .build()

        val evolutionResult: EvolutionResult<EnumGene<Team>, Double> = engine.stream()
            .limit(
                // Proceed optimization until these limits are met
                Limits.byFitnessThreshold(options.fitnessThreshold)
                    .or(Limits.bySteadyFitness(options.steadyFitness))
            )
            .peek(evolutionResultConsumer)
            .collect(EvolutionResult.toBestEvolutionResult())

        logger.debug("Computed plan: $evolutionResult")
        return evolutionResult
    }
}