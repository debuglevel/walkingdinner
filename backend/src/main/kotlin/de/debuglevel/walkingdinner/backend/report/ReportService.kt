package de.debuglevel.walkingdinner.backend.report

import de.debuglevel.walkingdinner.backend.common.ZipService
import de.debuglevel.walkingdinner.backend.plan.PlanService
import de.debuglevel.walkingdinner.backend.report.teams.TextReportService
import de.debuglevel.walkingdinner.backend.report.teams.gmail.CreateGmailDraftsEvent
import de.debuglevel.walkingdinner.backend.report.teams.gmail.GmailDraftReportService
import de.debuglevel.walkingdinner.backend.report.teams.gmail.GmailDraftsReport
import de.debuglevel.walkingdinner.backend.report.teams.mail.MailFileReportService
import de.debuglevel.walkingdinner.backend.report.teams.mail.MailService
import de.debuglevel.walkingdinner.backend.report.teams.summary.SummaryReporter
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Singleton
import mu.KotlinLogging
import java.io.ByteArrayOutputStream
import java.util.*

@Singleton
open class ReportService(
    private val textReportService: TextReportService,
    private val summaryReporter: SummaryReporter,
    private val gmailDraftReportService: GmailDraftReportService,
    private val mailService: MailService,
    private val mailFileReportService: MailFileReportService,
    private val planService: PlanService,
    private val zipService: ZipService,
    private val eventPublisher: ApplicationEventPublisher<Any>
) {
    private val logger = KotlinLogging.logger {}

    private val reports = mutableMapOf<UUID, Report>()

    // TODO: MIGHT better be placed in a SummaryService or something; same for the other functions
    fun getSummary(planId: UUID): String {
        logger.debug { "Getting summary for plan '$planId'..." }
        val plan = planService.get(planId)
        val summary = summaryReporter.generateReports(plan.meetings)
        logger.debug { "Got summary for plan '$planId'" }
        return summary
    }

    fun getGmailDrafts(reportId: UUID): GmailDraftsReport {
        logger.debug { "Getting Gmail drafts for report '$reportId'..." }

        // TODO: handle report-not-found
        val gmailDraftsReport = reports[reportId] as GmailDraftsReport

        logger.debug { "Got Gmail drafts for report '$reportId': $gmailDraftsReport" }
        return gmailDraftsReport
    }

    fun createGmailDrafts(planId: UUID): GmailDraftsReport {
        logger.debug { "Publishing event to create Gmail drafts for plan '$planId'..." }

        val plan = planService.get(planId)
        val report = GmailDraftsReport(UUID.randomUUID(), plan)
        reports[report.id!!] = report

        val createGmailDraftsEvent = CreateGmailDraftsEvent(report)
        eventPublisher.publishEvent(createGmailDraftsEvent)

        logger.debug { "Published event to create Gmail drafts for plan '$planId': $report" }
        return report
    }

    @EventListener
    @Async
    open fun onCreateGmailDraftsEvent(createGmailDraftsEvent: CreateGmailDraftsEvent) {
        logger.debug { "Creating Gmail drafts for plan '${createGmailDraftsEvent.gmailDraftsReport.plan.id}'..." }

        val drafts = gmailDraftReportService.generateReports(createGmailDraftsEvent.gmailDraftsReport.plan.meetings)
        createGmailDraftsEvent.gmailDraftsReport.drafts.addAll(drafts)

        logger.debug { "Created Gmail drafts for plan '${createGmailDraftsEvent.gmailDraftsReport.plan.id}'" }
    }

    /**
     * Creates a .zip with mail messages as .eml files.
     */
    fun getAllMails(planId: UUID): ByteArray {
        logger.debug { "Creating mail files for plan '$planId'..." }
        val plan = planService.get(planId)

        // Get all MimeMessages (which are .eml when written to file)
        val mimeMessages = mailFileReportService.generateReports(plan.meetings)

        // Zip them into an archive
        val zipItems = mimeMessages.map {
            // Generate some random filename
            val randomUUID = UUID.randomUUID().toString()
            val filename = "$randomUUID.eml"

            // TODO: don't know if .use{} would be good (close() has no effect according to documentation);
            //  the InputStream is used later on again!
            //  Probably just gets collected by garbage collector once not used anymore.
            val byteArrayOutputStream = ByteArrayOutputStream()
            it.writeTo(byteArrayOutputStream)
            val byteArrayInputStream = byteArrayOutputStream.toByteArray().inputStream()

            ZipService.ZipItem(filename, byteArrayInputStream)
        }.toSet()

        val byteArrayOutputStream = ByteArrayOutputStream()
        zipService.writeZip(zipItems, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()

        logger.debug { "Created mail files for plan '$planId'" }
        return bytes
    }
}