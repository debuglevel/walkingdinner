package de.debuglevel.walkingdinner.backend.dinner

import de.debuglevel.walkingdinner.backend.organisation.OrganisationService
import java.time.LocalDateTime
import java.util.*

data class DinnerRequest(
    val name: String,

    val city: String,

    val begin: LocalDateTime,

    val organisationId: UUID
) {
    fun toDinner(organisationService: OrganisationService): Dinner {
        return Dinner(
            name = this.name,
            city = this.city,
            begin = this.begin,
            organisation = organisationService.get(this.organisationId)
        )
    }
}