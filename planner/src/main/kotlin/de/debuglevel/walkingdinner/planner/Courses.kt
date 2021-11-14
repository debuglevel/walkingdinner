package de.debuglevel.walkingdinner.planner

import mu.KotlinLogging

/**
 * A [Courses] object represents all courses in this event.
 * Each course is initialized by a list of sequential [Team]s (i.e. the output of the [GeneticPlanner]) which is then
 * transformed to a representation of [Meeting]s.
 * @implNote: As weird things might happen with a genetic approach, use [List]s instead of [Set]s so that no deduplication will occur and order is preserved.
 *   Might probably better be placed somewhere near the [GeneticPlanner].
 */
data class Courses(
    val coursesTeams: List<Pair<String, List<Team>>>,
) {
    private val logger = KotlinLogging.logger {}

    val orderedCoursesNames: List<String>
        get() {
            return coursesTeams.map { it.first }
        }

    /**
     * Gets all [Meeting]s of all [Courses].
     */
    fun toMeetings(): List<Meeting> {
        return toCoursesMeetings()
            .values
            .flatten()
    }

    /**
     * Gets all [Meeting]s of all [Courses], separated by each Course.
     */
    fun toCoursesMeetings(): Map<String, List<Meeting>> {
        val coursesMeetings = coursesTeams
            .associateBy({ it.first }, { teamsToMeetings(it.first, it.second) })
        return coursesMeetings
    }

    /**
     * Transforms a list of sequential [Team]s to a set of [Meeting]s.
     * Sets the [courseName] in the [Meeting].
     * TODO: Should probably throw an exception if an unsuitable number of teams was supplied.
     */
    private fun teamsToMeetings(courseName: String, teams: Iterable<Team>): List<Meeting> {
        val meetingSize = 3 // TODO: Should be fetched somewhere else

        if (teams.count().rem(meetingSize) != 0) {
            logger.warn { "There are ${teams.count()} teams but each meeting only allows $meetingSize teams, which leads to the last meeting having not enough teams. Undefined behavior might occur." }
        }

        val meetings = teams
            .chunked(meetingSize) { teams_ -> Meeting(courseName, teams_.toList()) }

        return meetings
    }
}