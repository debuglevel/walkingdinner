package de.debuglevel.walkingdinner.rest.dinner

import de.debuglevel.walkingdinner.rest.plan.Plan
import java.util.*

data class DinnerDTO(val id: UUID,
                     val name: String) {

    val plans: Set<Plan> = setOf()
}