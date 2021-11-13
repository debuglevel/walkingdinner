package de.debuglevel.walkingdinner.planner

import de.debuglevel.walkingdinner.planner.calculation.CalculationRequest
import de.debuglevel.walkingdinner.planner.plan.PlanResponse
import de.debuglevel.walkingdinner.planner.plan.toPlanResponse
import de.debuglevel.walkingdinner.planner.planner.geneticplanner.GeneticPlannerOptions
import de.debuglevel.walkingdinner.planner.planner.geneticplanner.GeneticPlannerService
import io.micronaut.function.FunctionBean
import mu.KotlinLogging
import java.util.function.Function

@FunctionBean("genetic-planner")
class GeneticPlanFunction(private val geneticPlannerService: GeneticPlannerService) :
    Function<CalculationRequest, PlanResponse> {
    private val logger = KotlinLogging.logger {}

    override fun apply(calculationRequest: CalculationRequest): PlanResponse {
        logger.info { "Processing $calculationRequest..." }

        val geneticPlannerOptions = GeneticPlannerOptions(
            teams = calculationRequest.teams.map { it.toTeam() },
            fitnessThreshold = calculationRequest.fitnessThreshold,
            populationsSize = calculationRequest.populationsSize,
            steadyFitness = calculationRequest.steadyFitness
        )
        val plan = geneticPlannerService.calculatePlan(geneticPlannerOptions)
        val planResponse = plan.toPlanResponse()

        logger.info { "Processed $calculationRequest: $planResponse" }
        return planResponse
    }
}