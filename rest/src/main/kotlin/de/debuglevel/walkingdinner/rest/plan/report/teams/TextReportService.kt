package de.debuglevel.walkingdinner.rest.plan.report.teams

import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.participant.Diet
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.plan.Courses
import de.debuglevel.walkingdinner.rest.plan.report.Reporter
import mu.KotlinLogging
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import javax.inject.Singleton

@Singleton
open class TextReportService : Reporter {
    private val logger = KotlinLogging.logger {}

    /**
     * Generates a [TextReport] for each team occurring in of the [meetings].
     */
    override fun generateReports(meetings: Set<Meeting>): Set<TextReport> {
        logger.trace { "Generating text reports..." }

        // Get all teams once and generate a TextReport for each
        val textReports = meetings
            .flatMap { it.teams }
            .distinct()
            .map { team ->
                val text = generateReport(team, meetings)
                TextReport(team, text)
            }.toSet()

        logger.trace { "Generated text reports" }
        return textReports
    }

    /**
     * Generates a plaintext text addressed to both cooks of the [team].
     * Depending on the actual mail template, it usually contains information on...
     * * which course to cook
     * * which diet should be respected
     * * the teams to dine with (names, location, phone number)
     */
    private fun generateReport(team: Team, meetings: Set<Meeting>): String {
        logger.trace { "Generating text report..." }

        val model = JtwigModel.newModel()

        // Fill in this team
        model.with("team", team)

        // Get meetings this team is involved and sort them by course
        val teamMeetings = meetings
            .filter { it.teams.contains(team) }
            .sortedBy {
                when (it.course) {
                    Courses.course1name -> 1
                    Courses.course2name -> 2
                    Courses.course3name -> 3
                    else -> 0
                }
            }
        model.with("teamMeetings", teamMeetings)

        // Get the meeting where this team cooks
        val cookingMeeting = teamMeetings
            .first { it.getCookingTeam() == team }
        model.with("cookingMeeting", cookingMeeting)

        // Get the diet the team should cook for
        // TODO: not sure this is correct. Seems odd.
        val cookingDiet = teamMeetings
            .map { it.getCookingTeam().diet }
            .minByOrNull {
                when (it) {
                    Diet.Vegan -> 1
                    Diet.Vegetarian -> 2
                    Diet.Omnivore -> 3
                    else -> 0
                }
            }
        model.with("cookingDiet", cookingDiet)

        val template = JtwigTemplate.classpathTemplate("templates/mail.twig")

        val text = template.render(model)

        logger.trace { "Generated text report" }
        return text
    }
}
