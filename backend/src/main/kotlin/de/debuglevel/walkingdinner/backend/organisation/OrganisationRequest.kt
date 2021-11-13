package de.debuglevel.walkingdinner.backend.organisation

import de.debuglevel.walkingdinner.backend.MailAddress

data class OrganisationRequest(
    val name: String,
    val mail: String
) {
    fun toOrganisation(): Organisation {
        return Organisation(
            name = this.name,
            mail = MailAddress(value = this.mail)
        )
    }
}