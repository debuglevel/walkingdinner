package de.debuglevel.walkingdinner.backend.plan.calculation

import de.debuglevel.walkingdinner.backend.common.Base64String
import de.debuglevel.walkingdinner.backend.dinner.DinnerService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import mu.KotlinLogging
import java.util.*

@Controller("/plans/calculations")
class CalculationController(
    private val calculationService: CalculationService,
    private val dinnerService: DinnerService
) {
    private val logger = KotlinLogging.logger {}

    @Get("/{calculationId}")
    fun getOne(calculationId: UUID): CalculationResponse {
        logger.debug("Called getOne($calculationId)")
        val calculation = calculationService.get(calculationId)
        return CalculationResponse(calculation)
    }

    @Get("/")
    fun getList(): Set<CalculationResponse> {
        logger.debug("Called getList()")
        val calculations = calculationService.getAll()
        return calculations.map { CalculationResponse(it) }.toSet()
    }

    @Post("/")
    fun postOne(calculationRequest: CalculationRequest): CalculationResponse {
        logger.debug("Called postOne($calculationRequest)")

        val calculation = if (calculationRequest.dinnerId != null) {
            val dinner = dinnerService.get(calculationRequest.dinnerId)
            val teams = dinner.teams.toList()

            val calculation = calculationService.startCalculation(
                teams,
                calculationRequest.populationsSize,
                calculationRequest.fitnessThreshold,
                calculationRequest.steadyFitness
            )
            calculation
        } else if (calculationRequest.surveyfile != null && calculationRequest.surveyfile.isNotBlank()) {
            // TODO: as this is just a CSV, we could just transfer it as a String
            val surveyCsv = Base64String(calculationRequest.surveyfile).asString
            val calculation = calculationService.startCalculation(
                surveyCsv,
                calculationRequest.populationsSize,
                calculationRequest.fitnessThreshold,
                calculationRequest.steadyFitness
            )
            calculation
        } else {
            throw IllegalArgumentException("dinnerId or surveyfile must be set")
        }

        return CalculationResponse(calculation)
    }
}