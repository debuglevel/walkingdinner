package de.debuglevel.walkingdinner.planner

import mu.KotlinLogging

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
    }

    fun toMeetings(): Set<Meeting> {
        return toCourseMeetings()
            .values
            .flatten()
            .toSet()
    }

    fun toCourseMeetings(): Map<String, Set<Meeting>> {
        val courseMeetings = hashMapOf<String, Set<Meeting>>()

        courseMeetings[course1name] = teamToMeetings(
            course1teams,
            course1name
        )
        courseMeetings[course2name] = teamToMeetings(
            course2teams,
            course2name
        )
        courseMeetings[course3name] = teamToMeetings(
            course3teams,
            course3name
        )

        return courseMeetings
    }

    /**
     * Transforms a list of sequential [Team]s to a set of [Meeting]s.
     * TODO: Should probably throw an exception if an unsuitable number of teams was supplied.
     */
    private fun teamToMeetings(teams: Iterable<Team>, courseName: String): Set<Meeting> {
        val meetingSize = 3 // TODO: Should be fetched somewhere else

        if (teams.count().rem(meetingSize) != 0) {
            logger.warn { "There are ${teams.count()} teams but each meeting only allows $meetingSize teams, which leads to the last meeting having not enough teams. Undefined behavior might occur." }
        }

        val meetings = teams
            .chunked(meetingSize) { teams_ -> Meeting(courseName, teams_.toList()) }
            .toSet()

        return meetings
    }
}