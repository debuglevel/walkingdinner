package de.debuglevel.walkingdinner.planner.calculation

import java.util.*

data class AddCalculationResponse(
    /**
     * UUID of the calculation
     */
    val id: UUID,
) {
    constructor(calculation: Calculation) : this(
        id = calculation.id!!,
    )
}
