package de.debuglevel.walkingdinner.planner

import de.debuglevel.walkingdinner.planner.calculation.AddCalculationRequest
import de.debuglevel.walkingdinner.planner.plan.GetPlanResponse
import de.debuglevel.walkingdinner.planner.planner.geneticplanner.GeneticPlannerOptions
import de.debuglevel.walkingdinner.planner.planner.geneticplanner.GeneticPlannerService
import io.micronaut.function.FunctionBean
import mu.KotlinLogging
import java.util.function.Function

@FunctionBean("genetic-planner")
class GeneticPlanFunction(private val geneticPlannerService: GeneticPlannerService) :
    Function<AddCalculationRequest, GetPlanResponse> {
    private val logger = KotlinLogging.logger {}

    override fun apply(addCalculationRequest: AddCalculationRequest): GetPlanResponse {
        logger.info { "Processing $addCalculationRequest..." }

        val geneticPlannerOptions = GeneticPlannerOptions(
            teams = addCalculationRequest.teams.map { it.toTeam() },
            fitnessThreshold = addCalculationRequest.fitnessThreshold,
            populationsSize = addCalculationRequest.populationsSize,
            steadyFitness = addCalculationRequest.steadyFitness
        )
        val plan = geneticPlannerService.calculatePlan(geneticPlannerOptions)
        val getPlanResponse = GetPlanResponse(plan)

        logger.info { "Processed $addCalculationRequest: $getPlanResponse" }
        return getPlanResponse
    }
}