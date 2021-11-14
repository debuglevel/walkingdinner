package de.debuglevel.walkingdinner.planner.plan

import jakarta.inject.Singleton
import mu.KotlinLogging
import java.util.*

@Singleton
class PlanService {
    private val logger = KotlinLogging.logger {}

    /**
     * TODO: Would probably be useful if just persisted.
     */
    private val plans = mutableMapOf<UUID, Plan>()

    fun get(id: UUID): Plan {
        logger.debug { "Getting plan '$id'..." }
        val plan = plans.getOrElse(id) { throw PlanNotFoundException(id) }
        logger.debug { "Got plan: $plan" }
        return plan
    }

    fun getAll(): Set<Plan> {
        logger.debug { "Getting all plans..." }
        val plans = plans
            .map { get(it.key) }
            .toSet()
        logger.debug { "Got all plans" }
        return plans
    }

    fun add(plan: Plan) {
        logger.debug { "Adding plan $plan..." }
        plans[plan.id] = plan
        logger.debug { "Added plan $plan" }
    }

    class PlanNotFoundException(planId: UUID) : Exception("Plan $planId not found")
}