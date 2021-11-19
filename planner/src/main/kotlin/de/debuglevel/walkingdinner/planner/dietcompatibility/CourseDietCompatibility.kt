package de.debuglevel.walkingdinner.planner.dietcompatibility

import de.debuglevel.walkingdinner.planner.meeting.Meeting
import de.debuglevel.walkingdinner.planner.team.Team
import mu.KotlinLogging

/**
 * This diet compatibility will check for a "downwards compatible" diet while checking for the team's cooking capability:
 * A vegetarian can eat vegan food, but a vegan cannot eat vegetarian food.
 * One vegetarian might by able to cook a vegan meal while another one does not know how to do that.
 * This mixes teams based on their diet and capabilities.
 */
object CourseDietCompatibility : DietCompatibility {
    private val logger = KotlinLogging.logger {}

    override fun isCompatible(meeting: Meeting): Boolean {
        val cookingTeam = meeting.getCookingTeam()
        val otherTeams = meeting.teams.minus(cookingTeam)

        return isCapabilityCompatible(
            meeting.courseName,
            cookingTeam.cookingCapabilities,
            otherTeams
        )
    }

    /**
     * Check if all [otherTeams] are compatible with the [cookingCapabilities] of the cooking team.
     */
    private fun isCapabilityCompatible(
        course: String,
        cookingCapabilities: List<CookingCapability>,
        otherTeams: List<Team>
    ): Boolean {
        return otherTeams.all {
            isCapabilityCompatible(
                course,
                cookingCapabilities,
                it
            )
        }
    }

    /**
     * Check if the [otherTeam] is compatible with the [cookingCapabilities] of the cooking team.
     */
    private fun isCapabilityCompatible(
        course: String,
        cookingCapabilities: List<CookingCapability>,
        otherTeam: Team
    ): Boolean {
        // TODO: although Courses are flexible, this is still hardcoded to the old scheme.
        val course1name = "Vorspeise"
        val course2name = "Hauptspeise"
        val course3name = "Dessert"

        when (otherTeam.diet) {
            Diet.Vegan -> {
                when (course) {
                    course1name ->
                        return cookingCapabilities.contains(CookingCapability.VeganAppetizer)
                    course2name ->
                        return cookingCapabilities.contains(CookingCapability.VeganMaindish)
                    course3name ->
                        return cookingCapabilities.contains(CookingCapability.VeganDessert)
                }
            }

            Diet.Vegetarian -> {
                when (course) {
                    course1name ->
                        return cookingCapabilities.contains(CookingCapability.VeganAppetizer) ||
                                cookingCapabilities.contains(CookingCapability.VegetarianAppetizer)
                    course2name ->
                        return cookingCapabilities.contains(CookingCapability.VeganMaindish) ||
                                cookingCapabilities.contains(CookingCapability.VegetarianMaindish)
                    course3name ->
                        return cookingCapabilities.contains(CookingCapability.VeganDessert) ||
                                cookingCapabilities.contains(CookingCapability.VegetarianDessert)
                }
            }

            Diet.Pescetarian -> {
                when (course) {
                    course1name ->
                        return cookingCapabilities.contains(CookingCapability.VeganAppetizer) ||
                                cookingCapabilities.contains(CookingCapability.VegetarianAppetizer) ||
                                cookingCapabilities.contains(CookingCapability.PescetarianAppetizer)
                    course2name ->
                        return cookingCapabilities.contains(CookingCapability.VeganMaindish) ||
                                cookingCapabilities.contains(CookingCapability.VegetarianMaindish) ||
                                cookingCapabilities.contains(CookingCapability.PescetarianMaindish)
                    course3name ->
                        return cookingCapabilities.contains(CookingCapability.VeganDessert) ||
                                cookingCapabilities.contains(CookingCapability.VegetarianDessert) ||
                                cookingCapabilities.contains(CookingCapability.PescetarianDessert)
                }
            }

            Diet.Omnivore -> {
                when (course) {
                    course1name ->
                        return cookingCapabilities.contains(CookingCapability.VeganAppetizer) ||
                                cookingCapabilities.contains(CookingCapability.VegetarianAppetizer) ||
                                cookingCapabilities.contains(CookingCapability.PescetarianAppetizer) ||
                                cookingCapabilities.contains(CookingCapability.OmnivoreAppetizer)
                    course2name ->
                        return cookingCapabilities.contains(CookingCapability.VeganMaindish) ||
                                cookingCapabilities.contains(CookingCapability.VegetarianMaindish) ||
                                cookingCapabilities.contains(CookingCapability.PescetarianMaindish) ||
                                cookingCapabilities.contains(CookingCapability.OmnivoreMaindish)
                    course3name ->
                        return cookingCapabilities.contains(CookingCapability.VeganDessert) ||
                                cookingCapabilities.contains(CookingCapability.VegetarianDessert) ||
                                cookingCapabilities.contains(CookingCapability.PescetarianDessert) ||
                                cookingCapabilities.contains(CookingCapability.OmnivoreDessert)
                }
            }
        }

        logger.error("CourseCompatibility.isCapabilityCompatible(): Some case was not caught. This should not happen.")
        return false
    }
}