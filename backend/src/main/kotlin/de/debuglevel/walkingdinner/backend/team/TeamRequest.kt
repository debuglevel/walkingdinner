package de.debuglevel.walkingdinner.backend.team

import de.debuglevel.walkingdinner.backend.common.MailAddress
import de.debuglevel.walkingdinner.backend.common.PhoneNumber
import de.debuglevel.walkingdinner.backend.dinner.DinnerService
import java.util.*

data class TeamRequest(
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
    private val cookingCapabilities: List<CookingCapability>
        get() {
            val capabilities = hashMapOf<CookingCapability, Boolean>()
            capabilities[CookingCapability.VeganAppetizer] = veganAppetizer ?: false
            capabilities[CookingCapability.VeganMaindish] = veganMaindish ?: false
            capabilities[CookingCapability.VeganDessert] = veganDessert ?: false
            capabilities[CookingCapability.VegetarianAppetizer] = vegetarianAppetizer ?: false
            capabilities[CookingCapability.VegetarianMaindish] = vegetarianMaindish ?: false
            capabilities[CookingCapability.VegetarianDessert] = vegetarianDessert ?: false
            capabilities[CookingCapability.PescetarianAppetizer] = pescetarianAppetizer ?: false
            capabilities[CookingCapability.PescetarianMaindish] = pescetarianMaindish ?: false
            capabilities[CookingCapability.PescetarianDessert] = pescetarianDessert ?: false
            capabilities[CookingCapability.OmnivoreAppetizer] = omnivoreAppetizer ?: false
            capabilities[CookingCapability.OmnivoreMaindish] = omnivoreMaindish ?: false
            capabilities[CookingCapability.OmnivoreDessert] = omnivoreDessert ?: false

            return capabilities.filter { it.value }.map { it.key }
        }

    fun toTeam(dinnerService: DinnerService): Team {
        return Team(
            null,
            de.debuglevel.walkingdinner.backend.team.Cook(
                name = Name(
                    firstname = firstname1 ?: throw IllegalArgumentException("firstname1"),
                    lastname = lastname1 ?: throw IllegalArgumentException("lastname1"),
                ),
                mailAddress = MailAddress(
                    value = mail1 ?: throw IllegalArgumentException("mail1")
                ),
                phoneNumber = PhoneNumber(
                    number = phone1 ?: throw IllegalArgumentException("phone1")
                )
            ),
            de.debuglevel.walkingdinner.backend.team.Cook(
                name = Name(
                    firstname = firstname2 ?: throw IllegalArgumentException("firstname2"),
                    lastname = lastname2 ?: throw IllegalArgumentException("lastname2"),
                ),
                mailAddress = MailAddress(
                    value = mail2 ?: throw IllegalArgumentException("mail2")
                ),
                phoneNumber = PhoneNumber(
                    number = phone2 ?: throw IllegalArgumentException("phone2")
                )
            ),
            address ?: throw IllegalArgumentException("address"),
            diet ?: throw IllegalArgumentException("diet"),
            cookingCapabilities,
            null,
            city ?: throw IllegalArgumentException("city"),
            dinner = dinnerService.get(dinnerId)
        )
    }
}