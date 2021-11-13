package de.debuglevel.walkingdinner.backend.organisation

import java.util.*

data class OrganisationResponse(
    val id: UUID?,

    val mail: String,

    val name: String
) {
    constructor(organisation: Organisation) :
            this(
                organisation.id!!,
                organisation.mail.value,
                organisation.name
            )

    override fun toString(): String {
        return "OrganisationResponse(id=$id, mail='$mail', name='$name')"
    }
}