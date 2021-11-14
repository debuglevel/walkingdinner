package de.debuglevel.walkingdinner.planner.calculation

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import mu.KotlinLogging
import java.util.*

@Controller("/calculations")
class CalculationController(private val calculationService: CalculationService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{calculationId}")
    fun getOne(calculationId: UUID): GetCalculationResponse {
        logger.debug("Called getOne($calculationId)")
        val calculation = calculationService.get(calculationId)
        return GetCalculationResponse(calculation)
    }

    @Get("/")
    fun getList(): Set<GetCalculationResponse> {
        logger.debug("Called getList()")
        val calculations = calculationService.getAll()
        return calculations.map { GetCalculationResponse(it) }.toSet()
    }

    @Post("/")
    fun postOne(addCalculationRequest: AddCalculationRequest): GetCalculationResponse {
        logger.debug("Called postOne($addCalculationRequest)")
        val calculation = addCalculationRequest.toCalculation()
        calculationService.start(calculation)
        return GetCalculationResponse(calculation)
    }
}