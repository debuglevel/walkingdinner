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
    val course1teams: Iterable<Team>,
    val course2teams: Iterable<Team>,
    val course3teams: Iterable<Team>
) {
    private val logger = KotlinLogging.logger {}

    // TODO: should be labeled in english
    companion object {
        const val course1name = "Vorspeise"
        const val course2name = "Hauptspeise"
        const val course3name = "Dessert"

        val orderedCourseNames = listOf(Courses.course1name, Courses.course2name, Courses.course3name)
    }

    /**
     * Gets all [Meeting]s of all [Courses].
     */
    fun toMeetings(): List<Meeting> {
        return toCourseMeetings()
            .values
            .flatten()
    }

    /**
     * Gets all [Meeting]s of all [Courses], separated by each Course.
     */
    fun toCourseMeetings(): Map<String, List<Meeting>> {
        val courseMeetings = hashMapOf<String, List<Meeting>>()

        courseMeetings[course1name] = teamsToMeetings(
            course1name,
            course1teams
        )
        courseMeetings[course2name] = teamsToMeetings(
            course2name,
            course2teams
        )
        courseMeetings[course3name] = teamsToMeetings(
            course3name,
            course3teams
        )

        return courseMeetings
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