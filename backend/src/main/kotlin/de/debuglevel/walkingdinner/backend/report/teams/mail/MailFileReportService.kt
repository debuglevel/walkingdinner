package de.debuglevel.walkingdinner.backend.report.teams.mail


import de.debuglevel.walkingdinner.backend.Meeting
import de.debuglevel.walkingdinner.backend.report.Reporter
import de.debuglevel.walkingdinner.backend.report.teams.TextReportService
import de.debuglevel.walkingdinner.backend.team.Team
import io.micronaut.context.annotation.Property
import jakarta.inject.Singleton
import mu.KotlinLogging
import javax.mail.internet.MimeMessage

@Singleton
class MailFileReportService(
    @Property(name = "app.walkingdinner.reporters.mail.from-mail-address") private val fromMailAddress: String,
    private val textReportService: TextReportService,
    private val mailService: MailService
) : Reporter {
    private val logger = KotlinLogging.logger {}

    /**
     * Generates a mail message (i.e. a [MimeMessage]) for each team occurring in the [meetings].
     */
    override fun generateReports(meetings: Set<Meeting>): Set<MimeMessage> {
        logger.trace { "Generating mail files..." }

        val textReports = textReportService.generateReports(meetings)
        val mimeMessages = textReports
            .map { buildMailFile(it.team, it.plaintext) }
            .toSet()

        logger.trace { "Generated mail files" }
        return mimeMessages
    }

    /**
     * Builds a [MimeMessage] addressed to all the [team] cook's mail addresses, containing [plaintext] as mail body.
     */
    private fun buildMailFile(team: Team, plaintext: String): MimeMessage {
        logger.trace { "Generating mail file for team '$team'..." }

        val toMailAddresses = team.cooks.map { it.mailAddress.value }.toSet()
        val subject = "Walking Dinner"

        val mimeMessage = mailService.buildMimeMessage(
            toMailAddresses = toMailAddresses,
            fromMailAddress = fromMailAddress,
            subject = subject,
            bodyText = plaintext
        )

        logger.trace { "Generated mail file for team '$team'" }
        return mimeMessage
    }
}