package de.debuglevel.walkingdinner.backend.organisation

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface OrganisationRepository : CrudRepository<Organisation, UUID>