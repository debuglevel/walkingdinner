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

        val calculation = addCalculationRequest.toCalculation()

        val geneticPlannerOptions = GeneticPlannerOptions(calculation)
        val plan = geneticPlannerService.calculate(geneticPlannerOptions)
        val getPlanResponse = GetPlanResponse(plan)

        logger.info { "Processed $addCalculationRequest: $getPlanResponse" }
        return getPlanResponse
    }
}