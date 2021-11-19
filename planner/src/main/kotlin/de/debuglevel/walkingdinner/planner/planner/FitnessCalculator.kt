package de.debuglevel.walkingdinner.planner.planner

import de.debuglevel.walkingdinner.planner.Courses
import de.debuglevel.walkingdinner.planner.common.geo.GeoUtils
import de.debuglevel.walkingdinner.planner.dietcompatibility.CourseDietCompatibility
import de.debuglevel.walkingdinner.planner.location.Location
import de.debuglevel.walkingdinner.planner.meeting.Meeting
import de.debuglevel.walkingdinner.planner.team.Team

/**
 * Calculates the fitness of a [Courses] object.
 * @implNote: No placed in geneticplanner package because it might be used
 * to calculate or compare the fitness of results of other algorithms
 */
object FitnessCalculator {
    /**
     * Calculates the fitness of a [Courses] object.
     * @implNote: As weird things might happen with a genetic approach, use [List]s instead of [Set]s so that no deduplication will occur and order is preserved.
     */
    fun getFitness(courses: Courses): Double {
        val coursesMeetings = courses.toCoursesMeetings()

        val meetings = coursesMeetings.values.flatten()

        val multipleCookingTeamsMalus = 1 * calculateMultipleCookingTeams(meetings)

        val incompatibleTeamsMalus = coursesMeetings.values.sumOf { 1 * calculateIncompatibleMeetings(it) }

        val overallDistanceMalus = 0.00001 * calculateOverallDistance(courses.orderedCoursesNames, coursesMeetings)

        val fitness =
            multipleCookingTeamsMalus +
                    incompatibleTeamsMalus +
                    overallDistanceMalus
        return fitness
    }

    /**
     * Calculates how many meetings have incompatible teams (according to their diet and cooking capabilities).
     */
    private fun calculateIncompatibleMeetings(meetings: List<Meeting>): Int {
        // .count{ m -> !HardCompatibility.INSTANCE.areCompatibleTeams(m) }
        return meetings.count { meeting -> !CourseDietCompatibility.isCompatible(meeting) }
    }

    /**
     * Calculates the distance (in kilometers) all teams have to travel in sum.
     */
    private fun calculateOverallDistance(
        orderedCoursesNames: List<String>,
        coursesMeetings: Map<String, List<Meeting>>
    ): Double {
        val teamsLocations = getTeamLocations(orderedCoursesNames, coursesMeetings)

        return teamsLocations.values.sumOf { GeoUtils.calculateLocationsDistance(it) }
    }

    /**
     * Gets the ordered [Location]s for each [Team].
     */
    private fun getTeamLocations(
        orderedCoursesNames: List<String>,
        coursesMeetings: Map<String, List<Meeting>>
    ): HashMap<Team, MutableList<Location>> {
        // Create a HashMap with an empty List for each Team
        val teamsLocations = HashMap<Team, MutableList<Location>>()
        coursesMeetings.values.flatten().map { it.teams }.flatten().distinct().forEach {
            teamsLocations[it] = mutableListOf()
        }

        for (courseName in orderedCoursesNames) {
            val courseMeetings = coursesMeetings.getValue(courseName)
            for (meeting in courseMeetings) {
                for (team in meeting.teams) {
                    val teamLocations = teamsLocations.getValue(team)
                    teamLocations.add(meeting.getLocation())
                }
            }
        }

        return teamsLocations
    }

    /**
     * Calculates how many teams cook more than once in the given [meetings].
     */
    private fun calculateMultipleCookingTeams(meetings: List<Meeting>): Int {
        // Find out how often each team cooks
        val teamCookingCount = meetings
            .map { it.getCookingTeam() }
            .groupBy { it }
            .mapValues { it.value.count() }

        // Count how many teams cook more than once
        val multipleCookingTeamsCount = teamCookingCount.values
            .filter { it > 1 }
            .sumOf { it }

        return multipleCookingTeamsCount
    }
}