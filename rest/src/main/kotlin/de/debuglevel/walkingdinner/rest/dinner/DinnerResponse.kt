package de.debuglevel.walkingdinner.rest.dinner

import java.time.LocalDateTime
import java.util.*

data class DinnerResponse(
    val id: UUID,

    val name: String,

    val city: String,

    val begin: LocalDateTime,

    val organisationId: UUID
) {
    constructor(dinner: Dinner) :
            this(
                dinner.id!!,
                dinner.name,
                dinner.city,
                dinner.begin,
                dinner.organisation.id!!
            )

    override fun toString(): String {
        return "DinnerResponse(id=$id, name='$name', city='$city', begin=$begin, organisationId=$organisationId)"
    }

}