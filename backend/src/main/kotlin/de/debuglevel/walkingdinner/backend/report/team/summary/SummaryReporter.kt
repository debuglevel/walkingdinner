package de.debuglevel.walkingdinner.backend.report.team.summary

import de.debuglevel.walkingdinner.backend.meeting.Meeting
import de.debuglevel.walkingdinner.backend.report.Reporter
import de.debuglevel.walkingdinner.backend.team.Team
import jakarta.inject.Singleton
import mu.KotlinLogging

@Singleton
class SummaryReporter : Reporter {
    private val logger = KotlinLogging.logger {}

    /**
     * Generates an overview plaintext of each course, each meeting (with the cooking team highlighted).
     */
    override fun generateReports(meetings: Set<Meeting>): String {
        logger.trace { "Generating summary report..." }
        val summary = meetings
            .groupBy { it.courseName }
            .map { (course, meetings_) ->
                var text = "== Course $course\n"
                text += meetings_.map { meetingToString(it) }.joinToString("\n")
                text
            }
            .joinToString("\n\n")

        logger.trace { "Generated summary report" }
        return summary
    }

    /**
     * Checks if the supplied [team] cooks in this [meeting].
     */
    private fun isCookingTeam(team: Team, meeting: Meeting): Boolean {
        return meeting.getCookingTeam() == team
    }

    /**
     * Creates a textual representation of a meeting with the cooking team highlighted.
     * `[Harry & Ginny]   Ron & Hermione   Lupin & Tonks`
     */
    fun meetingToString(meeting: Meeting): String {
        return meeting.teams
            .map { it ->
                val teamText = it.cooks.map { it.name.fullName }.joinToString(" & ")
                if (isCookingTeam(it, meeting)) "[$teamText]" else teamText
            }
            .joinToString("\t")
    }
}