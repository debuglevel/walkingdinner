package de.debuglevel.walkingdinner.backend.team

import java.util.*

data class TeamResponse(
    val id: UUID,
    val address: String?,
    val firstname1: String?,
    val firstname2: String?,
    val lastname1: String?,
    val lastname2: String?,
    val mail1: String?,
    val mail2: String?,
    val phone1: String?,
    val phone2: String?,
    val diet: Diet?,
    val veganAppetizer: Boolean?,
    val veganMaindish: Boolean?,
    val veganDessert: Boolean?,
    val vegetarianAppetizer: Boolean?,
    val vegetarianMaindish: Boolean?,
    val vegetarianDessert: Boolean?,
    val pescetarianAppetizer: Boolean?,
    val pescetarianMaindish: Boolean?,
    val pescetarianDessert: Boolean?,
    val omnivoreAppetizer: Boolean?,
    val omnivoreMaindish: Boolean?,
    val omnivoreDessert: Boolean?,
    val notes: String?,
    val city: String?,
    val dinnerId: UUID
) {
    constructor(team: Team) :
            this(
                id = team.id!!,
                address = team.address,
                firstname1 = team.cook1.name.firstname,
                firstname2 = team.cook2.name.firstname,
                lastname1 = team.cook1.name.lastname,
                lastname2 = team.cook2.name.lastname,
                mail1 = team.cook1.mailAddress.value,
                mail2 = team.cook2.mailAddress.value,
                phone1 = team.cook1.phoneNumber.number,
                phone2 = team.cook2.phoneNumber.number,
                diet = team.diet,
                veganAppetizer = team.cookingCapabilities.any { it == CookingCapability.VeganAppetizer },
                veganMaindish = team.cookingCapabilities.any { it == CookingCapability.VeganMaindish },
                veganDessert = team.cookingCapabilities.any { it == CookingCapability.VeganDessert },
                vegetarianAppetizer = team.cookingCapabilities.any { it == CookingCapability.VegetarianAppetizer },
                vegetarianMaindish = team.cookingCapabilities.any { it == CookingCapability.VegetarianMaindish },
                vegetarianDessert = team.cookingCapabilities.any { it == CookingCapability.VegetarianDessert },
                pescetarianAppetizer = team.cookingCapabilities.any { it == CookingCapability.PescetarianAppetizer },
                pescetarianMaindish = team.cookingCapabilities.any { it == CookingCapability.PescetarianMaindish },
                pescetarianDessert = team.cookingCapabilities.any { it == CookingCapability.PescetarianDessert },
                omnivoreAppetizer = team.cookingCapabilities.any { it == CookingCapability.OmnivoreAppetizer },
                omnivoreMaindish = team.cookingCapabilities.any { it == CookingCapability.OmnivoreMaindish },
                omnivoreDessert = team.cookingCapabilities.any { it == CookingCapability.OmnivoreDessert },
                notes = "TODO",
                city = team.city,
                dinnerId = team.dinner?.id
                    ?: UUID.randomUUID() // TODO: randomUUID() is just a workaround as long as CSV importer exists and leads to a nullable dinner
            )
}