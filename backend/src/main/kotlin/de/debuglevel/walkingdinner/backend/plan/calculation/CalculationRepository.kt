package de.debuglevel.walkingdinner.backend.plan.calculation

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface CalculationRepository : CrudRepository<Calculation, UUID>