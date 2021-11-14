package de.debuglevel.walkingdinner.planner.plan

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import mu.KotlinLogging
import java.util.*

@Controller("/plans")
class PlanController(private val planService: PlanService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{planId}")
    fun getOne(planId: UUID): GetPlanResponse {
        logger.debug("Called getOne($planId)")
        val plan = planService.get(planId)
        return GetPlanResponse(plan)
    }

    @Get("/")
    fun getList(): Set<GetPlanResponse> {
        logger.debug("Called getList()")
        val plans = planService.getAll()
        return plans.map { GetPlanResponse(it) }.toSet()
    }
}