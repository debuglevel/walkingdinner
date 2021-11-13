package de.debuglevel.walkingdinner.backend.plan.report.teams.gmail

import com.google.api.services.gmail.model.Draft
import de.debuglevel.walkingdinner.backend.Meeting
import de.debuglevel.walkingdinner.backend.participant.Team
import de.debuglevel.walkingdinner.backend.plan.report.Reporter
import de.debuglevel.walkingdinner.backend.plan.report.teams.TextReportService
import mu.KotlinLogging
import javax.inject.Singleton

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