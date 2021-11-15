package de.debuglevel.walkingdinner.backend.location

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface LocationRepository : CrudRepository<Location, UUID>