package de.debuglevel.walkingdinner.rest.plan.report.teams.gmail

import com.google.api.services.gmail.model.Draft
import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.plan.report.Reporter
import de.debuglevel.walkingdinner.rest.plan.report.teams.TextReportService
import jakarta.inject.Singleton
import mu.KotlinLogging

@Singleton
class GmailDraftReportService(
    private val textReportService: TextReportService,
    private val gmailService: GmailService
) : Reporter {
    private val logger = KotlinLogging.logger {}

    override fun generateReports(meetings: Set<Meeting>): Set<Draft> {
        logger.trace { "Generating Gmail drafts..." }

        val reports = textReportService.generateReports(meetings)
        val drafts = reports
            .map { createDraft(it.team, it.plaintext) }
            .toSet()

        val draftIds = drafts
            .map { it.id }

        logger.trace { "Generated Gmail drafts: $draftIds" }
        return drafts
    }

    private fun createDraft(team: Team, text: String): Draft {
        val mailAddresses = team.cooks.map { it.mailAddress.value }.toSet()
        val subject = "Walking Dinner"

        val draft = gmailService.saveDraft(mailAddresses, subject, text)
        return draft
    }
}