package de.debuglevel.walkingdinner.planner.calculation

import de.debuglevel.walkingdinner.planner.plan.PlanService
import de.debuglevel.walkingdinner.planner.planner.geneticplanner.GeneticPlannerOptions
import de.debuglevel.walkingdinner.planner.planner.geneticplanner.GeneticPlannerService
import io.micronaut.context.annotation.Property
import jakarta.inject.Singleton
import mu.KotlinLogging
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors

@Singleton
class CalculationService(
    private val geneticPlannerService: GeneticPlannerService,
    @Property(name = "app.walkingdinner.calculations.maximum-parallel") private val threadCount: Int,
    private val planService: PlanService
) {
    private val logger = KotlinLogging.logger {}

    private val executor = Executors.newFixedThreadPool(threadCount)

    private val calculations = mutableMapOf<UUID, Calculation>()

    fun get(id: UUID): Calculation {
        logger.debug { "Getting calculation '$id'..." }
        val calculation = calculations.getOrElse(id) { throw CalculationNotFoundException(id) }
        logger.debug { "Got calculation: $calculation" }
        return calculation
    }

    fun getAll(): Set<Calculation> {
        logger.debug { "Getting all calculations..." }
        val calculations = calculations
            .map { get(it.key) }
            .toSet()
        logger.debug { "Got calculation: $calculations" }
        return calculations
    }

    fun add(calculation: Calculation): Calculation {
        val uuid = UUID.randomUUID()
        calculation.id = uuid
        logger.debug { "Adding calculation $calculation..." }

        calculations[uuid] = calculation

        logger.debug { "Added calculation $calculation" }
        return calculation
    }

    fun start(id: UUID): Calculation {
        logger.debug { "Starting calculation $id..." }
        val calculation = get(id)

        val calculatePlanTask = Callable {
            try {
                val geneticPlannerOptions = GeneticPlannerOptions(calculation)

                calculation.begin = LocalDateTime.now()
                val plan = geneticPlannerService.calculate(geneticPlannerOptions)
                calculation.end = LocalDateTime.now()
                calculation.finished = true

                calculation.plan = plan
                planService.add(plan)

                plan
            } catch (e: Exception) {
                logger.error(e) { "Callable threw exception" }
                throw e
            }
        }

        executor.submit(calculatePlanTask)

        logger.debug { "Started calculation $id" }
        return calculation
    }

    class CalculationNotFoundException(id: UUID) : Exception("Calculation $id not found")
}